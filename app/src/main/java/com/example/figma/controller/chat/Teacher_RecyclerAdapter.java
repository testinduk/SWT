package com.example.figma.controller.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Teacheritem;

import java.util.ArrayList;

public class Teacher_RecyclerAdapter extends RecyclerView.Adapter<Teacher_RecyclerAdapter.ViewHolder> {
    private ArrayList<Teacheritem> mTeacherList;
    
    @NonNull
    @Override
    public Teacher_RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Teacher_RecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mTeacherList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size();
    }

    public void setTeacherList(ArrayList<Teacheritem> list) {
        this.mTeacherList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.profile);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
        }

        public void onBind(Teacheritem item) {
            profile.setImageResource(item.getResourceId());
            name.setText(item.getName());
            number.setText(item.getNumber());
        }
    }
}
