package com.example.figma.controller.chat;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Board> mChatList;
    private Context context;
    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;
    private String senderUUID;

    public ChatAdapter(List<Board> ChatList, String senderUUID){
        mChatList = ChatList;
        this.senderUUID = senderUUID;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_SENDER){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender_chatting, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver_chatting, parent, false);
        }
        ChatViewHolder holder = new ChatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        Board chat = mChatList.get(position);
        holder.chattingName.setText(mChatList.get(position).getUserName());
        holder.chattingTime.setText(mChatList.get(position).getTimestamp());
        holder.chattingContent.setText(mChatList.get(position).getContent());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.chattingContent.getLayoutParams();

        if(getItemViewType(position) == VIEW_TYPE_SENDER){
            //오른쪽 정렬
            params.gravity = Gravity.END;
        }else {
            //왼쪽 정렬
            params.gravity = Gravity.START;
        }

        holder.chattingContent.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public int getItemViewType(int position){
        Board chat = mChatList.get(position);
        if(chat.getUserName().equals(senderUUID)){
            Log.e("senderUUID", senderUUID);
            Log.e("getUserName",chat.getUserName());
            return VIEW_TYPE_SENDER;
        }else {
            return VIEW_TYPE_RECEIVER;
        }
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chattingContent, chattingTime,chattingName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chattingContent = itemView.findViewById(R.id.receiver_chattingContent);
            chattingTime = itemView.findViewById(R.id.receiver_chattingTime);
            chattingName = itemView.findViewById(R.id.receiver_chattingName);

        }
    }
}
