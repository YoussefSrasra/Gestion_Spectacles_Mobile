package com.example.hafleti.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hafleti.Models.Reservation;
import com.example.hafleti.Models.ReservationDTO;
import com.example.hafleti.R;
import com.example.hafleti.Utils.ImageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private Context context;
    private List<ReservationDTO> reservations;
    private OnGeneratePdfListener listener;

    public interface OnGeneratePdfListener {
        void onGeneratePdf(ReservationDTO reservation);
    }

    public ReservationAdapter(Context context, List<ReservationDTO> reservations, OnGeneratePdfListener listener) {
        this.context = context;
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationDTO reservation = reservations.get(position);

        holder.titre.setText(reservation.getTitreSpectacle());
        byte[] imageBytes = ImageUtils.decodeBase64Image((reservation.getImageSpectacle()));
        if (imageBytes != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(imageBytes)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.imgSpectacle);
        } else {
            holder.imgSpectacle.setImageResource(R.drawable.error_image);
        }

            holder.lieuDate.setText(reservation.getLieuRepresentation() + " - " + reservation.getDateRepresentation());



        holder.detailsBillets.setText(reservation.getBilletsSummary());

        holder.btnPdf.setOnClickListener(v -> listener.onGeneratePdf(reservation));
    }


    @Override
    public int getItemCount() {
        return reservations.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSpectacle;
        TextView titre, lieuDate, detailsBillets;
        Button btnPdf;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSpectacle = itemView.findViewById(R.id.imgSpectacle);
            titre = itemView.findViewById(R.id.titreSpectacle);
            lieuDate = itemView.findViewById(R.id.lieuDate);
            detailsBillets = itemView.findViewById(R.id.detailsBillets);
            btnPdf = itemView.findViewById(R.id.btnGenererPdf);
        }
    }
}
