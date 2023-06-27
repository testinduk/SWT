package com.example.figma.controller.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MypageNoticeAdapter extends RecyclerView.Adapter<MypageNoticeAdapter.MypageViewHolder> {
    private List<Board> mMypageNotice;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public MypageNoticeAdapter(List<Board> arrayList, Mypage mypage) {
        this.mMypageNotice = arrayList;
    }

    @NonNull
    @Override
    public MypageNoticeAdapter.MypageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage, parent, false);
        MypageViewHolder holder = new MypageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MypageNoticeAdapter.MypageViewHolder holder, int position) {
        Board board = mMypageNotice.get(position);

        databaseReference = FirebaseDatabase.getInstance().getReference("notice Board");

        Query query = databaseReference.orderByChild("idToken").equalTo(board.getIdToken());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Board matchingBoard = snapshot1.getValue(Board.class);
                    if(matchingBoard != null){
                        String title = matchingBoard.getTitle();
                        holder.myListTitle.setText(title);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return (mMypageNotice != null ? mMypageNotice.size():0);
    }

    public class MypageViewHolder extends RecyclerView.ViewHolder {
        public TextView myListTitle;
        public Button myListDetail;
        public MypageViewHolder(@NonNull View itemView) {
            super(itemView);
            myListTitle = itemView.findViewById(R.id.myListTitle);
            myListDetail = itemView.findViewById(R.id.myListDetail);

        }
    }
}
