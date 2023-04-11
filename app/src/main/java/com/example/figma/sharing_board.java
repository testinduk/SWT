package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sharing_board extends Activity {

    //선언
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sharing_writing_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_board); //레이아웃의 sharing_board 부분 참조

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰의 크기 변경이 일정하다는 것을 사용자의 입력으로 확인
        layoutManager = new LinearLayoutManager(this); //아이템 배치 방향을 수평으로 설정
        recyclerView.setLayoutManager(layoutManager);  //리사이클러뷰 레이아웃 매니저를 레이아웃 매니저로 지정
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("sharing Board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    Sharing_writing_DB user = snapshot.getValue(Sharing_writing_DB.class);
                    arrayList.add(user);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });


        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);

        // 글쓰기 버튼
        Button writingButton = findViewById(R.id.writingButton);
        writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_writing.class);
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
                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
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


//    private void readUser() {
//        mDatabase.child("Sharing Board").child("1").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //Get Post object and use the values to update the UI
//                if(snapshot.getValue(sharing_DB.class) !=null){
//                    sharing_DB post = snapshot.getValue(sharing_DB.class);
//                    Log.w("FireBaseData","getData" + post.toString());
//                }else{
//                    Toast.makeText(sharing_board.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //Getting Post failed, Log a message
//                Log.w("FireBaseData", "loadPost:onCancelled");
