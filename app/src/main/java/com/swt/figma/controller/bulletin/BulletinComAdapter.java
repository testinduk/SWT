package com.swt.figma.controller.bulletin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swt.figma.R;
import com.swt.figma.model.Board;

import java.util.ArrayList;

public class BulletinComAdapter extends RecyclerView.Adapter<BulletinComAdapter.ViewHolder> {
    private ArrayList<Board> arrayList;
    private Context context;

    public BulletinComAdapter(ArrayList<Board> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_userName.setText(arrayList.get(position).getName());
        holder.tv_studentNumber.setText(arrayList.get(position).getStudentNumber());
        holder.tv_content.setText(arrayList.get(position).getContent());
        holder.tv_time.setText(arrayList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_userName, tv_studentNumber, tv_content, tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_studentNumber = itemView.findViewById(R.id.commentStudentNumber);
            this.tv_userName = itemView.findViewById(R.id.commentUserName);
            this.tv_content = itemView.findViewById(R.id.commentContent);
            this.tv_time = itemView.findViewById(R.id.commentTime);
        }
    }
}
