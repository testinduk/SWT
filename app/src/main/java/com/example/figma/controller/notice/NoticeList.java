
package com.example.figma.controller.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.chat.ChatPerson;
import com.example.figma.controller.MainHome;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.model.notice_DB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class NoticeList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<notice_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    EditText searchView;
    Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        search_button = findViewById(R.id.search_Button);    // 검색 버튼
        searchView = findViewById(R.id.searchView);          // 검색어 입력
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

        adapter = new NoticeAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);


        // 검색
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice_search = searchView.getText().toString().toLowerCase();

                ArrayList<notice_DB> filteredList = new ArrayList<>();
                filteredList.clear();
                for (notice_DB notice : arrayList) {
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
                recyclerView.setAdapter(adapter);

            }
        });

        // 글쓰기 버튼
        Button writingButton = findViewById(R.id.writingButton);
        writingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeWriting.class);
                startActivity(intent);
            }
        });

        //채팅 버튼
        ImageButton chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatPerson.class);
                startActivity(intent);
            }
        });

        // 나눔 버튼
        ImageButton sharingButton = findViewById(R.id.sharingButton);
        sharingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });

        // 게시판 버튼
        ImageButton boardButton = findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });

        // 마이페이지 버튼
        ImageButton mypageButton = findViewById(R.id.mypageButton);
        mypageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
    }
}
