package com.example.hafleti.Auth.Register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.R;

public class RegisterPage2Activity extends AppCompatActivity {

    EditText editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);

        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        Button buttonSuivant2 = findViewById(R.id.nextButton2);
        Intent prevIntent = getIntent();
        String nom = prevIntent.getStringExtra("nom");
        String prenom = prevIntent.getStringExtra("prenom");
        String email = prevIntent.getStringExtra("email");
        String numeroTel = prevIntent.getStringExtra("numeroTel");
        String dateNaissance = prevIntent.getStringExtra("dateNaissance");
        String adresse = prevIntent.getStringExtra("adresse");

        buttonSuivant2.setOnClickListener(v -> {
            String password = editTextPassword.getText().toString();
            String confirmPwd = editTextConfirmPassword.getText().toString();

            if (password.equals(confirmPwd)) {
                Intent intent = new Intent(RegisterPage2Activity.this, RegisterPage3Activity.class);
                intent.putExtra("nom", nom);
                intent.putExtra("prenom", prenom);
                intent.putExtra("email", email);
                intent.putExtra("numeroTel", numeroTel);
                intent.putExtra("dateNaissance", dateNaissance);
                intent.putExtra("adresse", adresse);
                intent.putExtra("password", password);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
