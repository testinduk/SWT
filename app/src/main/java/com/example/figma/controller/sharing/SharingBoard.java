package com.example.figma.controller.sharing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.controller.MainHome;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.chat.ChatPerson;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.databinding.SharingBoardBinding;
import com.example.figma.model.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SharingBoard extends Activity {
    private SharingBoardBinding mBinding;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = SharingBoardBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.sharingBoardRecycler.setHasFixedSize(true); //리사이클러뷰의 크기 변경이 일정하다는 것을 사용자의 입력으로 확인
        layoutManager = new LinearLayoutManager(this); //아이템 배치 방향을 수평으로 설정
        mBinding.sharingBoardRecycler.setLayoutManager(layoutManager);  //리사이클러뷰 레이아웃 매니저를 레이아웃 매니저로 지정
        arrayList = new ArrayList<>(); //arraylist를 리턴(원소 추가 가능)

        mBinding.sharingBoardRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mBinding.sharingBoardRecycler.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance(); //기본 FirebaseDatabase 인스턴스 가져오기(데이터베이스란 이름으로 객체 생성)

        databaseReference = database.getReference("sharing Board"); //sharing Board에서 데이터 레퍼런스 가져와 선언

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() { //firebase 데이터베이스 읽기(한 번만 호출되고 즉시 삭제되는 콜백이 필요한 경우에 사용)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // 이벤트 발생 시점에 특정 경로에 있던 콘텐츠의 정적 스냅샷을 읽음
                arrayList.clear(); //초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey(); //스냅샷에서의 키를 uid에 저장
                    Board user = snapshot.getValue(Board.class); //user에 sharing_writing_DB에서 얻은 값을 저장
                    arrayList.add(user); //추가
                }
                adapter.notifyDataSetChanged(); //어뎁터와 연결된 원본데이터의 값이 변경됨을 알려 리스트뷰 목록 갱신
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException())); //에러문 출력
            }
        });

        adapter = new SharingAdapter(arrayList, this);
        mBinding.sharingBoardRecycler.setAdapter(adapter); //리사이클러뷰에 어댑터 연결
        //검색 기능
        mBinding.sharingBoardSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mBinding.sharingBoardSearch.getText().toString().toLowerCase();
                ArrayList<Board> filteredList = new ArrayList<>();
                for(Board item : arrayList){
                    if(item.getTitle().toLowerCase().contains(searchText)
                            || (item.getContent().toLowerCase().contains(searchText))
                            || (item.getUserName().toLowerCase().contains(searchText))
                            || (item.getStudentNumber().toLowerCase().contains(searchText))) {
                        filteredList.add(item);
                    }
                }
                adapter = new SharingAdapter(filteredList, SharingBoard.this);
                mBinding.sharingBoardRecycler.setAdapter(adapter);
            }
        });
        // 글쓰기 버튼
        mBinding.writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingWriting.class);
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
