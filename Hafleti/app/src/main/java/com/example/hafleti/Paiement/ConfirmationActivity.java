/*package com.example.hafleti.Paiement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Reservation.ReservationsActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class ConfirmationActivity extends AppCompatActivity {

    private LinearLayout containerBillets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        int nbBillets = getIntent().getIntExtra("nbBillets", 1);
        String nomClient = getIntent().getStringExtra("nomClient");
        String emailClient = getIntent().getStringExtra("emailClient");

        TextView txtInfoClient = findViewById(R.id.txtInfoClient);
        txtInfoClient.setText("Nom : " + nomClient + "\nEmail : " + emailClient);

        containerBillets = findViewById(R.id.containerBillets);

        for (int i = 0; i < nbBillets; i++) {
            addBillet(nomClient, "CODE" + System.currentTimeMillis() + i);
        }
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

    private void addBillet(String nom, String code) {
        LinearLayout billetLayout = new LinearLayout(this);
        billetLayout.setOrientation(LinearLayout.VERTICAL);
        billetLayout.setPadding(16, 16, 16, 16);
        billetLayout.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32);
        billetLayout.setLayoutParams(params);

        TextView txtNom = new TextView(this);
        txtNom.setText("Nom : " + nom);
        txtNom.setTextSize(16f);
        txtNom.setPadding(0, 0, 0, 8);

        TextView txtCode = new TextView(this);
        txtCode.setText("Code : " + code);
        txtCode.setPadding(0, 0, 0, 8);

        ImageView qrView = new ImageView(this);
        Bitmap qrBitmap = generateQRCodeBitmap(code);
        qrView.setImageBitmap(qrBitmap);

        billetLayout.addView(txtNom);
        billetLayout.addView(txtCode);
        billetLayout.addView(qrView);

        containerBillets.addView(billetLayout);
    }

    private Bitmap generateQRCodeBitmap(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            int size = 400;
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}*/
