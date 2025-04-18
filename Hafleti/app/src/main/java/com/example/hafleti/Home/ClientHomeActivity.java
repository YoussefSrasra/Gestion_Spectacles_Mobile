package com.example.hafleti.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Spectacle.Spectacle;
import com.example.hafleti.Spectacle.SpectacleAdapter;
import com.example.hafleti.Reservation.ReservationsActivity;
import java.util.ArrayList;
import java.util.List;

public class ClientHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpectacleAdapter adapter;
    private List<Spectacle> fakeSpectacles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        recyclerView = findViewById(R.id.recyclerSpectacles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fakeSpectacles = new ArrayList<>();
        fakeSpectacles.add(new Spectacle("Spectacle de magie", "15 Mai 2025", "Théâtre Municipal", R.drawable.sample1));
        fakeSpectacles.add(new Spectacle("Concert Jazz", "22 Juin 2025", "Salle Ibn Khaldoun", R.drawable.sample2));
        fakeSpectacles.add(new Spectacle("Soirée Humour", "5 Juillet 2025", "Maison de la culture", R.drawable.sample3));

        adapter = new SpectacleAdapter(this, fakeSpectacles);
        recyclerView.setAdapter(adapter);

        ImageButton navReservations = findViewById(R.id.navReservations);
        navReservations.setOnClickListener(v -> {
            startActivity(new Intent(this, ReservationsActivity.class));
        });
        ImageButton navAccueil = findViewById(R.id.navAccueil);
        navAccueil.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientHomeActivity.class));
        });
        ImageButton navProfil = findViewById(R.id.navProfil);
        navProfil.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }
}
