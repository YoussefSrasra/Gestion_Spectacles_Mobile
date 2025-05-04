package com.example.hafleti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hafleti.Adapters.RepresentationAdapter;
import com.example.hafleti.Adapters.RubriqueAdapter;
import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Models.Lieu;
import com.example.hafleti.Models.Representation;
import com.example.hafleti.Models.RepresentationResponseDTO;
import com.example.hafleti.Models.RubriqueResponse;
import com.example.hafleti.Models.Spectacle;
import com.example.hafleti.Models.SpectacleHomeDTO;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.RepresentationApiService;
import com.example.hafleti.Network.RubriqueApiService;
import com.example.hafleti.Network.SpectacleApiService;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.Utils.ImageUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepresentationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RepresentationAdapter adapter;
    private List<Representation> representationList = new ArrayList<>(); // <-- init vide
    private RecyclerView recyclerViewRubriques;
    private RubriqueAdapter rubriqueAdapter;
    private List<RubriqueResponse> rubriqueList = new ArrayList<>();

    private Long spectacleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representations);

        recyclerView = findViewById(R.id.recyclerViewRepresentations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialise l’adaptateur dès le début
        adapter = new RepresentationAdapter(this, representationList);
        recyclerView.setAdapter(adapter);
        adapter.setOnRepresentationSelectedListener(selectedRepresentation -> {
            Button reserverButton = findViewById(R.id.btnReserver);
            reserverButton.setEnabled(true);
            reserverButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));
        });

        Button reserverButton = findViewById(R.id.btnReserver);
        reserverButton.setEnabled(false); // initially disabled
        reserverButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray)); // add gray color if you want
        recyclerViewRubriques = findViewById(R.id.recyclerViewRubriques);
        recyclerViewRubriques.setLayoutManager(new LinearLayoutManager(this));
        rubriqueAdapter = new RubriqueAdapter(rubriqueList);
        recyclerViewRubriques.setAdapter(rubriqueAdapter);
        spectacleId = getIntent().getLongExtra("spectacle_id", -1);
        fetchSpectacleDetails();
        setupBottomNavigation(this);
        findViewById(R.id.btnReserver).setOnClickListener(v -> {
            Representation selectedRepresentation = adapter.getSelectedRepresentation();
            if (selectedRepresentation != null) {
                Intent intent = new Intent(RepresentationActivity.this, BilletActivity.class);
                System.out.println("Representation id: " + selectedRepresentation.getId());
                intent.putExtra("representation_id", selectedRepresentation.getId());
                intent.putExtra("spectacle_id", spectacleId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Veuillez sélectionner une représentation.", Toast.LENGTH_SHORT).show();
            }
        });

        if (spectacleId != -1) {
            fetchRepresentations();
            fetchRubriques();
        } else {
            Toast.makeText(this, "Erreur: aucun spectacle sélectionné", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchRepresentations() {
        RepresentationApiService apiService = ApiClient.getClient().create(RepresentationApiService.class);
        Call<List<RepresentationResponseDTO>> call = apiService.getBySpectacle(spectacleId);
        Log.d("REP", "URL appelée : " + call.request().url()); // Ajoute ça pour debug

        call.enqueue(new Callback<List<RepresentationResponseDTO>>() {
            @Override
            public void onResponse(Call<List<RepresentationResponseDTO>> call, Response<List<RepresentationResponseDTO>> response) {
                Log.d("REP", "Code réponse HTTP: " + response.code());  // <-- Ajoute ça
                Log.d("REP", "Body: " + response.body());               // <-- Et ça
                if (response.isSuccessful() && response.body() != null) {
                    List<RepresentationResponseDTO> dtoList = response.body();
                    representationList.clear(); // clear old

                    for (RepresentationResponseDTO dto : dtoList) {
                        Representation rep = new Representation();
                        rep.setId(dto.getId());
                        //rep.setDuree(String.valueOf(dto.getDuree()));

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            rep.setDate(LocalDate.parse(dto.getDate()));
                            //rep.setHeureDebut(LocalTime.parse(dto.getHeureDebut()));
                        }

                        Lieu lieu = new Lieu();
                        lieu.setNom(dto.getLieuNom());
                        lieu.setAdresse(dto.getLieuAdresse());
                        rep.setLieu(lieu);

                        representationList.add(rep);
                    }

                    adapter.notifyDataSetChanged(); // très important
                } else {
                    Toast.makeText(RepresentationActivity.this, "Aucune représentation trouvée", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RepresentationResponseDTO>> call, Throwable t) {
                Log.e("REP", "Erreur : " + t.getMessage());
                Toast.makeText(RepresentationActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRubriques() {
        RubriqueApiService apiService = ApiClient.getClient().create(RubriqueApiService.class);
        Call<List<RubriqueResponse>> call = apiService.getRubriquesBySpectacle(spectacleId);

        call.enqueue(new Callback<List<RubriqueResponse>>() {
            @Override
            public void onResponse(Call<List<RubriqueResponse>> call, Response<List<RubriqueResponse>> response) {
                Log.d("REP", "Code réponse HTTP: " + response.code());  // <-- Ajoute ça
                Log.d("REP", "Body: " + response.body());               // <-- Et ça
                if (response.isSuccessful() && response.body() != null) {
                    rubriqueList.clear();
                    rubriqueList.addAll(response.body());
                    rubriqueAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RepresentationActivity.this, "Aucune rubrique trouvée", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RubriqueResponse>> call, Throwable t) {
                Toast.makeText(RepresentationActivity.this, "Erreur réseau (rubriques) : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSpectacleDetails() {
        Log.d("SPEC", "Fetching spectacle with ID: " + spectacleId);

        SpectacleApiService spectacleApi = ApiClient.getClient().create(SpectacleApiService.class);
        Call<SpectacleHomeDTO> call = spectacleApi.getById(spectacleId);
        Log.d("SPEC", "URL appelée: " + call.request().url());

        call.enqueue(new Callback<SpectacleHomeDTO>() {
            @Override
            public void onResponse(Call<SpectacleHomeDTO> call, Response<SpectacleHomeDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SpectacleHomeDTO spectacle = response.body();

                    Log.d("REP", "Titre reçu : " + spectacle.getTitre());
                    Log.d("REP", "Image base64 reçue (taille) : " + spectacle.getImage().length());

                    TextView titreTextView = findViewById(R.id.titreSpectacle);
                    ImageView imageView = findViewById(R.id.imageSpectacle);
                    TextView descriptionTextView = findViewById(R.id.descriptionSpectacle);

                    titreTextView.setText(spectacle.getTitre());
                    descriptionTextView.setText(spectacle.getDescription());

                    Bitmap bitmap = base64ToBitmap(spectacle.getImage());
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e("REP", "Réponse non réussie : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SpectacleHomeDTO> call, Throwable t) {
                Log.e("REP", "Erreur réseau spectacle: " + t.getMessage());
            }
        });

    }



    private Bitmap base64ToBitmap(String base64Image) {
        byte[] decodedString = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
        return android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public void setupBottomNavigation(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_search) {
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                activity.startActivity(searchIntent);
                return true;

            }
            else if (itemId == R.id.nav_home) {
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                activity.startActivity(homeIntent);
                return true;

            }else if (itemId == R.id.nav_profile) {
                if (isUserLoggedIn(activity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                } else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
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

