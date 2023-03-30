package com.example.figma;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class warning_message extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth = null;
    Button yesButton; // 회원 탈퇴 버튼 선언

    // 다른 페이지에서 게시판 버튼 눌렀을 때
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning_message);

        // 취소버튼(뒤로가기 처럼 생성)
        Button noButton = findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
            }
        });

        // 회원 탈퇴
        yesButton = (Button)findViewById(R.id.yesButton);

        mAuth = FirebaseAuth.getInstance();

        yesButton.setOnClickListener(this);
    }
    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yesButton:
                revokeAccess();
                finishAffinity();
                break;
        }
    }
}