package com.example.figma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class notice_adapter extends RecyclerView.Adapter<notice_adapter.ViewHolder> {

    private ArrayList<notice_DB> arrayList;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    public notice_adapter(ArrayList<notice_DB> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public notice_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list_recycler, parent, false);
        notice_adapter.ViewHolder holder = new notice_adapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull notice_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_studentNumber.setText(arrayList.get(position).getStudentNumber());
        holder.tv_userName.setText(arrayList.get(position).getUserName());

        String userName = arrayList.get(position).getUserName();
        String title = arrayList.get(position).getTitle();
        String content = arrayList.get(position).getContent();




        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("notice Board");


        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String shar_key = databaseReference.getKey();
//                Log.i("log", arrayList.get(position).getShar_key());
                Intent shar_intent = new Intent(context, sharing_details.class);
                shar_intent.putExtra("username", userName);
                shar_intent.putExtra("title", title);
                shar_intent.putExtra("content", content);

                context.startActivity(shar_intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton iv_profile;
        Button tv_title;
        Button tv_studentNumber;
        Button tv_userName;

        Button tv_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_title = itemView.findViewById(R.id.tv_title);

            this.tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);

            this.tv_detail = itemView.findViewById(R.id.tv_detail);
        }

    }
}
