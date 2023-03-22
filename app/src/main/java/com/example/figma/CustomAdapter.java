package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<User> arrayList;
    private Context context;


    public CustomAdapter(ArrayList<User> arrayList, Context context) {
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
//        holder.tv_id.setText(arrayList.get(position).getId());
//        holder.tv_pw.setText(String.valueOf(arrayList.get(position).getPw()));
//        holder.tv_userName.setText(arrayList.get(position).getUserName());
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.tv_content.setText(arrayList.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageButton iv_profile;
//        Button tv_id;
//        Button tv_pw;
//        Button tv_userName;
        Button tv_title;
        Button tv_content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
//            this.tv_id = itemView.findViewById(R.id.tv_id);
//            this.tv_pw = itemView.findViewById(R.id.tv_pw);
//            this.tv_userName = itemView.findViewById(R.id.tv_userName);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
