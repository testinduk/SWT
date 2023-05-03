package com.example.figma;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class warning_message extends Activity {

    private FirebaseAuth mAuth = null;
    Button yesButton; // 회원 탈퇴 버튼 선언
    String TAG = "warning_message";
    FirebaseStorage firebaseStorage;

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
//        yesButton = findViewById(R.id.yesButton);
//        yesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FirebaseAuth mAuth = FirebaseAuth.getInstance(); // Firebase 인증 객체 생성
//                FirebaseUser user = mAuth.getCurrentUser(); // 현재 사용자 정보 가져오기
//                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance(); // Realtime Database 객체 생성
//                FirebaseStorage mStorage = FirebaseStorage.getInstance(); // Storage 객체 생성
//
//                // 게시글, 댓글 등 모든 정보를 가져오기 위한 참조 생성
//                DatabaseReference userRef = mDatabase.getReference().child("users").child(user.getUid());
//
//                // 데이터 변경 감지
//                ValueEventListener postListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        // 현재 사용자가 작성한 게시글, 댓글 등 모든 정보 가져오기
//                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
//                            String postKey = postSnapshot.getKey();
//                            // 게시글, 댓글 등 모든 정보 삭제
//                            mDatabase.getReference().child("posts").child(postKey).removeValue();
//                        }
//                        // Realtime Database 에서 사용자 정보 삭제
//                        userRef.removeValue();
//                        mStorage.getReference().child("users").child(user.getUid()).delete(); // 스토리지에서 사용자 정보 삭제
//                        user.delete(); // 파이어베이스 Auth에서 사용자 계정 삭제
//                        // 회원탈퇴 완료
//                        Toast.makeText(getApplicationContext(), "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // 회원탈퇴 실패
//                        Toast.makeText(getApplicationContext(), "회원탈 dbr퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                // 모든 정보 변경 감지 리스너 추가
//                userRef.addListenerForSingleValueEvent(postListener);
//            }
//        });
   }
}

