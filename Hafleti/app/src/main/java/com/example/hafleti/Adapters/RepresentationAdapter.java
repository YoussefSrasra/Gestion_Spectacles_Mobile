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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.Models.Representation;
import com.example.hafleti.R;

import java.util.List;

public class RepresentationAdapter extends RecyclerView.Adapter<RepresentationAdapter.ViewHolder> {

    private final List<Representation> representations;
    private final Context context;

    public RepresentationAdapter(Context context, List<Representation> representations) {
        this.context = context;
        this.representations = representations;
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
        holder.tvHeure.setText("Heure: " + r.getHeureDebut());

        holder.ivMaps.setOnClickListener(v -> {
            String lieuQuery = r.getLieu().getNom() + " " + r.getLieu().getAdresse();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + lieuQuery));
            intent.setPackage("com.google.android.apps.maps") ;

            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        });
    }


    @Override
    public int getItemCount() {
        return representations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLieu, tvDate, tvHeure;
        ImageView ivMaps;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLieu = itemView.findViewById(R.id.tvLieu);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            ivMaps = itemView.findViewById(R.id.ivMaps);

        }
    }
}
