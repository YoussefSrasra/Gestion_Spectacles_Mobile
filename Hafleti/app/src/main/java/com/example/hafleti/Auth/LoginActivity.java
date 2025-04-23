package com.example.hafleti.Auth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.hafleti.Profile.ProfileActivity;
import com.example.hafleti.R;
import com.example.hafleti.Auth.Register.RegisterPage1Activity;
import com.example.hafleti.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textRegister = findViewById(R.id.textRegister);
        textRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterPage1Activity.class);
            startActivity(intent);
        });
        Button buttonLogin = findViewById(R.id.buttonLogin);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin.setOnClickListener(v->{
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if ( email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
            else if( !isValidEmail(email) ){
                Toast.makeText(LoginActivity.this, "Forme de l'Email non valide", Toast.LENGTH_SHORT).show();


            }else {
                // À ce stade, les champs sont remplis
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
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