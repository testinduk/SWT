package com.example.figma.controller.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Board> mChatList;
    private Context context;

    public ChatAdapter(List<Board> ChatList){
        this.context = context;
        mChatList = ChatList;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatting, parent, false);
        ChatViewHolder holder = new ChatViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        holder.chattingName.setText(mChatList.get(position).getUserName());
        holder.chattingTime.setText(mChatList.get(position).getTimestamp());
        holder.chattingContent.setText(mChatList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chattingContent, chattingTime;
        Button chattingName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chattingContent = itemView.findViewById(R.id.chattingContent);
            chattingTime = itemView.findViewById(R.id.chattingTime);
            chattingName = itemView.findViewById(R.id.chattingName);

        }
    }
}
