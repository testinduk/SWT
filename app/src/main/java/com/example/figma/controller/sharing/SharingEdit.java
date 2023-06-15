package com.example.figma.controller.sharing;

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

import java.util.UUID;

import com.example.figma.databinding.SharingEditBinding;

public class SharingEdit extends Activity {
    private SharingEditBinding mBinding;

    StorageReference storageRef;
    FirebaseStorage storage;
    String sharing_image_UUID = UUID.randomUUID().toString();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mBinding.photoImage.setImageURI(uri);

                    Intent four_intent = getIntent();
                    String sharing_image1 = four_intent.getStringExtra("image");

                    StorageReference storageRef = storage.getReference();
                    StorageReference oldStorageRef = storage.getReferenceFromUrl(sharing_image1);

                    oldStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            StorageReference newImageRef = storageRef.child("sharing/" + sharing_image_UUID);
                            Log.i("uuid", sharing_image_UUID);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = SharingEditBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent third_intent = getIntent(); //sharing_detail intent.putExtra 정보 받아오기

        String shar_edit_title = third_intent.getStringExtra("title");
        String shar_edit_content = third_intent.getStringExtra("content");
        String shar_key = third_intent.getStringExtra("key");
        String sharing_image = third_intent.getStringExtra("image");
        String shar_id = third_intent.getStringExtra("id");
        String shar_name = third_intent.getStringExtra("username");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid();//uid 가져오기
        Log.d("Uid", uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        mBinding.sharingBoardContentNameMod.setText(shar_edit_title);
        mBinding.sharingBoardContentMod.setText(shar_edit_content);
        mBinding.sharingBoardContent.setText(shar_name);
        Glide.with(this)
                .load(sharing_image)
                .into(mBinding.photoImage);

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
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });
        mBinding.sharingBoardModComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();
                //수정하기 위해 sharing Board 밑에 현재 선택된 shar_key 와 같은 것을 찾아서 title 과 content 에 수정된 글 저장하기.
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("sharing Board").child(shar_key);
                ref.child("title").setValue(mBinding.sharingBoardContentNameMod.getText().toString());
                ref.child("content").setValue(mBinding.sharingBoardContentMod.getText().toString());

                storageRef.child("sharing/" + sharing_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            ref.child("sharing_image").setValue(uri.toString());
                        }
                    }
                });
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });
    }
}
