package com.example.figma.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.figma.R;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.chat.ChatPerson;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.notice.MainAdapter;
import com.example.figma.controller.notice.NoticeList;
import com.example.figma.controller.notice.NoticeWriting;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.controller.timetable.MyTimeTable;
import com.example.figma.controller.timetable.TimeTable;
import com.example.figma.model.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import com.example.figma.databinding.MainHomeBinding;

public class MainHome extends AppCompatActivity {
    private MainHomeBinding mBinding;
    private ArrayList<String> tabNames = new ArrayList<>();
    private ArrayList<String> timetable_name = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int num_page = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = MainHomeBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //Fragment 연결
        TimeTable timetable = new TimeTable();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.time_frame, timetable);
        ft.commit();


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

        adapter = new MainAdapter(arrayList, this);
        mBinding.noticeBoardRecyclerView.setAdapter(adapter);
        // 내 시간표 설정
        mBinding.btnMyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyTimeTable.class);
                startActivity(intent);
            }
        });
        // (공지)더보기 버튼
        mBinding.showNoticeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });

        // (공지)등록하기 버튼
        mBinding.showNoticeWriting.setOnClickListener(new View.OnClickListener() {
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

    }
}
