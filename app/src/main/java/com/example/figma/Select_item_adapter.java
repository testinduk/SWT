package com.example.figma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Select_item_adapter extends RecyclerView.Adapter<Select_item_adapter.ViewHolder> {
    private List<DataField> item_list;

    public Select_item_adapter(List<DataField> item_list) {
        this.item_list = item_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Select_item_adapter.ViewHolder holder, int position) {
        DataField item = item_list.get(position);
        // Bind the data to the ViewHolder
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView select_item_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            select_item_text = itemView.findViewById(R.id.select_item_text);
        }

        public void bind(DataField data) {
            select_item_text.setText(String.valueOf(data.getFieldValue()));

        }
    }
}

