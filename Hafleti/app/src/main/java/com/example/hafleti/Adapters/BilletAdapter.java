/*package com.example.hafleti.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hafleti.Models.Billet;
import com.example.hafleti.R;
import java.util.List;

public class BilletAdapter extends RecyclerView.Adapter<BilletAdapter.ViewHolder> {

    private final List<Billet> billets;

    public BilletAdapter(List<Billet> billets) {
        this.billets = billets;
    }

    @NonNull
    @Override
    public BilletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BilletAdapter.ViewHolder holder, int position) {
        Billet billet = billets.get(position);
        holder.textCategorie.setText(billet.getCategorie().toString());
        holder.textPrix.setText(billet.getPrix().toString() + " DT");
    }

    @Override
    public int getItemCount() {
        return billets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCategorie, textPrix;

        public ViewHolder(View itemView) {
            super(itemView);
            textCategorie = itemView.findViewById(R.id.textCategorie);
            textPrix = itemView.findViewById(R.id.textPrix);
        }
    }
}*/
