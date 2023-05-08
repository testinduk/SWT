package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class bulletin_board extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<bulletin_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button search_Button;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        search_Button = findViewById(R.id.search_Button);
        searchView = findViewById(R.id.editText);


        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("bulletin Board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    bulletin_DB user = snapshot.getValue(bulletin_DB.class);
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


        adapter = new bulletin_board_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);
        //검색 기능
        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchView.getText().toString().toLowerCase();

                ArrayList<bulletin_DB> filteredList = new ArrayList<>();
                for(bulletin_DB item : arrayList){
                    if(item.getTitle().toLowerCase().contains(searchText)
                            || (item.getContent().toLowerCase().contains(searchText))
                            || (item.getStudentNumber().toLowerCase().contains(searchText))
                            || (item.getUserName().toLowerCase().contains(searchText))){
                        filteredList.add(item);
                    }
                }

                // -----시간 정렬 (역순)-----
                Collections.reverse(filteredList);

                adapter = new bulletin_board_adapter(filteredList, bulletin_board.this);
                recyclerView.setAdapter(adapter);
            }
        });

        // 글쓰기 버튼
        Button writingButton = findViewById(R.id.writingButton);
        writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board_writing.class);
                startActivity(intent);
            }
        });

        //채팅 버튼
        ImageButton chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chat_person.class);
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

        // 마이페이지 버튼
        ImageButton mypageButton = findViewById(R.id.mypageButton);
        mypageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_home.class);
                startActivity(intent);
            }
        });
    }


}
