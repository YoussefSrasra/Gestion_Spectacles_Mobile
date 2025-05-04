package com.example.hafleti.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hafleti.R;

import java.util.List;

public class TypeGridAdapter extends RecyclerView.Adapter<TypeGridAdapter.TypeViewHolder> {

    public interface OnTypeClickListener {
        void onTypeClick(String type);
    }

    private List<String> typeList;
    private OnTypeClickListener listener;

    public TypeGridAdapter(List<String> typeList, OnTypeClickListener listener) {
        this.typeList = typeList;
        this.listener = listener;
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_card, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        String type = typeList.get(position);
        holder.typeText.setText(type);
        holder.card.setOnClickListener(v -> listener.onTypeClick(type));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    static class TypeViewHolder extends RecyclerView.ViewHolder {
        TextView typeText;
        CardView card;

        public TypeViewHolder(View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.type_text);
            card = itemView.findViewById(R.id.type_card);
        }
    }
}