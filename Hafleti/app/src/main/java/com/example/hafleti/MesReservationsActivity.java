package com.example.hafleti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Adapters.ReservationAdapter;
import com.example.hafleti.Auth.LoginActivity;
import com.example.hafleti.Home.HomeActivity;
import com.example.hafleti.Models.Reservation;
import com.example.hafleti.Models.ReservationDTO;
import com.example.hafleti.Network.ApiClient;
import com.example.hafleti.Network.RepresentationApiService;
import com.example.hafleti.Network.ReservationApiService;
import com.example.hafleti.Profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private ReservationApiService apiService;

    private List<ReservationDTO> reservations = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Long clientId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_reservations);
        sharedPreferences = getSharedPreferences("user_prefs", this.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.contains("token");
        clientId = isLoggedIn ? sharedPreferences.getLong("id", -1) : null;
        recyclerView = findViewById(R.id.recyclerReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService = ApiClient.getClient().create(ReservationApiService.class);

        adapter = new ReservationAdapter(this, reservations, reservation -> {
            generatePDF(reservation);
            Toast.makeText(this, "PDF pour : " + reservation.getTitreSpectacle(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
        setupBottomNavigation(this);
        fetchReservations();
    }
    private void generatePDF(ReservationDTO reservation) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int y = 25;
        canvas.drawText("Confirmation de Réservation", 10, y, paint);
        y += 25;
        canvas.drawText("Spectacle: " + reservation.getTitreSpectacle(), 10, y, paint);
        y += 20;
        canvas.drawText("Date: " + reservation.getDateRepresentation(), 10, y, paint);
        y += 20;
        canvas.drawText("Lieu: " + reservation.getLieuRepresentation(), 10, y, paint);
        y += 20;
        canvas.drawText("Billets: " + reservation.getBilletsSummary(), 10, y, paint);
        y += 20;

        pdfDocument.finishPage(page);

        try {
            File file = new File(this.getExternalFilesDir(null), "reservation_" + reservation.getId() + ".pdf");
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            pdfDocument.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(FileProvider.getUriForFile(this, this.getPackageName() + ".provider", file), "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            this.startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchReservations() {
        Call<List<ReservationDTO>> call = apiService.getReservationsByClient(clientId);
        call.enqueue(new Callback<List<ReservationDTO>>() {
            @Override
            public void onResponse(Call<List<ReservationDTO>> call, Response<List<ReservationDTO>> response) {
                if (response.isSuccessful()) {
                    reservations.clear();
                    reservations.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ReservationDTO>> call, Throwable t) {
                Toast.makeText(MesReservationsActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                } else {
                    activity.startActivity(new Intent(activity, LoginActivity.class));
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
