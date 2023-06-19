package com.example.figma.controller.bulletin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.controller.MainHome;
import com.example.figma.controller.chat.ChatPerson;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.databinding.BulletinBoardBinding;
import com.example.figma.model.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BulletinBoard extends Activity {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private BulletinBoardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = BulletinBoardBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mBinding.bulletinBoardRecyclerView.setLayoutManager(layoutManager);
        mBinding.bulletinBoardRecyclerView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("bulletin Board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    Board user = snapshot.getValue(Board.class);
                    arrayList.add(user);
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });
        adapter = new BulletinBoardAdapter(arrayList, this);
        mBinding.bulletinBoardRecyclerView.setAdapter(adapter);
        //검색 기능
        mBinding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mBinding.searchBox.getText().toString().toLowerCase();

                ArrayList<Board> filteredList = new ArrayList<>();
                for(Board item : arrayList){
                    if(item.getTitle().toLowerCase().contains(searchText)
                            || (item.getContent().toLowerCase().contains(searchText))
                            || (item.getStudentNumber().toLowerCase().contains(searchText))
                            || (item.getUserName().toLowerCase().contains(searchText))){
                        filteredList.add(item);
                    }
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(filteredList);
                adapter = new BulletinBoardAdapter(filteredList, BulletinBoard.this);
                mBinding.bulletinBoardRecyclerView.setAdapter(adapter);
            }
        });
        // 글쓰기 버튼
        mBinding.writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoardWriting.class);
                startActivity(intent);
            }
        });
        //채팅 버튼
        mBinding.chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatPerson.class);
                startActivity(intent);
            }
        });
        // 나눔 버튼
        mBinding.sharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });
        // 홈 버튼
        mBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
        // 게시판 버튼
        mBinding.boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });
        // 마이페이지 버튼
        mBinding.mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });
        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
    }
}
