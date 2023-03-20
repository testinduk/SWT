package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<sharing_DB> arrayList;
    private Context context;

    public CustomAdapter(ArrayList<sharing_DB> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sharing_list_recycler,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.list_profile);
        holder.list_title.setText(String.valueOf(arrayList.get(position).getTitle()));
        holder.list_name.setText(String.valueOf(arrayList.get(position).getUid()));

    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView list_profile;
        TextView list_title;
        TextView list_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.list_profile = itemView.findViewById(R.id.list_profile);
            this.list_title = itemView.findViewById(R.id.list_title);
            this.list_name = itemView.findViewById(R.id.list_name);
        }
    }
}
