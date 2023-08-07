package com.swt.figma.controller.notice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.swt.figma.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.swt.figma.databinding.NoticeBoardEditBinding;


public class NoticeEdit extends Activity {
    private NoticeBoardEditBinding mBinding;

    StorageReference storageRef;
    FirebaseStorage storage;
    String notice_image_UUID = UUID.randomUUID().toString();     // 랜덤 UUID 생성
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mBinding = NoticeBoardEditBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent third_intent = getIntent(); // notice_detail intent.putExtra 정보 받아오기

        String notice_edit_title = third_intent.getStringExtra("title");
        String notice_edit_content = third_intent.getStringExtra("content");
        String notice_key = third_intent.getStringExtra("key");
        String notice_image = third_intent.getStringExtra("image");
        String notice_time = third_intent.getStringExtra("time");
        String notice_username = third_intent.getStringExtra("username");
        String notice_idToken = third_intent.getStringExtra("idToken");


        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid(); //uid 가져오기

        mBinding.noticeBoardContentNameMod.setText(notice_edit_title);
        mBinding.noticeBoardContentMod.setText(notice_edit_content);
        Glide.with(this)
                .load(notice_image)
                .into(mBinding.photoImageView);

        mBinding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mBinding.noticeBoardModComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                String current_time = getCurrentTime();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notice Board").child(notice_key);
                ref.child("title").setValue(mBinding.noticeBoardContentNameMod.getText().toString());
                ref.child("content").setValue(mBinding.noticeBoardContentMod.getText().toString());
                ref.child("notice_time").setValue(current_time);

                storageRef.child("notice/" + notice_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            ref.child("notice_image").setValue(uri.toString());
                        }
                    }
                });

                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notice_intent = new Intent(getApplicationContext(), NoticeDetails.class);
                notice_intent.putExtra("username", notice_username);
                notice_intent.putExtra("title", notice_edit_title);
                notice_intent.putExtra("content", notice_edit_content);
                notice_intent.putExtra("time", notice_time);
                notice_intent.putExtra("idToken",notice_idToken);
                notice_intent.putExtra("key", notice_key);
                notice_intent.putExtra("image", notice_image);
                startActivity(notice_intent);
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
                    mBinding.photoImageView.setImageURI(uri);

                    Intent third_intent = getIntent();
                    String notice_image1 = third_intent.getStringExtra("image");

                    StorageReference storageRef = storage.getReference();
                    if (notice_image1 != null && !notice_image1.isEmpty()) {
                        StorageReference oldStorageRef = storage.getReferenceFromUrl(notice_image1);

                        oldStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i("log", "원래 있던 아이미지가 지워졌습니다");
                            }
                        });
                    }

                    StorageReference newImageRef = storageRef.child("notice/" + notice_image_UUID);
                    newImageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //이미지 업로드 성공
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
