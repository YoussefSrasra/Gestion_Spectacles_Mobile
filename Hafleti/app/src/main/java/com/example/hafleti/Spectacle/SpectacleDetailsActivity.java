package com.example.hafleti.Spectacle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Home.ClientHomeActivity;
import com.example.hafleti.Paiement.PaiementActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Reservation.ReservationsActivity;
import com.example.hafleti.Rubrique.Rubrique;
import com.example.hafleti.Rubrique.RubriqueAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpectacleDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerRubriques;
    private RubriqueAdapter rubriqueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_details);

        TextView titre = findViewById(R.id.detailTitre);
        TextView dateLieu = findViewById(R.id.detailDateLieu);
        TextView description = findViewById(R.id.detailDescription);
        ImageView image = findViewById(R.id.detailImage);
        recyclerRubriques = findViewById(R.id.recyclerRubriques);

        int spectacleId = getIntent().getIntExtra("spectacleId", -1);

        // TODO : fetch spectacle + rubriques depuis ton backend avec l'ID
        // Remplir les champs

        // recyclerView setup
        recyclerRubriques.setLayoutManager(new LinearLayoutManager(this));
        rubriqueAdapter = new RubriqueAdapter(getRubriquesFake()); // à remplacer par data du backend
        recyclerRubriques.setAdapter(rubriqueAdapter);

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
        Button btnReserver = findViewById(R.id.btnReserver);
        btnReserver.setOnClickListener(v->{
            startActivity(new Intent (this, PaiementActivity.class));
        });
    }

    // Exemples de données factices
    private List<Rubrique> getRubriquesFake() {
        List<Rubrique> rubriques = new ArrayList<>();
        rubriques.add(new Rubrique("Ouverture", "10h00 - 10h30", "MC Ahmed"));
        rubriques.add(new Rubrique("Danse", "10h30 - 11h30", "Troupe XYZ"));
        return rubriques;
    }
}
