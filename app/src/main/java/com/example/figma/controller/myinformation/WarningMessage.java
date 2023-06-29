package com.example.figma.controller.myinformation;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.controller.mypage.Mypage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.example.figma.databinding.WarningMessageBinding;

public class WarningMessage extends Activity {
    private WarningMessageBinding mBinding;
    private DatabaseReference mDatabase;
    private StorageReference mStroage;
    private FirebaseAuth mAuth = null;
    private FirebaseFirestore mFirestore;

    String TAG = "WarningMessage";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    // 다른 페이지에서 게시판 버튼 눌렀을 때
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = WarningMessageBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // 취소버튼(뒤로가기 처럼 생성)
        mBinding.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        // 회원 탈퇴
        mBinding.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mStroage = FirebaseStorage.getInstance().getReference();
                String uid = mAuth.getCurrentUser().getUid();
                mFirestore = FirebaseFirestore.getInstance();

                // realtime 데이터베이스에 있는 SignUp 삭제(완료)
                mDatabase.child("SignUp").child(user.getUid()).removeValue();

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

                Query notice = mDatabase.child("notice Board").orderByChild("idToken").equalTo(uid);

                notice.addListenerForSingleValueEvent(new ValueEventListener() {
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

                Query bulletin = mDatabase.child("bulletin Board").orderByChild("idToken").equalTo(uid);

                bulletin.addListenerForSingleValueEvent(new ValueEventListener() {
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

                Query signUp = mDatabase.child("signUp").orderByChild("idToken").equalTo(uid);

                signUp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String signUpKey = dataSnapshot.child("idToken").getValue(String.class);
                            mDatabase.child("signUp").child(signUpKey).removeValue();

                            String signUpimage = dataSnapshot.child("emailId").getValue(String.class);
                            mStroage.child("signUp/").child(signUpimage).delete();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mFirestore.collection("signUp").whereEqualTo("uid",uid)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                    documentSnapshot.getReference().delete();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                mFirestore.collection("chat").whereEqualTo("chatRoomKey"+uid, true)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                    documentSnapshot.getReference().delete();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
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