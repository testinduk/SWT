package com.example.figma.controller.timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GeAdapter extends RecyclerView.Adapter<GeAdapter.ViewHolder> {
    private List<Board> gefieldList;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private List<String> fieldValues = new ArrayList<>();

    public GeAdapter(List<Board> gefieldList) {
        this.gefieldList = gefieldList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Board field = gefieldList.get(position);
        // Bind the data to the ViewHolder
        holder.bind(field);
    }

    @Override
    public int getItemCount() {
        return gefieldList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button fieldName;
        private Button fieldValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.timeTableNameDetail);
            fieldValue = itemView.findViewById(R.id.timeTableTimeDetail);
        }

        public void bind(Board field) {
            String sub_name = field.getFieldName();
            String sub_value = String.valueOf(field.getFieldValue());
            fieldName.setText(sub_name);
            fieldValue.setText(sub_value);
        }
    }
}
