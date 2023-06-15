package com.example.figma.controller.mypage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.controller.MainHome;
import com.example.figma.controller.myinformation.MyInfDetails;
import com.google.firebase.auth.FirebaseAuth;

import com.example.figma.databinding.MypageBinding;

public class Mypage extends AppCompatActivity {
    private MypageBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        mBinding = MypageBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // 종합정보시스템 버튼 하이퍼 링크
        mBinding.schoolPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://student.induk.ac.kr/KR/login.do"));
                startActivity(urlIntent);
            }
        });

        // 스마트클래스 버튼 하이퍼링크
        mBinding.smartClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lms.induk.ac.kr/login.php"));
                startActivity(urlIntent);
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

        // 정보수정 버튼
        mBinding.myInfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfDetails.class);
                startActivity(intent);
            }
        });

        // 로그아웃
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그아웃버튼",": 클릭");
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });
    }
}