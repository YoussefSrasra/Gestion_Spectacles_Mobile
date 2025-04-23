package com.example.hafleti.Profile;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.R;
import com.example.hafleti.Reservation.ReservationsActivity;
import com.example.hafleti.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Gestion photo de profil (identique à avant)
        ImageView profileImage = findViewById(R.id.profileImage);
        Button changePhotoBtn = findViewById(R.id.changePhotoBtn);
        changePhotoBtn.setOnClickListener(v -> openGallery());

        // 1. Bouton "Modifier infos générales"
        Button editInfoBtn = findViewById(R.id.editInfoBtn);
        editInfoBtn.setOnClickListener(v -> showEditInfoDialog());

        // 2. Bouton "Changer mot de passe"
        Button changePasswordBtn = findViewById(R.id.changePasswordBtn);
        changePasswordBtn.setOnClickListener(v -> showChangePasswordDialog());

        // 3. Bouton "Historique réservations"
        /*Button historyBtn = findViewById(R.id.historyBtn);
        historyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });*/
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
        setupBottomNavigation(this);

    }

    // 1. Dialog pour modifier les infos
    private void showEditInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_info, null);

        EditText editFirstName = dialogView.findViewById(R.id.dialogEditFirstName);
        EditText editLastName = dialogView.findViewById(R.id.dialogEditLastName);
        EditText editEmail = dialogView.findViewById(R.id.dialogEditEmail);
        EditText editPhone = dialogView.findViewById(R.id.dialogEditPhone);

        // Pré-remplir avec les données actuelles (à adapter)
        editFirstName.setText("Youssef");
        editLastName.setText("Srasra");
        editEmail.setText("youssefsrasra8@email.com");
        editPhone.setText("54848072");

        builder.setView(dialogView)
                .setTitle("Modifier mes informations")
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    // Valider et sauvegarder les changements
                    Toast.makeText(this, "Infos mises à jour !", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    // 2. Dialog pour changer le mot de passe
    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);

        EditText oldPassword = dialogView.findViewById(R.id.oldPassword);
        EditText newPassword = dialogView.findViewById(R.id.newPassword);
        EditText confirmPassword = dialogView.findViewById(R.id.confirmPassword);

        builder.setView(dialogView)
                .setTitle("Changer mon mot de passe")
                .setPositiveButton("Valider", (dialog, which) -> {
                    String oldPwd = oldPassword.getText().toString();
                    String newPwd = newPassword.getText().toString();
                    String confirmPwd = confirmPassword.getText().toString();

                    if (newPwd.equals(confirmPwd)) {
                        // Vérifier l'ancien mot de passe (backend) puis enregistrer
                        Toast.makeText(this, "Mot de passe changé !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
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

            if (itemId == R.id.nav_home) {
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(homeIntent);
                return true;

            } else if (itemId == R.id.nav_search) {
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                activity.startActivity(searchIntent);
                return true;

            } else if (itemId == R.id.nav_profile) {
                if (isUserLoggedIn()) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                } else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                return true;
            }

            return false;
        });

    }

    private boolean isUserLoggedIn() {
        // Exemple : vérifie dans SharedPreferences si un token ou ID utilisateur existe
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return prefs.contains("user_id");
    }
}