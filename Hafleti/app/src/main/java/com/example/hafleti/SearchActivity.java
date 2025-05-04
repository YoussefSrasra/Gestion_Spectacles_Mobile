package com.example.hafleti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Adapters.TypeGridAdapter;
import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView typeGridRecyclerView;
    private TypeGridAdapter typeGridAdapter;
    private EditText searchInput;

    private List<String> spectacleTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        setupBottomNavigation(this);

        searchInput = findViewById(R.id.search_input);

        // Initialize RecyclerView for spectacle types
        typeGridRecyclerView = findViewById(R.id.type_grid);
        typeGridRecyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // was 2

        // Example spectacle types (you can replace with dynamic loading)
        spectacleTypes = new ArrayList<>();
        spectacleTypes.add("Stand-up");
        spectacleTypes.add("Comédie");
        spectacleTypes.add("Musique");
        spectacleTypes.add("Danse");
        spectacleTypes.add("Théâtre");
        spectacleTypes.add("Impro");

        typeGridAdapter = new TypeGridAdapter(spectacleTypes, type -> {
            // Handle click — for now just show a toast
            Toast.makeText(this, "Filter: " + type, Toast.LENGTH_SHORT).show();

            // TODO: Filter the spectacle list and show search_results RecyclerView
        });

        typeGridRecyclerView.setAdapter(typeGridAdapter);
    }
    public void setupBottomNavigation(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

           if (itemId == R.id.nav_profile) {
                if (isUserLoggedIn(activity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }

                else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                return true;
            }
           else if (itemId == R.id.nav_home) {
               Intent homeIntent = new Intent(activity, HomeActivity.class);
               activity.startActivity(homeIntent);
               return true;
           }

            return false;
        });

    }

    private boolean isUserLoggedIn(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.contains("token"); // or use prefs.getString("token", null) != null
    }
}
