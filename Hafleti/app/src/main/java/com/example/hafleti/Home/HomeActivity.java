package com.example.hafleti.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.SpectacleApiService;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Models.Spectacle;
import com.example.hafleti.Adapters.SpectacleAdapter;
import com.example.hafleti.RepresentationActivity;
import com.example.hafleti.SearchActivity;
import com.example.hafleti.Spectacle.SpectacleDetailsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> spectacleList = new ArrayList<>();
    private Context context; // Add context reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this; // Initialize context

        recyclerView = findViewById(R.id.recyclerSpectacles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupBottomNavigation(this);

        fetchSpectacles();
    }

    private void fetchSpectacles() {
        SpectacleApiService apiService = ApiClient.getClient().create(SpectacleApiService.class);
        Call<List<Spectacle>> call = apiService.getAllSpectacles();

        call.enqueue(new Callback<List<Spectacle>>() {
            @Override
            public void onResponse(Call<List<Spectacle>> call, Response<List<Spectacle>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    spectacleList = response.body();
                    adapter = new SpectacleAdapter(context, spectacleList, spectacle -> {
                        Intent intent = new Intent(context, RepresentationActivity.class); // 👈 Go to representations
                        intent.putExtra("spectacle_id", spectacle.getId());
                        startActivity(intent);
                    });

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Spectacle>> call, Throwable t) {
                Log.e("HomeActivity", "API call failed", t); // 👈 logs full stack trace
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void setupBottomNavigation(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            /*if (itemId == R.id.nav_home) {
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(homeIntent);
                return true;*/

             if (itemId == R.id.nav_search) {
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                activity.startActivity(searchIntent);
                return true;

            } else if (itemId == R.id.nav_profile) {
                if (isUserLoggedIn()) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                } else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                return true;
            }

            return false;
        });

    }

    private boolean isUserLoggedIn() {
        // Exemple : vérifie dans SharedPreferences si un token ou ID utilisateur existe
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.contains("user_id");
    }

}