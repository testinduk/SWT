package com.example.figma.controller.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChatMainAdapter extends RecyclerView.Adapter<ChatMainAdapter.StudentViewHolder> {
    private List<Board> mChatList;
    private Context context;

    private FirebaseFirestore database;

    public ChatMainAdapter(List<Board> ChatList){
        mChatList = ChatList;
    }
    public ChatMainAdapter(Context context, List<Board> ChatList){
        this.context = context;
        mChatList =ChatList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        StudentViewHolder holder = new StudentViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMainAdapter.StudentViewHolder holder, int position) {
        holder.chattingContent.setText(mChatList.get(position).getUserName());
        holder.chattingName.setText(mChatList.get(position).getStudentNumber());
        holder.chattingTime.setText(mChatList.get(position).getStudentNumber());

        String chattingContent = mChatList.get(position).getUserName();
        String chattingName = mChatList.get(position).getStudentNumber();
        String chattingTime = mChatList.get(position).getProfileUri();

        database = FirebaseFirestore.getInstance();

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView chattingContent, chattingName, chattingTime;

        public StudentViewHolder(View itemView) {
            super(itemView);
            chattingContent = itemView.findViewById(R.id.chattingContent);
            chattingName = itemView.findViewById(R.id.chattingName);
            chattingTime = itemView.findViewById(R.id.chattingTime);
        }
    }
}
