package com.example.hafleti.Spectacle;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hafleti.Adapters.RepresentationAdapter;
import com.example.hafleti.Models.Spectacle;
import com.example.hafleti.R;

public class SpectacleDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectacle_details);

        // Récupérer l'ID du spectacle
        Long spectacleId = getIntent().getLongExtra("spectacle_id", -1);

        // Trouver le spectacle complet (à adapter avec votre source de données)
        Spectacle spectacle = findSpectacleById(spectacleId);

        // Afficher les infos
        ImageView imageView = findViewById(R.id.imgSpectacle);
        TextView titleView = findViewById(R.id.titreSpectacle);

        Glide.with(this).load(spectacle.getImage()).into(imageView);
        titleView.setText(spectacle.getTitre());

        // Afficher les représentations
        RecyclerView recyclerRep = findViewById(R.id.recyclerRepresentations);
        RepresentationAdapter adapter = new RepresentationAdapter(this, spectacle.getRepresentations());
        recyclerRep.setAdapter(adapter);
        recyclerRep.setLayoutManager(new LinearLayoutManager(this));
    }

    private Spectacle findSpectacleById(Long id) {
        // Implémentez cette méthode selon votre source de données
        // Pour l'exemple, on retourne un spectacle mocké
        return new Spectacle(id, "Spectacle " + id, "image_url_" + id);
    }
}