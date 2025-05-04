package com.example.hafleti.Paiement;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.MesReservationsActivity;
import com.example.hafleti.Models.BilletPurchaseRequest;
import com.example.hafleti.Models.BilletPurchaseRequestWithUser;
import com.example.hafleti.Models.RepresentationResponseDTO;
import com.example.hafleti.Models.Reservation;
import com.example.hafleti.Models.SpectacleHomeDTO;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.RepresentationApiService;
import com.example.hafleti.Network.ReservationApiService;
import com.example.hafleti.Network.SpectacleApiService;
import com.example.hafleti.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaiementActivity extends AppCompatActivity {

    private EditText editCustomer, editEmail, editCardNumber, editCVV;
    private Spinner monthSpinner, yearSpinner;
    private TextView orderSummary, timerText;
    private Button payButton;
    private SharedPreferences sharedPreferences;

    private Drawable defaultBackground, errorBackground;

    private boolean isEmailValid, isCardValid, isCVVValid, isDateValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        editCustomer = findViewById(R.id.editCustomer);
        editEmail = findViewById(R.id.editEmail);
        editCardNumber = findViewById(R.id.editCardNumber);
        editCVV = findViewById(R.id.editCVV);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        orderSummary = findViewById(R.id.orderSummary);
        timerText = findViewById(R.id.timerText);
        payButton = findViewById(R.id.payButton);
        sharedPreferences = getSharedPreferences("user_prefs", this.MODE_PRIVATE);

        defaultBackground = ContextCompat.getDrawable(this, R.drawable.edittext_default);
        errorBackground = ContextCompat.getDrawable(this, R.drawable.edittext_error);

        // Récupération de l’intent
        Intent intent = getIntent();
        long representationId = intent.getLongExtra("representation_id", -1);
        long spectacleId = intent.getLongExtra("spectacle_id", -1);
        String billetSummary = intent.getStringExtra("billet_summary");
        String totalAmount = intent.getStringExtra("total_amount");

        SpectacleApiService spectacleService = ApiClient.getClient().create(SpectacleApiService.class);
        RepresentationApiService representationService = ApiClient.getClient().create(RepresentationApiService.class);
        spectacleService.getById(spectacleId).enqueue(new Callback<SpectacleHomeDTO>() {
            @Override
            public void onResponse(Call<SpectacleHomeDTO> call, Response<SpectacleHomeDTO> response1) {
                if (response1.isSuccessful() && response1.body() != null) {
                    String spectacleNom = response1.body().getTitre();

                    // Récupérer lieu et date
                    representationService.getRepresentationById(representationId).enqueue(new Callback<RepresentationResponseDTO>() {
                        @Override
                        public void onResponse(Call<RepresentationResponseDTO> call2, Response<RepresentationResponseDTO> response2) {
                            if (response2.isSuccessful() && response2.body() != null) {
                                String lieu = response2.body().getLieuNom();
                                String date = response2.body().getDate();

                                // Résumé
                                String commande = spectacleNom + " @ " + lieu;
                                orderSummary.setText("Commande: " + commande +
                                        "\nMarchand: " + billetSummary +
                                        "\nMontant: " + totalAmount + " DT" +
                                        "\nDate: " + date);
                            }
                        }

                        @Override
                        public void onFailure(Call<RepresentationResponseDTO> call2, Throwable t) {
                            Toast.makeText(PaiementActivity.this, "Erreur chargement représentation", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SpectacleHomeDTO> call1, Throwable t) {
                Toast.makeText(PaiementActivity.this, "Erreur chargement spectacle", Toast.LENGTH_SHORT).show();
            }
        });

        setupSpinners();
        setupValidations();
        startCountdown();

        // In PaiementActivity, modify the payButton onClickListener:
        payButton.setOnClickListener(v -> {
            if (allValid()) {
                // Get the selected billets from the intent
                Bundle bundle = getIntent().getBundleExtra("selected_billets");
                Map<String, Integer> selectedBillets = new HashMap<>();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        selectedBillets.put(key, bundle.getInt(key));
                    }
                }

                // Create the basic BilletPurchaseRequest
                BilletPurchaseRequest billetRequest = new BilletPurchaseRequest();
                billetRequest.setRepresentationId(representationId);
                billetRequest.setBillets(selectedBillets);

                // Check if user is logged in
                boolean isLoggedIn = sharedPreferences.contains("token");
                Long clientId = isLoggedIn ? sharedPreferences.getLong("id", -1) : null;
                String email = editEmail.getText().toString().trim();
                String fullName = editCustomer.getText().toString().trim();

                // Create enhanced request
                /*BilletPurchaseRequestWithUser request = new BilletPurchaseRequestWithUser(
                        billetRequest,
                        clientId,
                        email,
                        fullName
                );*/

                representationService.markBilletsAsSold(billetRequest).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("PaymentDebug", "Received response from markBilletsAsSold");

                        if (response.isSuccessful()) {
                            Log.d("PaymentDebug", "Payment successful - HTTP " + response.code());
                            showSuccessDialog();
                        } else {
                            // Log the error details
                            Log.e("PaymentDebug", "Payment failed - HTTP " + response.code());

                            try {
                                if (response.errorBody() != null) {
                                    String errorBody = response.errorBody().string();
                                    Log.e("PaymentDebug", "Error response body: " + errorBody);
                                }
                            } catch (IOException e) {
                                Log.e("PaymentDebug", "Error reading error body", e);
                            }

                            // More detailed error message
                            String errorMsg = "Erreur lors du paiement (Code: " + response.code() + ")";
                            Toast.makeText(PaiementActivity.this, errorMsg, Toast.LENGTH_LONG).show();

                            // Log the request that failed
                            Log.e("PaymentDebug", "Failed request details:");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("PaymentDebug", "Network failure", t);

                        String errorMsg = "Échec de la connexion: " + t.getMessage();
                        Toast.makeText(PaiementActivity.this, errorMsg, Toast.LENGTH_LONG).show();

                        // Log the request that failed
                        Log.e("PaymentDebug", "Failed request details:");
                        Log.e("PaymentDebug", "URL: " + call.request().url());
                    }
                });
            } else {
                Toast.makeText(this, "Veuillez corriger les erreurs.", Toast.LENGTH_SHORT).show();
            }
        });

        // Auto-fill for logged-in users
        if (sharedPreferences.contains("token")) {
            // Get user info from SharedPreferences (matching your LoginActivity storage)
            String userEmail = sharedPreferences.getString("user_email", "");
            String userNom = sharedPreferences.getString("user_nom", "");
            String userPrenom = sharedPreferences.getString("user_prenom", "");

            // Combine first and last name for full name
            String fullName = userPrenom + " " + userNom;

            editEmail.setText(userEmail);
            editCustomer.setText(fullName);

            // Optionally make these fields non-editable
            editEmail.setEnabled(false);
            editCustomer.setEnabled(false);

        }

    }

    private void setupSpinners() {
        String[] months = new String[12];
        for (int i = 0; i < 12; i++) months[i] = String.format(Locale.getDefault(), "%02d", i + 1);
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(this, R.layout.spinner_item, months);
        monthSpinner.setAdapter(adapterMonth);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[15];
        for (int i = 0; i < 15; i++) years[i] = String.valueOf(currentYear + i);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, R.layout.spinner_item, years);
        yearSpinner.setAdapter(adapterYear);
        adapterMonth.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adapterYear.setDropDownViewResource(R.layout.spinner_dropdown_item);

        monthSpinner.setOnItemSelectedListener(dateWatcher);
        yearSpinner.setOnItemSelectedListener(dateWatcher);
    }

    private void setupValidations() {
        editEmail.addTextChangedListener(new ValidationWatcher(editEmail) {
            @Override
            boolean validate(String text) {
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
                return isEmailValid;
            }
        });

        editCardNumber.addTextChangedListener(new ValidationWatcher(editCardNumber) {
            @Override
            boolean validate(String text) {
                String digitsOnly = text.replaceAll("\\s", "");
                isCardValid = digitsOnly.length() == 16;
                return isCardValid;
            }

            @Override
            public void afterTextChanged(Editable s) {
                editCardNumber.removeTextChangedListener(this);
                String input = s.toString().replaceAll("\\s", "");
                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < input.length(); i++) {
                    formatted.append(input.charAt(i));
                    if ((i + 1) % 4 == 0 && (i + 1) < input.length()) {
                        formatted.append(" ");
                    }
                }
                editCardNumber.setText(formatted.toString());
                editCardNumber.setSelection(formatted.length());
                editCardNumber.addTextChangedListener(this);
                super.afterTextChanged(s);
            }
        });

        editCVV.addTextChangedListener(new ValidationWatcher(editCVV) {
            @Override
            boolean validate(String text) {
                isCVVValid = text.matches("\\d{3}");
                return isCVVValid;
            }
        });
    }

    private boolean allValid() {
        return isEmailValid && isCardValid && isCVVValid && isDateValid;
    }

    private final AdapterView.OnItemSelectedListener dateWatcher = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int selectedMonth = Integer.parseInt(monthSpinner.getSelectedItem().toString());
            int selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());

            Calendar now = Calendar.getInstance();
            Calendar selected = Calendar.getInstance();
            selected.set(Calendar.MONTH, selectedMonth - 1);
            selected.set(Calendar.YEAR, selectedYear);

            isDateValid = selected.after(now);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isDateValid = false;
        }
    };

    private void startCountdown() {
        long millis = 20 * 60 * 1000; // 20 minutes
        new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
                long sec = millisUntilFinished / 1000;
                timerText.setText(String.format(Locale.getDefault(), "Paiement expire dans : %02dd %02dh %02dm %02ds",
                        sec / 86400, (sec % 86400) / 3600, (sec % 3600) / 60, sec % 60));
            }

            public void onFinish() {
                timerText.setText("Temps écoulé !");
                payButton.setEnabled(false);
            }
        }.start();
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_success, null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        View loader = view.findViewById(R.id.loader);
        View tick = view.findViewById(R.id.tick);
        TextView message = view.findViewById(R.id.successText);
        Button btnOk = view.findViewById(R.id.btnOk);

        loader.setVisibility(View.VISIBLE);
        tick.setVisibility(View.GONE);
        btnOk.setVisibility(View.GONE);
        message.setText("Traitement en cours...");

        // Après 3 secondes, afficher le succès
        loader.postDelayed(() -> {
            loader.setVisibility(View.GONE);
            tick.setVisibility(View.VISIBLE);
            btnOk.setVisibility(View.VISIBLE);
            message.setText("Paiement Réussi");

            // Gestion du clic sur le bouton OK
            btnOk.setOnClickListener(v -> {
                createReservationAndRedirect();
                dialog.dismiss();
            });
        }, 3000);
    }
    private void createReservationAndRedirect() {
        // Récupérer les données nécessaires
        Bundle bundle = getIntent().getBundleExtra("selected_billets");
        Map<String, Integer> selectedBillets = new HashMap<>();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                selectedBillets.put(key, bundle.getInt(key));
            }
        }

        BilletPurchaseRequest request = new BilletPurchaseRequest();
        request.setRepresentationId(getIntent().getLongExtra("representation_id", -1));
        request.setBillets(selectedBillets);

        boolean isLoggedIn = sharedPreferences.contains("token");
        ReservationApiService apiService = ApiClient.getClient().create(ReservationApiService.class);

        if (isLoggedIn) {
            // Utilisateur connecté
            Long clientId = sharedPreferences.getLong("id", -1);
            Log.d("ReservationDebug", "Tentative création réservation client - ID: " + clientId);
            Log.d("ReservationDebug", "Request: " + new Gson().toJson(request));

            apiService.createReservationForClient(clientId, request).enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                    Log.d("ReservationDebug", "Réponse reçue - Code: " + response.code());

                    if (response.isSuccessful()) {
                        Log.d("ReservationDebug", "Réservation créée avec succès: " + new Gson().toJson(response.body()));
                        redirectToMesReservations();
                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                            Log.e("ReservationDebug", "Erreur création réservation - Code: " + response.code()
                                    + "\nErreur: " + errorBody
                                    + "\nHeaders: " + response.headers());
                        } catch (IOException e) {
                            Log.e("ReservationDebug", "Erreur lecture errorBody", e);
                        }
                        redirectToMesReservations();
                    }
                }

                @Override
                public void onFailure(Call<Reservation> call, Throwable t) {
                    Log.e("ReservationDebug", "Échec réseau", t);
                    Log.e("ReservationDebug", "URL appelée: " + call.request().url());
                    redirectToMesReservations();
                }
            });
        } else {
            // Invité
            String email = editEmail.getText().toString().trim();
            String fullName = editCustomer.getText().toString().trim();
            Log.d("ReservationDebug", "Tentative création réservation invité - Email: " + email + ", Nom: " + fullName);
            Log.d("ReservationDebug", "Request: " + new Gson().toJson(request));

            apiService.createReservationForGuest(email, fullName, request).enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                    Log.d("ReservationDebug", "Réponse reçue - Code: " + response.code());

                    if (response.isSuccessful()) {
                        Log.d("ReservationDebug", "Réservation invité créée: " + new Gson().toJson(response.body()));
                        redirectToHome();
                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                            Log.e("ReservationDebug", "Erreur création réservation invité - Code: " + response.code()
                                    + "\nErreur: " + errorBody
                                    + "\nHeaders: " + response.headers());
                        } catch (IOException e) {
                            Log.e("ReservationDebug", "Erreur lecture errorBody", e);
                        }
                        redirectToHome();
                    }
                }

                @Override
                public void onFailure(Call<Reservation> call, Throwable t) {
                    Log.e("ReservationDebug", "Échec réseau invité", t);
                    Log.e("ReservationDebug", "URL appelée: " + call.request().url());
                    redirectToHome();
                }
            });

        }





    }

    abstract class ValidationWatcher implements TextWatcher {
        View field;

        ValidationWatcher(View field) {
            this.field = field;
        }

        abstract boolean validate(String text);

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (validate(s.toString())) {
                field.setBackground(defaultBackground);
            } else {
                field.setBackground(errorBackground);
            }
        }
    }
    private void redirectToMesReservations() {
        Intent intent = new Intent(this, MesReservationsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // remplace la stack
        startActivity(intent);
    }

    private void redirectToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // remplace la stack
        startActivity(intent);
    }

    private void showReservationError() {
        new AlertDialog.Builder(this)
                .setTitle("Erreur")
                .setMessage("La création de la réservation a échoué. Veuillez réessayer.")
                .setPositiveButton("OK", null)
                .show();
    }
}
