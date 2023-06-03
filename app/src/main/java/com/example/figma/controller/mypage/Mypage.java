package com.example.figma.controller.mypage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.controller.MainHome;
import com.example.figma.controller.myinformation.MyInfDetails;
import com.google.firebase.auth.FirebaseAuth;

public class Mypage extends AppCompatActivity {
    // 다른 페이지에서 마이페이지 버튼 눌렀을 때
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        ImageButton mschoolpage; // 종합정보시스템 버튼 선언
        ImageButton msmartclass; // 스마트클래스 버튼 선언

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        // 종합정보시스템 버튼 하이퍼 링크
        mschoolpage = findViewById(R.id.schoolpage);

        mschoolpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://student.induk.ac.kr/KR/login.do"));
                startActivity(urlintent);
            }
        });

        // 스마트클래스 버튼 하이퍼링크
        msmartclass = findViewById(R.id.smartclass);

        msmartclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lms.induk.ac.kr/login.php"));
                startActivity(urlintent);
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

        // 정보수정 버튼
        Button show_notice_more = findViewById(R.id.my_inf);
        show_notice_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfDetails.class);
                startActivity(intent);
            }
        });

        // 로그아웃
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
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