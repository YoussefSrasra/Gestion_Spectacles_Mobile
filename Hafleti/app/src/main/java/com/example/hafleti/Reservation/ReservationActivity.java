/*package com.example.hafleti.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Paiement.PaiementActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;

public class ReservationActivity extends AppCompatActivity {

    private NumberPicker numberPicker;
    private TextView txtPrixTotal;
    private static final int PRIX_UNITAIRE = 20; // Par exemple 20 DT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        numberPicker = findViewById(R.id.numberPickerBillets);
        txtPrixTotal = findViewById(R.id.txtPrixTotal);
        RadioGroup radioGroupPaiement = findViewById(R.id.radioGroupPaiement);
        Button btnContinuer = findViewById(R.id.btnContinuerPaiement);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(1);

        // Calcul dynamique du prix total
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int total = newVal * PRIX_UNITAIRE;
            txtPrixTotal.setText("Prix total : " + total + " DT");
        });

        // Bouton "Continuer"
        btnContinuer.setOnClickListener(v -> {
            int nbBillets = numberPicker.getValue();
            String methodePaiement = "";

            int selectedId = radioGroupPaiement.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selected = findViewById(selectedId);
                methodePaiement = selected.getText().toString();
            }

            Intent intent = new Intent(ReservationActivity.this, PaiementActivity.class);
            intent.putExtra("nbBillets", nbBillets);
            intent.putExtra("prixTotal", nbBillets * PRIX_UNITAIRE);
            intent.putExtra("methode", methodePaiement);
            startActivity(intent);
        });
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
}*/