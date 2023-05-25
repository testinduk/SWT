package com.example.figma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class bulletin_board_writing extends Activity {
    Button button;
    EditText editTextTextPersonName5, editTextTextPersonName6;
    String title, content;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef;
    FirebaseStorage storage;
    String bulletin_board_image_UUID = UUID.randomUUID().toString(); //랜덤함수로 이미지 이름 지정
    ImageView photo_image;
    ImageButton imageButton, imageButton7, backButton;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board_writing);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        button = findViewById(R.id.button); //완료 버튼
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName); // 제목 적는 곳
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName1); // 내용 적는 곳
        photo_image = findViewById(R.id.photo_image); //사진 띄우기
        imageButton = findViewById(R.id.imageButton); //앨범 버튼
        imageButton7 = findViewById(R.id.imageButton7); //파일 버튼
        backButton = findViewById(R.id.backButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
            }
        });

        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);

                String current_time = getCurrentTime();

                Query query = databaseReference.child("sign_up").orderByChild("idToken").equalTo(uid);

                query.addListenerForSingleValueEvent(new ValueEventListener() { //sign_up 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);
                            String idToken = dataSnapshot.child("idToken").getValue(String.class);
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String userName = dataSnapshot.child("userName").getValue(String.class);

                            String title = editTextTextPersonName5.getText().toString();
                            String content = editTextTextPersonName6.getText().toString();

                            DatabaseReference boardRef = databaseReference.child("bulletin Board").push();
                            String boardKey = boardRef.getKey();
                            boardRef.child("emailId").setValue(emailId);
                            boardRef.child("idToken").setValue(idToken);
                            boardRef.child("studentNumber").setValue(studentNumber);
                            boardRef.child("userName").setValue(userName);
                            boardRef.child("title").setValue(title);
                            boardRef.child("content").setValue(content);
                            boardRef.child("key").setValue(boardKey);
                            boardRef.child("bulletin_time").setValue(current_time);
                            boardRef.child("image_UUID").setValue(bulletin_board_image_UUID);

                            storageRef.child("bulletin/"+bulletin_board_image_UUID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if(uri != null){
                                        databaseReference.child("bulletin Board").child(boardKey).child("bulletin_image").setValue(uri.toString());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    photo_image.setImageURI(uri);

                    StorageReference imageRef = storageRef.child("bulletin/" + bulletin_board_image_UUID);
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



    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
