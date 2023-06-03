package com.example.figma.controller.notice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Board> arrayList;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    public MainAdapter(ArrayList<Board> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler, parent, false);
        MainAdapter.ViewHolder holder = new MainAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_userName.setText(arrayList.get(position).getUserName());
        holder.tv_time.setText(arrayList.get(position).getNotice_time());

        String userName = arrayList.get(position).getUserName();
        String title = arrayList.get(position).getTitle();
        String content = arrayList.get(position).getContent();
        String time = arrayList.get(position).getNotice_time();
        String idToken = arrayList.get(position).getIdToken();
        String notice_key = arrayList.get(position).getKey();
        String notice_image = arrayList.get(position).getNotice_image();


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("notice Board");

    }



    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_userName, tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_time = itemView.findViewById(R.id.tv_time);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);
        }
    }
}
