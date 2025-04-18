package com.example.hafleti.Paiement;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.ConfirmationActivity;
import com.example.hafleti.Home.ClientHomeActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Reservation.ReservationsActivity;

public class PaiementActivity extends AppCompatActivity {

    private int nbBillets;
    private int montant;
    private String methodePaiement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        nbBillets = getIntent().getIntExtra("nbBillets", 1);
        montant = getIntent().getIntExtra("prixTotal", 0);
        methodePaiement = getIntent().getStringExtra("methode");

        TextView txtMontant = findViewById(R.id.txtMontant);
        txtMontant.setText("Montant à payer : " + montant + " DT");

        EditText edtNumCarte = findViewById(R.id.edtNumCarte);
        EditText edtDateExp = findViewById(R.id.edtDateExp);
        EditText edtCodeSecurite = findViewById(R.id.edtCodeSecurite);
        EditText edtNom = findViewById(R.id.edtNom);
        EditText edtEmail = findViewById(R.id.edtEmail);
        Button btnPayer = findViewById(R.id.btnContinuerPaiement);

        btnPayer.setOnClickListener(v -> {
            // On valide les champs (simple)
            if (edtNumCarte.getText().toString().isEmpty() ||
                    edtDateExp.getText().toString().isEmpty() ||
                    edtCodeSecurite.getText().toString().isEmpty() ||
                    edtNom.getText().toString().isEmpty() ||
                    edtEmail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simulation de traitement de paiement (avec délai)
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Traitement du paiement...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Handler().postDelayed(() -> {
                progressDialog.dismiss();
                // Succès -> vers ticket virtuel
                Intent intent = new Intent(PaiementActivity.this, ConfirmationActivity.class);
                intent.putExtra("nbBillets", nbBillets);
                intent.putExtra("nomClient", edtNom.getText().toString());
                intent.putExtra("emailClient", edtEmail.getText().toString());
                startActivity(intent);
                finish();
            }, 3000); // 3 sec de délai simulé
        });
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
