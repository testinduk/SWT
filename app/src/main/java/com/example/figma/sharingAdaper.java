package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class sharingAdaper extends RecyclerView.Adapter<sharingAdaper.SharingViewHolder> {

    private ArrayList<sharing_DB> arrayList;
    private Context context;

    public sharingAdaper(ArrayList<sharing_DB> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SharingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sharing_writing, parent, false);
        SharingViewHolder holder = new SharingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SharingViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getTitle())
                .into(holder.imageView);
        holder.editTextTextPersonName.setText(arrayList.get(position).getTitle());
        holder.editTextTextPersonName1.setText(arrayList.get(position).getContent());

    }

    @Override
    public int getItemCount() {

        return (arrayList != null ? arrayList.size() : 0);
    }

    public class SharingViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        EditText editTextTextPersonName;
        EditText editTextTextPersonName1;

    public SharingViewHolder(@NonNull View itemview) {
        super(itemview);
        this.editTextTextPersonName = itemView.findViewById(R.id.editTextTextPersonName);
        this.editTextTextPersonName1 = itemView.findViewById(R.id.editTextTextPersonName1);
        }
    }
}
