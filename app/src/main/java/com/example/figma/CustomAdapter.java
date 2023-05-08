package com.example.figma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<Sharing_writing_DB> arrayList;
    private Context context;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



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
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getSharing_image())
                .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_studentNumber.setText(arrayList.get(position).getStudentNumber());
        holder.tv_userName.setText(arrayList.get(position).getUserName());
        holder.tv_time.setText(arrayList.get(position).getSharing_time());

        String userName = arrayList.get(position).getUserName();
        String title = arrayList.get(position).getTitle();
        String content = arrayList.get(position).getContent();
        String sharing_image = arrayList.get(position).getSharing_image();

        String idToken = arrayList.get(position).getIdToken();
        String shar_key = arrayList.get(position).getKey(); //키값 가져오기
        String sharing_time = arrayList.get(position).getSharing_time();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("sharing Board");


        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent shar_intent = new Intent(context, sharing_details.class);
                shar_intent.putExtra("username", userName);
                shar_intent.putExtra("title", title);
                shar_intent.putExtra("content", content);
                shar_intent.putExtra("idToken", idToken);
                shar_intent.putExtra("key",shar_key);
                shar_intent.putExtra("image",sharing_image);
                shar_intent.putExtra("time",sharing_time);

                context.startActivity(shar_intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        Button tv_title;
        Button tv_studentNumber;
        Button tv_userName;
        Button tv_detail;
        Button tv_time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_studentNumber = itemView.findViewById(R.id.tv_studentNumber);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);
            this.tv_detail = itemView.findViewById(R.id.tv_detail);
            this.tv_time = itemView.findViewById(R.id.tv_time);
        }

    }
}