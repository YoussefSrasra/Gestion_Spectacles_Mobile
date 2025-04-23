package com.example.hafleti.Auth.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.ApiService;
import com.example.hafleti.R;
import com.example.hafleti.Models.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage3Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView profileImage;
    Button selectImageButton, finishButton;
    private String photoBase64 = null;

    String nom, prenom, email, numeroTel, dateNaissance, adresse, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page3);

        profileImage = findViewById(R.id.profileImage);
        selectImageButton = findViewById(R.id.selectImageBtn);
        finishButton = findViewById(R.id.finishButton);

        selectImageButton.setOnClickListener(v -> openGallery());

        // Récupérer les données des pages précédentes
        Intent data = getIntent();
        nom = data.getStringExtra("nom");
        prenom = data.getStringExtra("prenom");
        email = data.getStringExtra("email");
        numeroTel = data.getStringExtra("numeroTel");
        dateNaissance = data.getStringExtra("dateNaissance");
        adresse = data.getStringExtra("adresse");
        password = data.getStringExtra("password");

        finishButton.setOnClickListener(v -> {
            Client client = new Client(
                    nom,
                    prenom,
                    email,
                    password,
                    numeroTel,         // ici téléphone
                    adresse,       // ici adresse
                    dateNaissance,     // ici date naissance en format String "1990-01-01"
                    photoBase64           // pour photo, tu peux mettre null ou "" si pas encore d'image
            );
            registerClient(client);
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choisissez une image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profileImage.setImageBitmap(bitmap);
                photoBase64 = encodeImageToBase64(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerClient(Client client) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Map<String, String>> call = apiService.registerClient(client);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().get("message");
                    Toast.makeText(RegisterPage3Activity.this, "✅ " + message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterPage3Activity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterPage3Activity.this, "❌ Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("API_ERROR", "Erreur: " + t.getMessage());
                Toast.makeText(RegisterPage3Activity.this, "⚠️ Erreur de connexion : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return android.util.Base64.encodeToString(imageBytes, android.util.Base64.NO_WRAP);
    }

}
