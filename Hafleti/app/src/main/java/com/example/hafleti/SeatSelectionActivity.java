package com.example.hafleti;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

    private GridLayout seatGrid;
    private Button btnReserver;
    private List<Integer> selectedSeatIds = new ArrayList<>();
    private int totalSeats = 200; // Exemple : capacité du lieu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        seatGrid = findViewById(R.id.seatGrid);
        btnReserver = findViewById(R.id.btnReserver);

        // On calcule automatiquement le nombre de colonnes
        int numColumns = 10;
        int numRows = (int) Math.ceil((double) totalSeats / numColumns);
        seatGrid.setRowCount(numRows);
        seatGrid.setColumnCount(numColumns);

        generateSeats(totalSeats, numColumns);

        btnReserver.setOnClickListener(v -> {
            if (selectedSeatIds.isEmpty()) {
                Toast.makeText(this, "Aucune place sélectionnée", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Envoyer selectedSeatIds au backend ou nouvelle page
                Toast.makeText(this, "Places sélectionnées : " + selectedSeatIds.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateSeats(int total, int columns) {
        for (int i = 0; i < total; i++) {
            int seatId = i + 1;

            Button seatBtn = new Button(this);
            seatBtn.setText(String.valueOf(seatId));
            seatBtn.setTag(seatId);
            seatBtn.setBackgroundColor(Color.YELLOW);
            seatBtn.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            seatBtn.setOnClickListener(v -> toggleSeatSelection((Button) v));

            seatGrid.addView(seatBtn);
        }
    }

    private void toggleSeatSelection(Button seatBtn) {
        int seatId = (int) seatBtn.getTag();

        if (selectedSeatIds.contains(seatId)) {
            selectedSeatIds.remove(Integer.valueOf(seatId));
            seatBtn.setBackgroundColor(Color.YELLOW); // libre
        } else {
            selectedSeatIds.add(seatId);
            seatBtn.setBackgroundColor(Color.GREEN); // sélectionné
        }
    }
}
