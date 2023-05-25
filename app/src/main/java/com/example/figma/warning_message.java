package com.example.figma;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class warning_message extends Activity {

    private DatabaseReference mDatabase;
    private StorageReference mStroage;
    private FirebaseAuth mAuth = null;
    Button yesButton; // 회원 탈퇴 버튼 선언
    String TAG = "warning_message";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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
        yesButton = findViewById(R.id.yesButton);
                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mStroage = FirebaseStorage.getInstance().getReference();
                        String uid = mAuth.getCurrentUser().getUid();

                        // realtime 데이터베이스에 있는 sign_up 삭제(완료)
                        mDatabase.child("sign_up").child(user.getUid()).removeValue();

                        // realtime 데이터베이스에서 sharing board에 있는 사용자가 쓴 글 삭제
                        Query qsharing = mDatabase.child("sharing Board").orderByChild("idToken").equalTo(uid);

                        qsharing.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    String sKey = dataSnapshot.child("key").getValue(String.class);
                                    mDatabase.child("sharing Board").child(sKey).removeValue();

                                    String simage = dataSnapshot.child("image_UUID").getValue(String.class);
                                    mStroage.child("sharing/").child(simage).delete();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Query qnotice = mDatabase.child("notice Board").orderByChild("idToken").equalTo(uid);

                        qnotice.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    String nKey = dataSnapshot.child("key").getValue(String.class);
                                    mDatabase.child("notice Board").child(nKey).removeValue();

                                    String nimage = dataSnapshot.child("image_UUID").getValue(String.class);
                                    mStroage.child("notice/").child(nimage).delete();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Query qbulletin = mDatabase.child("bulletin Board").orderByChild("idToken").equalTo(uid);


                        qbulletin.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    String bKey = dataSnapshot.child("key").getValue(String.class);
                                    mDatabase.child("bulletin Board").child(bKey).removeValue();

                                    String bimage = dataSnapshot.child("image_UUID").getValue(String.class);
                                    mStroage.child("bulletin/").child(bimage).delete();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                // Firebase Auth(인증)에서 계정 삭제 -> 이건 되는 아이
                user.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // 회원탈퇴 성공
                                Log.d(TAG, "회원탈퇴 되었습니다.");
                                finish(); // 액티비티 종료
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 회원탈퇴 실패
                                Log.e(TAG, "회원탈퇴에 실패하였습니다.", e);
                            }
                        });
            }
        });
    }
}