
package com.example.figma.controller.notice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.controller.MainHome;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.chat.ChatPerson;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.databinding.NoticeBoardBinding;
import com.example.figma.model.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class NoticeList extends AppCompatActivity {
    private NoticeBoardBinding mBinding;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = NoticeBoardBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.noticeBoardRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mBinding.noticeBoardRecyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("notice Board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
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

        adapter = new NoticeAdapter(arrayList, this);
        mBinding.noticeBoardRecyclerView.setAdapter(adapter);
        // 검색
        mBinding.noticeBoardSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice_search = mBinding.noticeBoardSearch.getText().toString().toLowerCase();

                ArrayList<Board> filteredList = new ArrayList<>();
                filteredList.clear();
                for (Board notice : arrayList) {
                    if(notice.getTitle().toLowerCase().contains(notice_search)
                            || (notice.getUserName().toLowerCase().contains(notice_search))
                            || (notice.getStudentNumber().toLowerCase().contains(notice_search))
                            || (notice.getContent().toLowerCase().contains((notice_search)))) {
                        filteredList.add(notice);
                    }
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(filteredList);
                adapter = new NoticeAdapter(filteredList, NoticeList.this);
                mBinding.noticeBoardRecyclerView.setAdapter(adapter);

            }
        });

        // 글쓰기 버튼
        mBinding.writingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeWriting.class);
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
