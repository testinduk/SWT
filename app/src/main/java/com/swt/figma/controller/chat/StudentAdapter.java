package com.swt.figma.controller.chat;

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
import com.swt.figma.R;
import com.swt.figma.model.Board;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Board> mStudentList;
    private Context context;

    private FirebaseFirestore database;

    public StudentAdapter(Context context, List<Board> studentList){
        this.context = context;
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        StudentViewHolder holder = new StudentViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mStudentList.get(position).getProfileUri())
                .into(holder.photo);
        holder.nameTextView.setText(mStudentList.get(position).getUserName());
        holder.numberTextView.setText(mStudentList.get(position).getStudentNumber());

        String userName = mStudentList.get(position).getUserName();
        String studentNumber = mStudentList.get(position).getStudentNumber();
        String profile = mStudentList.get(position).getProfileUri();
        String receiverUUID = mStudentList.get(position).getReceiverUUID();

        database = FirebaseFirestore.getInstance();

        holder.chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat_intent = new Intent(context, ChatMain.class);
                chat_intent.putExtra("userName", userName);
                chat_intent.putExtra("studentNumber",studentNumber);
                chat_intent.putExtra("photo", profile);
                chat_intent.putExtra("receiverUUID", receiverUUID);

                chat_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(chat_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, numberTextView;
        ImageView photo;
        Button chatting;

        public StudentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.chatName);
            numberTextView = itemView.findViewById(R.id.chatNumber);
            photo = itemView.findViewById(R.id.chatProfile);
            chatting = itemView.findViewById(R.id.chatting);
        }
    }
}
