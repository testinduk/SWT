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

public class TeacherRecyclerAdapter extends RecyclerView.Adapter<TeacherRecyclerAdapter.ViewHolder> {
    private ArrayList<Teacheritem> mTeacherList;
    
    @NonNull
    @Override
    public TeacherRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherRecyclerAdapter.ViewHolder holder, int position) {
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
