package com.example.figma.controller.chat;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.MainHome;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.databinding.BulletinBoardEditBinding;
import com.example.figma.model.Teacheritem;

import java.util.ArrayList;

import com.example.figma.databinding.ChatPersonBinding;

public class ChatPerson extends AppCompatActivity {
    private ChatPersonBinding mBinding;

    private TeacherRecyclerAdapter mRecyclerAdapter; // 아래에 있는 mRecyclerAdapter과 연결
    private ArrayList<Teacheritem> mTeacherItem;

    // 다른페이지에서 채팅 버튼 눌렀을 때
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ChatPersonBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

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

        //Adapter와 LayoutManager 연결
        /* initiate adapter */
        mRecyclerAdapter = new TeacherRecyclerAdapter();

        /* initiate recyclerview */
        mBinding.TeacherList.setAdapter(mRecyclerAdapter);
        mBinding.TeacherList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.TeacherList.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data 프로필 정보 ㅅ*/
        mTeacherItem = new ArrayList<>();
        for(int i=1;i<=10;i++){
            if(i%2==0)
                mTeacherItem.add(new Teacheritem(R.drawable.profile,"황선철교수님","02-950-0000"));
            else
                mTeacherItem.add(new Teacheritem(R.drawable.profile,i+"번째 사람",i+"번째 상태메시지"));

        }
        mRecyclerAdapter.setTeacherList(mTeacherItem);

    }
}

