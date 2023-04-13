package com.example.figma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class bulletin_board_adapter extends RecyclerView.Adapter<bulletin_board_adapter.ViewHolder> {

    private ArrayList<bulletin_DB> arrayList;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public bulletin_board_adapter(ArrayList<bulletin_DB> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public bulletin_board_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_bullentin_board, parent, false);
        bulletin_board_adapter.ViewHolder holder = new bulletin_board_adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bulletin_board_adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getBulletin_image())
                .into(holder.iv_profile);
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_studentNumber.setText(arrayList.get(position).getStudentNumber());
        holder.tv_userName.setText(arrayList.get(position).getUserName());

        String userName = arrayList.get(position).getUserName();
        String title = arrayList.get(position).getTitle();
        String content = arrayList.get(position).getContent();
        String idToken = arrayList.get(position).getIdToken();
        String bulletin_key = arrayList.get(position).getKey(); //키값 가져오기
        String bulletin_image = arrayList.get(position).getBulletin_image(); // 이미지 가져오기

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("bulletin Board");

        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shar_intent = new Intent(context, bulletin_board_details.class);
                shar_intent.putExtra("username", userName);
                shar_intent.putExtra("title", title);
                shar_intent.putExtra("content", content);
                shar_intent.putExtra("idToken", idToken);
                shar_intent.putExtra("key", bulletin_key);
                shar_intent.putExtra("image",bulletin_image);

                context.startActivity(shar_intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
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