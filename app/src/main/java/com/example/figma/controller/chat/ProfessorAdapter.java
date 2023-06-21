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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder> {
    private List<Board> mProfessorList;
    private Context context;

    private FirebaseFirestore database;

    public ProfessorAdapter(List<Board> professorList) {
        mProfessorList = professorList;
    }

    public ProfessorAdapter(Context context, List<Board> ProfessorList) {
        this.context = context;
        mProfessorList = ProfessorList;
    }

    @NonNull
    @Override
    public ProfessorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        ProfessorViewHolder holder = new ProfessorViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mProfessorList.get(position).getProfileUri())
                .into(holder.photo);
        holder.nameTextView.setText(mProfessorList.get(position).getUserName());
        holder.numberTextView.setText(mProfessorList.get(position).getStudentNumber());

        String userName = mProfessorList.get(position).getUserName();
        String studentNumber = mProfessorList.get(position).getStudentNumber();
        String profile = mProfessorList.get(position).getProfileUri();

        database = FirebaseFirestore.getInstance();

        holder.chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat_intent = new Intent(context, ChatMain.class);
                chat_intent.putExtra("userName", userName);
                chat_intent.putExtra("studentNumber",studentNumber);
                chat_intent.putExtra("photo", profile);
                context.startActivity(chat_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProfessorList.size();
    }

    public class ProfessorViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, numberTextView;
        ImageView photo;
        Button chatting;

        public ProfessorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.chatName);
            numberTextView = itemView.findViewById(R.id.chatNumber);
            photo = itemView.findViewById(R.id.chatProfile);
            chatting = itemView.findViewById(R.id.chatting);
        }
    }
}

