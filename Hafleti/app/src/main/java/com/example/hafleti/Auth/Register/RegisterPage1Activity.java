package com.example.hafleti.Auth.Register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Models.ClientDTO;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.ApiService;
import com.example.hafleti.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPage1Activity extends AppCompatActivity {

    EditText nomField, prenomField, emailField, passwordField, confirmPasswordField,
            phoneField, birthDateField, addressField;
    ImageView profileImageView;
    String photoBase64 = "";

    private static final int IMAGE_PICK_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);

        // Bind fields
        nomField = findViewById(R.id.nom);
        prenomField = findViewById(R.id.prenom);
        emailField = findViewById(R.id.email);
        phoneField = findViewById(R.id.phone);
        birthDateField = findViewById(R.id.birthDate);
        addressField = findViewById(R.id.address);
        passwordField = findViewById(R.id.password);
        confirmPasswordField = findViewById(R.id.confirmPassword);
        profileImageView = findViewById(R.id.profileImage);
        Button selectImageBtn = findViewById(R.id.selectImageBtn);
        Button registerButton = findViewById(R.id.registerButton);

        // Handle date picker
        birthDateField.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(RegisterPage1Activity.this,
                    (view, y, m, d) -> {
                        String formatted = String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d);
                        birthDateField.setText(formatted);
                    }, year, month, day);
            dialog.show();
        });

        // Image selection
        selectImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        // Handle registration
        registerButton.setOnClickListener(v -> {
            String password = passwordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "❗ Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }

            ClientDTO clientDTO = new ClientDTO(
                    nomField.getText().toString(),
                    prenomField.getText().toString(),
                    emailField.getText().toString(),
                    password,
                    phoneField.getText().toString(),
                    addressField.getText().toString(),
                    birthDateField.getText().toString(),
                    photoBase64
            );

            registerClient(clientDTO);
        });
    }

    private void registerClient(ClientDTO client) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Map<String, String>> call = apiService.registerClient(client);

        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().get("message");
                    Toast.makeText(RegisterPage1Activity.this, "✅ " + message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterPage1Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterPage1Activity.this, "❌ Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("API_ERROR", "Erreur: " + t.getMessage());
                Toast.makeText(RegisterPage1Activity.this, "⚠️ Erreur de connexion : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
                photoBase64 = encodeImageToBase64(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
}
