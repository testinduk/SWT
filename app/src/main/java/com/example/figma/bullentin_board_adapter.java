package com.example.figma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class bullentin_board_adapter extends RecyclerView.Adapter<bullentin_board_adapter.ViewHolder> {
    private ArrayList<bullentin_DB> arrayList;
    private Context context;

    public bullentin_board_adapter(ArrayList<bullentin_DB> arrayList, Context context ){
        this.arrayList = arrayList;
        this.context =context;
    }


    @NonNull
    @Override
    public bullentin_board_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull bullentin_board_adapter.ViewHolder holder, int position) {
        holder.item_title_text.setText(arrayList.get(position).getItem_title_text());
        holder.item_name_text.setText(arrayList.get(position).getItem_name_text());


    }

    @Override
    public int getItemCount() { return (arrayList != null ? arrayList.size():0);}

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button item_title_text;
        Button item_name_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_title_text = itemView.findViewById(R.id.item_title_text);
            this.item_name_text = itemView.findViewById(R.id.item_name_text);

        }
    }
}
