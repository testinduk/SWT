package com.example.figma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Sharing_writing_DB> arrayList;
    private Context context;


    public CustomAdapter(ArrayList<Sharing_writing_DB> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sharing_list_recycler, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_studentNumber.setText(arrayList.get(position).getStudentNumber());
        holder.tv_username.setText(arrayList.get(position).getUserName());
        holder.tv_userName.setText(arrayList.get(position).getUserName());
        holder.tv_studentNumber.setText((arrayList.get(position).getStudentNumber()));

        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, sharing_details.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageButton iv_profile;
        Button tv_title;
        Button tv_studentNumber;
        Button tv_username;
        Button tv_userName;

        Button tv_detail;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_title = itemView.findViewById(R.id.tv_title);

            this.tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);
            this.tv_username = itemView.findViewById(R.id.tv_userName);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);
            this.tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);

            this.tv_detail = itemView.findViewById(R.id.tv_detail);
        }

    }
}
