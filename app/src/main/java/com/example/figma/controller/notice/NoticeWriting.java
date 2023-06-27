package com.example.figma.controller.notice;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.figma.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.example.figma.databinding.NoticeBoardWritingBinding;

public class NoticeWriting extends Activity {
    private NoticeBoardWritingBinding mBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef;
    FirebaseStorage storage;
    String notice_image_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = NoticeBoardWritingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mBinding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });

        mBinding.noticeBoardContentComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);

                //
                String current_time = getCurrentTime();

                Query query = databaseReference.child("signUp").orderByChild("idToken").equalTo(uid);

                query.addListenerForSingleValueEvent(new ValueEventListener() { //SignUp 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);
                            String idToken = dataSnapshot.child("idToken").getValue(String.class);
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String username = dataSnapshot.child("userName").getValue(String.class);


                            String title = mBinding.noticeBoardContentNameWrite.getText().toString();
                            String content = mBinding.noticeBoardContentWrite.getText().toString();


                            DatabaseReference boardRef = databaseReference.child("notice Board").push();
                            String boardKey = boardRef.getKey(); //키 값 가져오기.
                            boardRef.child("emailId").setValue(emailId);
                            boardRef.child("idToken").setValue(idToken);
                            boardRef.child("studentNumber").setValue(studentNumber);
                            boardRef.child("userName").setValue(username);
                            boardRef.child("title").setValue(title);
                            boardRef.child("content").setValue(content);
                            boardRef.child("key").setValue(boardKey);
                            boardRef.child("notice_time").setValue(current_time);
                            boardRef.child("image_UUID").setValue(notice_image_UUID);

                            storageRef.child("notice/" + notice_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (uri != null) {
                                        databaseReference.child("notice Board").child(boardKey).child("notice_image").setValue(uri.toString());
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mBinding.photoImage.setImageURI(uri);

                    StorageReference imageRef = storageRef.child("notice/" + notice_image_UUID);
                    Log.i("uuid", notice_image_UUID);
                    imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // 이미지 업로드 성공
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // 이미지 업로드 실패
                        }
                    });
                }
                break;
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
