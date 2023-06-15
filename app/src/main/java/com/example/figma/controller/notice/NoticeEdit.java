package com.example.figma.controller.notice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.google.android.gms.tasks.OnFailureListener;
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

import com.example.figma.databinding.NoticeBoardEditBinding;


public class NoticeEdit extends Activity {
    private NoticeBoardEditBinding mBinding;
    private ImageView photo_image;


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


        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid(); //uid 가져오기

        mBinding.noticeBoardContentNameMod.setText(notice_edit_title);
        mBinding.noticeBoardContentMod.setText(notice_edit_content);
        Glide.with(this)
                .load(notice_image)
                .into(photo_image);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    photo_image.setImageURI(uri);

                    Intent third_intent = getIntent();
                    String notice_image1 = third_intent.getStringExtra("image");

                    StorageReference storageRef = storage.getReference();
                    StorageReference oldStorageRef = storage.getReferenceFromUrl(notice_image1);
                    Log.i("edit_image", String.valueOf(oldStorageRef));


                    oldStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            StorageReference newImageRef = storageRef.child("notice/" + notice_image_UUID);
                            Log.i("uuid", notice_image_UUID);
                            newImageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //이미지 업로드 성공
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //이미지 업로드 실패
                                }
                            });
                        }
                    });
                    break;
                }
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
