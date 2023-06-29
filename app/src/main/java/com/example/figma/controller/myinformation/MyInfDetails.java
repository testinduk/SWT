package com.example.figma.controller.myinformation;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.databinding.MyInfDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class MyInfDetails extends Activity {
    private MyInfDetailsBinding mBinding;
    static final int REQUEST_CODE = 0;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;
    private String uid;
    private ListenerRegistration profileListenerRegistration;
    private String strEmail;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = MyInfDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference();

        //현재 정보 받아오기

        DatabaseReference signUpRef = FirebaseDatabase.getInstance().getReference("signUp");
        Query query = signUpRef.orderByChild("idToken").equalTo(uid);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String idToken = dataSnapshot.child("idToken").getValue(String.class);
                        if (idToken.equals(uid)) {
                            String userName = dataSnapshot.child("userName").getValue(String.class);
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            strEmail = dataSnapshot.child("emailId").toString();

                            mBinding.chname.setText(userName);
                            mBinding.class2.setText(studentNumber);
                            mStorageRef.child("signUp/" + strEmail);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

        //프로필 이미지 업데이트
        profileListenerRegistration = mFirestore.collection("signUp").document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(MyInfDetails.this, "프로필 이미지 업데이트 중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (value != null && value.exists()) {
                            String profileUri = value.getString("profileUri");
                            if (profileUri != null) {
                                Glide.with(MyInfDetails.this)
                                        .load(profileUri)
                                        .into(mBinding.changeImage);
                            }
                        }
                    }
                });

        mBinding.SelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mBinding.CompleteChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUserName = mBinding.chname.getText().toString();
                String newStudentNumber = mBinding.class2.getText().toString();

                //리얼타임 정보 업데이트
                DatabaseReference signUpRef = FirebaseDatabase.getInstance().getReference("signUp");
                Query query = signUpRef.orderByChild("idToken").equalTo(uid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().child("userName").setValue(newUserName);
                            snapshot.getRef().child("studentNumber").setValue(newStudentNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                DatabaseReference sharingRef = FirebaseDatabase.getInstance().getReference("sharing Board");
                Query sharingQuery = sharingRef.orderByChild("idToken").equalTo(uid);
                sharingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            snapshot.getRef().child("userName").setValue(newUserName);
                            snapshot.getRef().child("studentNumber").setValue(newStudentNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference noticeRef = FirebaseDatabase.getInstance().getReference("notice Board");
                Query noticeQuery = noticeRef.orderByChild("idToken").equalTo(uid);
                noticeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            snapshot.getRef().child("userName").setValue(newUserName);
                            snapshot.getRef().child("studentNumber").setValue(newStudentNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                DatabaseReference bulletinRef = FirebaseDatabase.getInstance().getReference("bulletin Board");
                Query bulletinQuery = bulletinRef.orderByChild("idToken").equalTo(uid);
                bulletinQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            snapshot.getRef().child("userName").setValue(newUserName);
                            snapshot.getRef().child("studentNumber").setValue(newStudentNumber);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //파이어스토어 정보 업데이트
                if (selectedImageUri != null) {
                    StorageReference imageRef = mStorageRef.child("signUp/" + strEmail);

                    imageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                imageRef.putFile(selectedImageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri downloadUri) {
                                                        if (downloadUri != null) {
                                                            String imageUri = downloadUri.toString();
                                                            Map<String, Object> updateMap = new HashMap<>();
                                                            updateMap.put("userName", newUserName);
                                                            updateMap.put("studentNumber", newStudentNumber);
                                                            updateMap.put("profileUri", imageUri);

                                                            mFirestore.collection("signUp").document(uid)
                                                                    .set(updateMap, SetOptions.merge())
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(MyInfDetails.this, "정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                                                                                Intent intent = new Intent(MyInfDetails.this, Mypage.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            }
                                        });
                            }
                        }
                    });
                } else {
                    //프로필 이미지를 변경하지 않았을 때의 처리
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("userName", newUserName);
                    updateMap.put("studentNumber", newStudentNumber);

                    mFirestore.collection("signUp").document(uid)
                            .set(updateMap, SetOptions.merge())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MyInfDetails.this, "정보가 업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MyInfDetails.this, Mypage.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyInfDetails.this, Mypage.class);
                startActivity(intent);
            }
        });
        // warningButton
        mBinding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyInfDetails.this, WarningMessage.class);
                startActivity(intent);
            }
        });
        //비밀번호 변경
        mBinding.changPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyInfDetails.this, MyInfChPW.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mBinding.changeImage.setImageURI(uri);
                    selectedImageUri = uri;

                    StorageReference storageRef = mStorage.getReference();
                    StorageReference riversRef = storageRef.child("signUp/" + strEmail);
                    UploadTask uploadTask = riversRef.putFile(uri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
                }
                break;
        }
    }
}
