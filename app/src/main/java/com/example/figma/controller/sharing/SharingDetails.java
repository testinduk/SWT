package com.example.figma.controller.sharing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SharingDetails extends Activity {
    private TextView sharingBoardContentWriter;
    private TextView sharingBoardContent;
    private TextView sharingBoardContentName;
    private TextView sharingBoardContentDay;
    private ImageView photo_image, sharingBoardContentMod;
    private ImageButton sharingBoardContentDelete;
    private Button sharingBoardContentComment;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);


        // 수정 버튼
        sharingBoardContentWriter = findViewById(R.id.sharingBoardContentWriter);
        sharingBoardContentName = findViewById(R.id.sharingBoardContentName);
        sharingBoardContent = findViewById(R.id.sharingBoardContent);
        sharingBoardContentMod = findViewById(R.id.sharingBoardContentMod);
        sharingBoardContentDelete = findViewById(R.id.sharingBoardContentDelete);
        photo_image = findViewById(R.id.photo_image);
        sharingBoardContentDay = findViewById(R.id.sharingBoardContentDay);
        sharingBoardContentComment = findViewById(R.id.sharingBoardContentComment); // 댓글창

        Intent second_intent = getIntent();

        String shar_username = second_intent.getStringExtra("username");
        String shar_title = second_intent.getStringExtra("title");
        String shar_content = second_intent.getStringExtra("content");
        String shar_idToken = second_intent.getStringExtra("idToken");
        String shar_key = second_intent.getStringExtra("key");
        String sharing_image = second_intent.getStringExtra("image");
        String sharing_time = second_intent.getStringExtra("time");


        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseFirestore fs_db = FirebaseFirestore.getInstance();
        String sharing_comment_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정


        sharingBoardContentWriter.setText(shar_username);
        sharingBoardContent.setText(shar_content);
        sharingBoardContentName.setText(shar_title);
        Glide.with(this)
                .load(sharing_image)
                .into(photo_image);
        sharingBoardContentDay.setText(sharing_time);

        if (uid.equals(shar_idToken)) {
            sharingBoardContentMod.setEnabled(true);
            sharingBoardContentDelete.setEnabled(true);
            //수정 버튼
            sharingBoardContentMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shar_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), SharingEdit.class);
                        intent.putExtra("id", shar_idToken);
                        intent.putExtra("title",shar_title);
                        intent.putExtra("content",shar_content);
                        intent.putExtra("key",shar_key);
                        intent.putExtra("image",sharing_image);
                        intent.putExtra("username", shar_username);
                        Log.i("id", shar_idToken);
                        Log.i("title",shar_title);
                        Log.i("content",shar_content);
                        Log.i("key",shar_key);
                        Log.i("image",sharing_image);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            //글 삭제하기.
            sharingBoardContentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shar_idToken != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SharingDetails.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                                startActivity(intent);
                                ref.child("sharing Board").child(shar_key).removeValue();
                                Toast.makeText(SharingDetails.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();
                    }
                }
            });

        } else {
            sharingBoardContentMod.setEnabled(false);
            sharingBoardContentDelete.setEnabled(false);
        }

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });


        //채팅창 이동버튼 추가하기(김한용)


        // 댓글창 이동 버튼
        sharingBoardContentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoardComment.class);
                intent.putExtra("key", shar_key);
                startActivity(intent);
            }
        });
    }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
