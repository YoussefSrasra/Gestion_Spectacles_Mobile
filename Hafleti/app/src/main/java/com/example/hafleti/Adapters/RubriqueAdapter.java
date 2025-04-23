package com.example.hafleti.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Models.RubriqueResponse;
import com.example.hafleti.R;

import java.util.List;

public class RubriqueAdapter extends RecyclerView.Adapter<RubriqueAdapter.RubriqueViewHolder> {

    private List<RubriqueResponse> rubriques;

    public RubriqueAdapter(List<RubriqueResponse> rubriques) {
        this.rubriques = rubriques;
    }

    @NonNull
    @Override
    public RubriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rubrique, parent, false);
        return new RubriqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RubriqueViewHolder holder, int position) {
        RubriqueResponse rubrique = rubriques.get(position);
        holder.tvType.setText(rubrique.getType());
        holder.tvHeure.setText("Début : " + rubrique.getHeureDebutRubrique());
        holder.tvDuree.setText("Durée : " + rubrique.getDureeRubrique());
        holder.tvArtiste.setText("Artiste : " + rubrique.getArtisteNomComplet());
    }

    @Override
    public int getItemCount() {
        return rubriques.size();
    }

    public static class RubriqueViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvHeure, tvDuree, tvArtiste;

        public RubriqueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvType);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvDuree = itemView.findViewById(R.id.tvDuree);
            tvArtiste = itemView.findViewById(R.id.tvArtiste);
        }
    }
}
