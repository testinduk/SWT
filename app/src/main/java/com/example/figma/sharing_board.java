package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Locale;

public class sharing_board extends Activity {

    //선언
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Sharing_writing_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Button search_Button; //검색 버튼
    private EditText searchView; //검색어 입력

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_board); //레이아웃의 sharing_board 부분 참조

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결

        recyclerView.setHasFixedSize(true); //리사이클러뷰의 크기 변경이 일정하다는 것을 사용자의 입력으로 확인
        layoutManager = new LinearLayoutManager(this); //아이템 배치 방향을 수평으로 설정
        recyclerView.setLayoutManager(layoutManager);  //리사이클러뷰 레이아웃 매니저를 레이아웃 매니저로 지정
        arrayList = new ArrayList<>(); //arraylist를 리턴(원소 추가 가능)

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        search_Button = findViewById(R.id.search_Button);
        searchView = findViewById(R.id.searchView);


        database = FirebaseDatabase.getInstance(); //기본 FirebaseDatabase 인스턴스 가져오기(데이터베이스란 이름으로 객체 생성)

        databaseReference = database.getReference("sharing Board"); //sharing Board에서 데이터 레퍼런스 가져와 선언

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() { //firebase 데이터베이스 읽기(한 번만 호출되고 즉시 삭제되는 콜백이 필요한 경우에 사용)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // 이벤트 발생 시점에 특정 경로에 있던 콘텐츠의 정적 스냅샷을 읽음
                arrayList.clear(); //초기화

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey(); //스냅샷에서의 키를 uid에 저장
                    Sharing_writing_DB user = snapshot.getValue(Sharing_writing_DB.class); //user에 sharing_writing_DB에서 얻은 값을 저장
                    arrayList.add(user); //추가

                }

                adapter.notifyDataSetChanged(); //어뎁터와 연결된 원본데이터의 값이 변경됨을 알려 리스트뷰 목록 갱신

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });


        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter); //리사이클러뷰에 어댑터 연결

        //검색 기능
        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchView.getText().toString().toLowerCase();

                ArrayList<Sharing_writing_DB> filteredList = new ArrayList<>();
                for(Sharing_writing_DB item : arrayList){
                    if(item.getTitle().toLowerCase().contains(searchText)
                            || (item.getContent().toLowerCase().contains(searchText))
                            || (item.getUserName().toLowerCase().contains(searchText))
                            || (item.getStudentNumber().toLowerCase().contains(searchText))) {
                        filteredList.add(item);
                    }
                }
                adapter = new CustomAdapter(filteredList, sharing_board.this);
                recyclerView.setAdapter(adapter);
            }
        });

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
