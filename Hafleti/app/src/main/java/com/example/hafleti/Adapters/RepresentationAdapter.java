package com.example.hafleti.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Models.Representation;
import com.example.hafleti.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class RepresentationAdapter extends RecyclerView.Adapter<RepresentationAdapter.ViewHolder> {

    private final List<Representation> representations;
    private final Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;

    private OnRepresentationSelectedListener listener;

    public interface OnRepresentationSelectedListener {
        void onRepresentationSelected(Representation selected);
    }

    public RepresentationAdapter(Context context, List<Representation> representations) {
        this.context = context;
        this.representations = representations;
    }

    public void setOnRepresentationSelectedListener(OnRepresentationSelectedListener listener) {
        this.listener = listener;
    }

    public Representation getSelectedRepresentation() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return representations.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_representation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Representation r = representations.get(position);
        holder.tvLieu.setText(r.getLieu().getNom());
        holder.tvDate.setText("Date: " + r.getDate());

        holder.ivMaps.setOnClickListener(v -> {
            String lieuQuery = r.getLieu().getNom() + " " + r.getLieu().getAdresse();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + lieuQuery));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        });

        // Apply border color if selected
        if (position == selectedPosition) {
            holder.cardView.setStrokeColor(context.getResources().getColor(R.color.light_green));
        } else {
            holder.cardView.setStrokeColor(context.getResources().getColor(android.R.color.transparent));
        }

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            if (holder.getAdapterPosition() == selectedPosition) {
                // Toggle off
                selectedPosition = RecyclerView.NO_POSITION;
                notifyItemChanged(previousPosition);
                if (listener != null) listener.onRepresentationSelected(null);
            } else {
                // Select new
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);
                if (listener != null) listener.onRepresentationSelected(representations.get(selectedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return representations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLieu, tvDate;
        ImageView ivMaps;
        MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLieu = itemView.findViewById(R.id.tvLieu);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivMaps = itemView.findViewById(R.id.ivMaps);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
