package com.example.figma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Major_adapter extends RecyclerView.Adapter<Major_adapter.ViewHolder> {
    private List<DataField> fieldList;

    public Major_adapter(List<DataField> fieldList) {
        this.fieldList = fieldList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_rcy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataField field = fieldList.get(position);
        // Bind the data to the ViewHolder
        holder.bind(field);
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fieldNameTextView;
        private TextView fieldValueTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldNameTextView = itemView.findViewById(R.id.time_rcy_detail);
            fieldValueTextView = itemView.findViewById(R.id.time_rcy_detail2);
        }

        public void bind(DataField field) {
            fieldNameTextView.setText(field.getFieldName());
            fieldValueTextView.setText(String.valueOf(field.getFieldValue()));


            fieldValueTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
