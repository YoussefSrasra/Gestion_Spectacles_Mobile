package com.example.hafleti.Rubrique;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.R;

import java.util.List;

public class RubriqueAdapter extends RecyclerView.Adapter<RubriqueAdapter.RubriqueViewHolder> {

    private List<Rubrique> rubriqueList;

    public RubriqueAdapter(List<Rubrique> rubriqueList) {
        this.rubriqueList = rubriqueList;
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
        Rubrique rubrique = rubriqueList.get(position);
        holder.titre.setText(rubrique.getTitre());
        holder.horaire.setText(rubrique.getHoraire());
        holder.intervenant.setText("Avec : " + rubrique.getIntervenant());
    }

    @Override
    public int getItemCount() {
        return rubriqueList.size();
    }

    static class RubriqueViewHolder extends RecyclerView.ViewHolder {
        TextView titre, horaire, intervenant;

        public RubriqueViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titreRubrique);
            horaire = itemView.findViewById(R.id.horaireRubrique);
            intervenant = itemView.findViewById(R.id.intervenantRubrique);
        }
    }
}
