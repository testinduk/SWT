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

public class AssistantAdapter extends RecyclerView.Adapter<AssistantAdapter.AssistantViewHolder> {
    private List<Board> mAssistantList;
    private Context context;

    private FirebaseFirestore database;

    public AssistantAdapter(List<Board> assistantList){
        mAssistantList = assistantList;
    }

    public AssistantAdapter(Context context, List<Board> AssistantList){
        this.context = context;
        mAssistantList = AssistantList;
    }

    @NonNull
    @Override
    public AssistantAdapter.AssistantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        AssistantAdapter.AssistantViewHolder holder = new AssistantAdapter.AssistantViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssistantAdapter.AssistantViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mAssistantList.get(position).getProfileUri())
                .into(holder.photo);
        holder.nameTextView.setText(mAssistantList.get(position).getUserName());
        holder.numberTextView.setText(mAssistantList.get(position).getStudentNumber());

        String userName = mAssistantList.get(position).getUserName();
        String studentNumber = mAssistantList.get(position).getStudentNumber();
        String profile = mAssistantList.get(position).getProfile();

        database = FirebaseFirestore.getInstance();

        holder.chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat_intent = new Intent(context, ChattingMain1.class);
                chat_intent.putExtra("userName", userName);
                chat_intent.putExtra("studentNumber",studentNumber);
                chat_intent.putExtra("photo", profile);
                context.startActivity(chat_intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssistantList.size();
    }

    public class AssistantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, numberTextView;
        ImageView photo;
        Button chatting;
        public AssistantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.chatName);
            numberTextView = itemView.findViewById(R.id.chatNumber);
            photo = itemView.findViewById(R.id.chatProfile);
            chatting = itemView.findViewById(R.id.chatting);
        }
    }
}
