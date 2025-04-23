package com.example.hafleti.Auth.Register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hafleti.R;

import java.util.Calendar;
import java.util.Locale;

public class RegisterPage1Activity extends AppCompatActivity {

    EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page1);

        editTextDate = findViewById(R.id.birthDate);
        Button buttonSuivant = findViewById(R.id.nextButton1);

        // 📆 Afficher le DatePicker
        editTextDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterPage1Activity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Format en yyyy-MM-dd avec zéros pour mois/jour
                        String formattedDate = String.format(Locale.US, "%04d-%02d-%02d",
                                selectedYear, selectedMonth + 1, selectedDay);
                        editTextDate.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });


        // 🎯 Aller à la page suivante
        buttonSuivant.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterPage1Activity.this, RegisterPage2Activity.class);
            intent.putExtra("nom", ((EditText) findViewById(R.id.nom)).getText().toString());
            intent.putExtra("prenom", ((EditText) findViewById(R.id.prenom)).getText().toString());
            intent.putExtra("email", ((EditText) findViewById(R.id.email)).getText().toString());
            intent.putExtra("numeroTel", ((EditText) findViewById(R.id.phone)).getText().toString());
            intent.putExtra("dateNaissance", ((EditText) findViewById(R.id.birthDate)).getText().toString());
            intent.putExtra("adresse", ((EditText) findViewById(R.id.address)).getText().toString());
            startActivity(intent);

        });
    }
}
