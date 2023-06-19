package com.example.figma.controller.timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;


import java.util.List;

public class SelectItemAdapter extends RecyclerView.Adapter<SelectItemAdapter.ViewHolder> {
    private List<Board> item_list;

    public SelectItemAdapter(List<Board> item_list) {
        this.item_list = item_list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectItemAdapter.ViewHolder holder, int position) {
        Board item = item_list.get(position);
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
            select_item_text = itemView.findViewById(R.id.selectMajorName);
        }

        public void bind(Board data) {
            select_item_text.setText(String.valueOf(data.getFieldValue()));

        }
    }
}
