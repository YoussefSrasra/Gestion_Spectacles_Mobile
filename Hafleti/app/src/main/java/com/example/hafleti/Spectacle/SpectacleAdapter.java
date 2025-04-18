package com.example.hafleti.Spectacle;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.R;
import com.example.hafleti.Spectacle.Spectacle;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.ViewHolder> {

    private List<Spectacle> spectacleList;
    private Context context;

    public SpectacleAdapter(Context context, List<Spectacle> spectacleList) {
        this.context = context;
        this.spectacleList = spectacleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spectacle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Spectacle spectacle = spectacleList.get(position);
        holder.titre.setText(spectacle.getTitre());
        holder.date.setText(spectacle.getDate());
        holder.lieu.setText(spectacle.getLieu());
        holder.image.setImageResource(spectacle.getImageResId());

        holder.details.setOnClickListener(v ->{
            Toast.makeText(context, "Détails de: " + spectacle.getTitre(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, SpectacleDetailsActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titre, date, lieu;
        Button details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSpectacle);
            titre = itemView.findViewById(R.id.titreSpectacle);
            date = itemView.findViewById(R.id.dateSpectacle);
            lieu = itemView.findViewById(R.id.lieuSpectacle);
            details = itemView.findViewById(R.id.btnDetails);
        }
    }
}
