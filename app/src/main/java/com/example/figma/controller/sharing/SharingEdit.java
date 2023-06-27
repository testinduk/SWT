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
    private StorageReference storageRef;
    FirebaseStorage storage;
    String sharing_image_UUID = UUID.randomUUID().toString();

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
                    if (sharing_image1 != null && !sharing_image1.isEmpty()) {
                        StorageReference oldStorageRef = storage.getReferenceFromUrl(sharing_image1);

                        oldStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i("log", "원래 있던 아이미지가 지워졌습니다");
                            }
                        });
                    }


                    StorageReference newImageRef = storageRef.child("sharing/" + sharing_image_UUID);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = SharingEditBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent third_intent = getIntent(); //sharing_detail intent.putExtra 정보 받아오기

        String shar_title = third_intent.getStringExtra("title");
        String shar_content = third_intent.getStringExtra("content");
        String shar_key = third_intent.getStringExtra("key");
        String shar_image = third_intent.getStringExtra("image");
        String shar_id = third_intent.getStringExtra("idToken");
        String shar_name = third_intent.getStringExtra("username");
        String shar_time = third_intent.getStringExtra("time");

        mBinding.sharingBoardContentNameMod.setText(shar_title);
        mBinding.sharingBoardContentMod.setText(shar_content);
        mBinding.sharingBoardContent.setText(shar_name);
        Glide.with(this)
                .load(shar_image)
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

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharingDetails.class);
                intent.putExtra("idToken", shar_id);
                intent.putExtra("title",shar_title);
                intent.putExtra("content",shar_content);
                intent.putExtra("key",shar_key);
                intent.putExtra("image",shar_image);
                intent.putExtra("username", shar_name);
                intent.putExtra("time", shar_time);
                startActivity(intent);
            }
        });
    }
}
