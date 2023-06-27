package com.example.figma.controller.bulletin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import com.example.figma.databinding.BulletinBoardEditBinding;

public class BulletinBoardEdit extends Activity {

    private BulletinBoardEditBinding mBinding;

    StorageReference storageRef;
    FirebaseStorage storage;
    String bulletin_image_UUID = UUID.randomUUID().toString();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mBinding.imageView.setImageURI(uri);

                    Intent four_intent = getIntent();
                    String bulletin_image1 = four_intent.getStringExtra("image");

                    StorageReference storageRef = storage.getReference();
                    StorageReference oldStorageRef = storage.getReferenceFromUrl(bulletin_image1);

                    oldStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            StorageReference newImageRef = storageRef.child("bulletin/" + bulletin_image_UUID);
                            Log.i("uuid", bulletin_image_UUID);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = BulletinBoardEditBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent third_intent = getIntent(); //sharing_detail intent.putExtra 정보 받아오기

        String bulletin_title = third_intent.getStringExtra("title");
        String bulletin_content = third_intent.getStringExtra("content");
        String bulletin_key = third_intent.getStringExtra("key");
        String bulletin_image = third_intent.getStringExtra("image");
        String bulletin_idToken = third_intent.getStringExtra("idToken");
        String bulletin_time = third_intent.getStringExtra("time");
        String bulletin_username = third_intent.getStringExtra("username");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid();//uid 가져오기
        Log.d("Uid", uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        mBinding.titleEdit.setText(bulletin_title);
        mBinding.contentEdit.setText(bulletin_content);
        Glide.with(this)
                .load(bulletin_image)
                .into(mBinding.imageView);

        mBinding.attachImageFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        mBinding.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                String current_time = getCurrentTime();

                //수정하기 위해 bulletin Board 밑에 현재 선택된 bulletin_key 와 같은 것을 찾아서 title 과 content 에 수정된 글 저장하기.
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("bulletin Board").child(bulletin_key);
                ref.child("title").setValue(mBinding.titleEdit.getText().toString());
                ref.child("content").setValue(mBinding.contentEdit.getText().toString());
                ref.child("bulletin_time").setValue(current_time);

                storageRef.child("bulletin/" + bulletin_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            ref.child("bulletin_image").setValue(uri.toString());
                        }
                    }
                });
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bulletin_idToken != null) {
                    Intent intent = new Intent(getApplicationContext(), BulletinBoardDetails.class);
                    intent.putExtra("idToken", bulletin_idToken);
                    intent.putExtra("title", bulletin_title);
                    intent.putExtra("content", bulletin_content);
                    intent.putExtra("key", bulletin_key);
                    intent.putExtra("image", bulletin_image);
                    intent.putExtra("time", bulletin_time);
                    startActivity(intent);
                }
            }
        });
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
