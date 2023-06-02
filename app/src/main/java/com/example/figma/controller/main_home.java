package com.example.figma.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.figma.R;
import com.example.figma.controller.bulletin.bulletin_board;
import com.example.figma.controller.chat.chatting_main;
import com.example.figma.controller.mypage.mypage;
import com.example.figma.controller.notice.main_adapter;
import com.example.figma.controller.notice.notice_list;
import com.example.figma.controller.notice.notice_writing;
import com.example.figma.controller.sharing.sharing_board;
import com.example.figma.controller.timetable.My_time_table;
import com.example.figma.controller.timetable.Timetable;
import com.example.figma.model.notice_DB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class main_home extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;

    private ArrayList<String> tabNames = new ArrayList<>();

    private ArrayList<String> timetable_name = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<notice_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int num_page = 6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        //Fragment 연결
        Timetable timetable = new Timetable();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.time_frame, timetable);
        ft.commit();



        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("notice Board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    notice_DB user = snapshot.getValue(notice_DB.class);
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

        adapter = new main_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);


        // 내 시간표 설정
        Button btn_my_time = findViewById(R.id.btn_my_time);
        btn_my_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_time_table.class);
                startActivity(intent);
            }
        });


        // (공지)더보기 버튼
        Button show_notice_more = findViewById(R.id.show_notice_more);
        show_notice_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });

        // (공지)등록하기 버튼
        Button show_notice_writing = findViewById(R.id.show_notice_writing);
        show_notice_writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_writing.class);
                startActivity(intent);
            }
        });


        //채팅 버튼
        ImageButton chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chatting_main.class);
                startActivity(intent);
            }
        });

        // 나눔 버튼
        ImageButton sharingButton = findViewById(R.id.sharingButton);
        sharingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_home.class);
                startActivity(intent);
            }
        });

        // 게시판 버튼
        ImageButton boardButton = findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);
            }
        });

        // 시간표 설정 버튼
//        Button btn_timetable = findViewById(R.id.btn_timetable);
//        btn_timetable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Timetable.class);
//                startActivity(intent);
//            }
//        });

        // 마이페이지 버튼
        ImageButton mypageButton = findViewById(R.id.mypageButton);
        mypageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
            }
        });











//        btn_timetable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Timetable.class);
//                startActivity(intent);
//            }
//        });

//        // Fragment 부분
//        // ViewPager2
//        mPager = findViewById(R.id.viewpager);
//        // Adapter
//        pagerAdapter = new ViewAdapter(this, num_page);
//        mPager.setAdapter(pagerAdapter);
//        //ViewPager Setting
//        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//
//        /**
//         * 이 부분 조정하여 처음 시작하는 이미지 설정.
//         * 2000장 생성하였으니 현재위치 1002로 설정하여
//         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
//         */
//
//        mPager.setCurrentItem(0); //시작 지점
//        mPager.setOffscreenPageLimit(6); //최대 이미지 수
//
//        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                if (positionOffsetPixels == 0) {
//                    mPager.setCurrentItem(position);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//            }
//        });
//





//        tabLayout 선언
//        tabLayout = findViewById(R.id.time_tab);
//        tabLyout와 viewPager 연결
//        new TabLayoutMediator(tabLayout, mPager, (tab, position) -> {
//
//            int index = position + 1;
//
//            String title;
//
//            if (index %2 == 0) {
//                //짝수 인덱스는 B로 끝남
//                title = (index / 2) + "B";
//            } else {
//                //홀수 인덱스는 A로 끝남
//                title = (index / 2 + 1) + "A";
//            }
//
//            tab.setText(title);
//        }).attach();



    }
}
