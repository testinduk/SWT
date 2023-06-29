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

import java.util.ArrayList;
import java.util.List;

public class MypageSharingAdapter extends RecyclerView.Adapter<MypageSharingAdapter.MypageViewHolder> {
    private List<Board> mMypageSharing;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public MypageSharingAdapter(List<Board> arrayList, Mypage mypage) {
        this.mMypageSharing = arrayList;
    }

    @NonNull
    @Override
    public MypageSharingAdapter.MypageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypage, parent, false);
        MypageViewHolder holder = new MypageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MypageSharingAdapter.MypageViewHolder holder, int position) {
        Board board = mMypageSharing.get(position);

        databaseReference = FirebaseDatabase.getInstance().getReference("sharing Board");

        Query query = databaseReference.orderByChild("idToken").equalTo(board.getIdToken());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> titles = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Board matchingBoard = snapshot1.getValue(Board.class);
                    if(matchingBoard != null){
                        String title = matchingBoard.getTitle();
                        titles.add(title);
                    }
                }
                if(!titles.isEmpty()){
                    String title = titles.get(holder.getAdapterPosition());
                    holder.myListTitle.setText(title);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return (mMypageSharing != null ? mMypageSharing.size():0);
    }

    public class MypageViewHolder extends RecyclerView.ViewHolder {
        public TextView myListTitle;
        public Button myListDetail;
        public MypageViewHolder(@NonNull View itemView) {
            super(itemView);
            myListTitle = itemView.findViewById(R.id.myListTitle);
        }
    }
}
