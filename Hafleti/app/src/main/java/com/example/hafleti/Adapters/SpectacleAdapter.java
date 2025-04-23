package com.example.hafleti.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hafleti.Models.Spectacle;
import com.example.hafleti.R;
import com.example.hafleti.Utils.ImageUtils;

import java.util.List;

public class SpectacleAdapter extends RecyclerView.Adapter<SpectacleAdapter.ViewHolder> {

    private final Context context;
    private final List<Spectacle> spectacleList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Spectacle spectacle);
    }

    public SpectacleAdapter(Context context, List<Spectacle> spectacleList, OnItemClickListener listener) {
        this.context = context;
        this.spectacleList = spectacleList;
        this.listener = listener;
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
        holder.title.setText(spectacle.getTitre());

        byte[] imageBytes = ImageUtils.decodeBase64Image(spectacle.getImage());

        if (imageBytes != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(imageBytes)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.error_image);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(spectacle));
    }

    @Override
    public int getItemCount() {
        return spectacleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSpectacle);
            title = itemView.findViewById(R.id.titreSpectacle);
        }
    }
}
