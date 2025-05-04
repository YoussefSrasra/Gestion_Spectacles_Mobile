package com.example.hafleti.Profile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.MesReservationsActivity;
import com.example.hafleti.Models.ClientDTO;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.ApiService;
import com.example.hafleti.R;
import com.example.hafleti.SearchActivity;
import com.example.hafleti.Utils.ImageUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ClientDTO currentClientDTO;
    private SharedPreferences prefs;
    private ApiService apiService;
    ImageView profileImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        apiService = ApiClient.getClient().create(ApiService.class);
        String clientEmail = prefs.getString("user_email", null);
        Log.d("ProfileDebug", "Client Email : " + clientEmail);
        if (clientEmail != null) {
            fetchClientInfo(clientEmail);
            Log.d("ProfileDebug", "Fetch called ");
        }

        Button changePhotoBtn = findViewById(R.id.changePhotoBtn);
        changePhotoBtn.setOnClickListener(v -> openGallery());
        profileImageView= findViewById(R.id.profileImage);

        // 1. Bouton "Modifier infos générales"
        Button editInfoBtn = findViewById(R.id.editInfoBtn);
        editInfoBtn.setOnClickListener(v -> showEditInfoDialog());

        // 2. Bouton "Changer mot de passe"
        Button changePasswordBtn = findViewById(R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(v -> showChangePasswordDialog());
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> {
            // Clear token and user data from SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // Removes all keys
            editor.apply();

            // Redirect to LoginActivity and clear back stack
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        // 3. Bouton "Historique réservations"*
        Button historyBtn = findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MesReservationsActivity.class);
            startActivity(intent);
        });

        setupBottomNavigation(this);

    }

    private void fetchClientInfo(String email) {
        Call<ClientDTO> call = apiService.getClientByEmail(email);
        call.enqueue(new Callback<ClientDTO>() {
            @Override
            public void onResponse(Call<ClientDTO> call, Response<ClientDTO> response) {
                if (response.isSuccessful()) {
                    currentClientDTO = response.body();
                    Log.d("ProfileDebug", "Response code: " + response.code());
                    Log.d("ProfileDebug", "Response body: " + response.body());
                    if (currentClientDTO != null && currentClientDTO.getPhoto() != null) {
                        try {
                            byte[] decodedString = android.util.Base64.decode(currentClientDTO.getPhoto(), android.util.Base64.DEFAULT);
                            Bitmap decodedByte = android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            profileImageView.setImageBitmap(decodedByte);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "Erreur lors du décodage de l'image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Log.d("ProfileDebug", "Response body: Nothing to show");
                }
            }

            @Override
            public void onFailure(Call<ClientDTO> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Erreur de chargement", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 1. Dialog pour modifier les infos
    private void showEditInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_info, null);

        EditText editFirstName = dialogView.findViewById(R.id.dialogEditFirstName);
        EditText editLastName = dialogView.findViewById(R.id.dialogEditLastName);
        EditText editEmail = dialogView.findViewById(R.id.dialogEditEmail);
        EditText editPhone = dialogView.findViewById(R.id.dialogEditPhone);

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        AlertDialog dialog = builder.setView(dialogView).create();

        if (currentClientDTO != null) {
            editFirstName.setText(currentClientDTO.getPrenom());
            editLastName.setText(currentClientDTO.getNom());
            editEmail.setText(currentClientDTO.getEmail());
            editPhone.setText(currentClientDTO.getNumeroTel());
        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            currentClientDTO.setPrenom(editFirstName.getText().toString());
            currentClientDTO.setNom(editLastName.getText().toString());
            currentClientDTO.setEmail(editEmail.getText().toString());
            currentClientDTO.setNumeroTel(editPhone.getText().toString());

            apiService.updateClient(currentClientDTO).enqueue(new Callback<ClientDTO>() {
                @Override
                public void onResponse(Call<ClientDTO> call, Response<ClientDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Infos mises à jour !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<ClientDTO> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Erreur serveur", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }


    // 2. Dialog pour changer le mot de passe
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);

        EditText oldPassword = dialogView.findViewById(R.id.oldPassword);
        EditText newPassword = dialogView.findViewById(R.id.newPassword);
        EditText confirmPassword = dialogView.findViewById(R.id.confirmPassword);

        Button btnCancel = dialogView.findViewById(R.id.btnCancelPassword);
        Button btnSave = dialogView.findViewById(R.id.btnSavePassword);

        AlertDialog dialog = builder.setView(dialogView).create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String oldPwd = oldPassword.getText().toString();
            String newPwd = newPassword.getText().toString();
            String confirmPwd = confirmPassword.getText().toString();

            if (newPwd.equals(confirmPwd)) {
                // TODO: Replace with actual verification & backend call
                Toast.makeText(this, "Mot de passe changé !", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    // 3. Historique (activité séparée)
    /*private static class HistoryActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            RecyclerView recyclerView = findViewById(R.id.recyclerHistory);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Filtrer les réservations passées (exemple mock)
            List<Reservation> pastReservations = filterPastReservations();

            ReservationAdapter adapter = new ReservationAdapter(this, pastReservations);
            recyclerView.setAdapter(adapter);
        }

        private List<Reservation> filterPastReservations() {
            // Implémente la logique de filtrage ici
            // Exemple mock :
            List<Reservation> allReservations = getMockReservations();
            List<Reservation> past = new ArrayList<>();
            past.add(new Reservation("Concert Jazz", "22 Juin 2023", "Terminé", R.drawable.sample2));
            return past;
        }
    }*/
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choisissez une image"), PICK_IMAGE_REQUEST);
    }

    public void setupBottomNavigation(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_search) {
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                activity.startActivity(searchIntent);
                return true;

            } else if (itemId == R.id.nav_home) {
                activity.startActivity(new Intent(activity, HomeActivity.class));
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