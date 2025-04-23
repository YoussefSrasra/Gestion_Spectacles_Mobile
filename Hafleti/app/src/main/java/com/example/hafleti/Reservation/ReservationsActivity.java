package com.example.hafleti.Reservation;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        RecyclerView recyclerView = findViewById(R.id.recyclerReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Données mockées
        List<Reservation> fakeReservations = new ArrayList<>();
        fakeReservations.add(new Reservation("Spectacle de Magie", "15 Mai 2025 - 20h", "Confirmé", R.drawable.sample1));
        fakeReservations.add(new Reservation("Concert Jazz", "22 Juin 2025 - 19h", "En attente", R.drawable.sample2));
        fakeReservations.add(new Reservation("Soirée Humour", "5 Juillet 2025 - 21h", "Annulé", R.drawable.sample3));

        ReservationAdapter adapter = new ReservationAdapter(this, fakeReservations);
        recyclerView.setAdapter(adapter);
        ImageButton navReservations = findViewById(R.id.navReservations);
        navReservations.setOnClickListener(v -> {
            startActivity(new Intent(this, ReservationsActivity.class));
        });
        ImageButton navAccueil = findViewById(R.id.navAccueil);
        navAccueil.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
        ImageButton navProfil = findViewById(R.id.navProfil);
        navProfil.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }
}
