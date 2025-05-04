package com.example.hafleti.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Models.LoginRequest;
import com.example.hafleti.Models.LoginResponse;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.ApiService;
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Auth.Register.RegisterPage1Activity;
import com.example.hafleti.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        TextView textRegister = findViewById(R.id.textRegister);
        textRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterPage1Activity.class);
            startActivity(intent);
        });
        Button buttonLogin = findViewById(R.id.buttonLogin);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            Log.d("LoginDebug", "Email entered: " + email);
            Log.d("LoginDebug", "Password entered: " + password);

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                Log.w("LoginDebug", "Email or password is empty.");
            } else if (!isValidEmail(email)) {
                Toast.makeText(LoginActivity.this, "Forme de l'Email non valide", Toast.LENGTH_SHORT).show();
                Log.w("LoginDebug", "Invalid email format.");
            } else {
                // Call backend
                LoginRequest loginRequest = new LoginRequest(email, password);
                ApiService apiService = ApiClient.getClient().create(ApiService.class);

                Log.d("LoginDebug", "Sending login request...");

                apiService.login(loginRequest).enqueue(new retrofit2.Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                        Log.d("LoginDebug", "Response code: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse loginResponse = response.body();
                            Log.d("LoginDebug", "Login successful. Token: " + loginResponse.getToken());

                            // Save token or user info
                            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("token", loginResponse.getToken());
                            editor.putLong("id", loginResponse.getId());  // Add this line
                            editor.putString("user_email", loginResponse.getEmail());
                            editor.putString("user_nom", loginResponse.getNom());
                            editor.putString("user_prenom", loginResponse.getPrenom());
                            editor.apply();

                            boolean fromBillet = getIntent().getBooleanExtra("from_billet", false);

                            if (fromBillet) {
                                // Return to BilletActivity
                                finish();
                            } else {
                                // Default behavior: go to HomeActivity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Log.e("LoginDebug", "Login failed. Code: " + response.code());
                            if (response.errorBody() != null) {
                                try {
                                    Log.e("LoginDebug", "Error body: " + response.errorBody().string());
                                } catch (Exception e) {
                                    Log.e("LoginDebug", "Error reading error body", e);
                                }
                            }
                            Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("LoginDebug", "Network failure: " + t.getMessage(), t);
                        Toast.makeText(LoginActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        setupBottomNavigation(this);

    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void setupBottomNavigation(Activity activity) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_search) {
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                activity.startActivity(searchIntent);
                return true;

            }
            else if (itemId == R.id.nav_home) {
                Intent homeIntent = new Intent(activity, HomeActivity.class);
                activity.startActivity(homeIntent);
                return true;

            }else if (itemId == R.id.nav_profile) {
                if (isUserLoggedIn(activity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
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