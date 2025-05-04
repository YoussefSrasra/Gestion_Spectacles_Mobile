/*package com.example.hafleti.Reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hafleti.Reservation.Reservation;
import com.example.hafleti.R;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<Reservation> reservationList;
    private Context context;

    public ReservationAdapter(Context context, List<Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);

        holder.titre.setText(reservation.getSpectacleTitle());
        holder.date.setText(reservation.getReservationDate());
        holder.status.setText(reservation.getStatus());
        holder.image.setImageResource(reservation.getImageResId());

        // Gestion du clic sur "Annuler"
        holder.btnAnnuler.setOnClickListener(v -> {
            Toast.makeText(context, "Réservation annulée : " + reservation.getSpectacleTitle(), Toast.LENGTH_SHORT).show();
            // À remplacer plus tard par une suppression en backend
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titre, date, status;
        Button btnAnnuler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgReservation);
            titre = itemView.findViewById(R.id.titreReservation);
            date = itemView.findViewById(R.id.dateReservation);
            status = itemView.findViewById(R.id.statusReservation);
            btnAnnuler = itemView.findViewById(R.id.btnAnnuler);
        }
    }
}*/