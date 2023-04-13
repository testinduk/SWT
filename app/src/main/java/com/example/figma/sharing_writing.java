package com.example.figma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class sharing_writing extends Activity {

    Button btn;
    ImageButton imageButton,backButton;
    EditText edit1, edit2;
    String title, content;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef;
    FirebaseStorage storage;
    String sharing_image_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정
    ImageView photo_image;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_writing);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        btn = findViewById(R.id.button); //버튼 아이디 연결
        edit1 = findViewById(R.id.editTextTextPersonName); // 제목 적는 곳
        edit2 = findViewById(R.id.editTextTextPersonName1); // 내용 적는 곳
        imageButton = findViewById(R.id.imageButton); //이미지 사진 넣기
        backButton = findViewById(R.id.backButton);
        photo_image = findViewById(R.id.photo_image);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
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

                    StorageReference imageRef = storageRef.child("sharing/" + sharing_image_UUID);
                    Log.i("uuid",sharing_image_UUID);
                    imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                break;
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);

                Query query = databaseReference.child("sign_up").orderByChild("idToken").equalTo(uid);

                query.addListenerForSingleValueEvent(new ValueEventListener() { //sign_up 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);
                            String idToken = dataSnapshot.child("idToken").getValue(String.class);
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String username = dataSnapshot.child("userName").getValue(String.class);

                            String title = edit1.getText().toString();
                            String content = edit2.getText().toString();

                            DatabaseReference boardRef = databaseReference.child("sharing Board").push();
                            String boardKey = boardRef.getKey(); //새로운 키 값 가져오기
                            boardRef.child("emailId").setValue(emailId);
                            boardRef.child("idToken").setValue(idToken);
                            boardRef.child("studentNumber").setValue(studentNumber);
                            boardRef.child("userName").setValue(username);
                            boardRef.child("title").setValue(title);
                            boardRef.child("content").setValue(content);
                            boardRef.child("key").setValue(boardKey);


                            storageRef.child("sharing/" + sharing_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (uri != null) {
                                        databaseReference.child("sharing Board").child(boardKey).child("sharing_image").setValue(uri.toString());
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });
    }
}