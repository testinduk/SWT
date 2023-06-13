package com.example.figma.controller.notice;

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

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class NoticeDetails extends Activity {
    private ImageButton noticeBoardContentDelete, noticeBoardContentMod, backButton, imageButton2;
    private TextView noticeBoardContent, noticeBoardContentName, noticeBoardUserName, noticeBoardDay;
    private ImageView photo_image;
    private Button noticeBoardContentComment;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_details);


        photo_image = findViewById(R.id.photo_image);    // 이미지뷰
        noticeBoardContentMod = findViewById(R.id.noticeBoardContentMod); //수정 버튼
        noticeBoardContent = findViewById(R.id.noticeBoardContent); //내용
        noticeBoardContentName = findViewById(R.id.noticeBoardContentName); //제목
        noticeBoardContentDelete = findViewById(R.id.noticeBoardContentDelete);   // 삭제 버튼
        backButton = findViewById(R.id.backButton); // back 버튼
        noticeBoardUserName =findViewById(R.id.noticeBoardUserName);   // 글쓴이
        noticeBoardDay = findViewById(R.id.noticeBoardDay); // 날짜
        noticeBoardContentComment = findViewById(R.id.noticeBoardContentComment);


        Intent second_intent = getIntent();

        String notice_username = second_intent.getStringExtra("username");
        String notice_title = second_intent.getStringExtra("title");
        String notice_content = second_intent.getStringExtra("content");
        String notice_idToken = second_intent.getStringExtra("idToken");
        String notice_key = second_intent.getStringExtra("key");
        String notice_time = second_intent.getStringExtra("time");
        String notice_image = second_intent.getStringExtra("image");

        //db레퍼런스
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseFirestore fs_db = FirebaseFirestore.getInstance();
        String notice_comment_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정


        noticeBoardContent.setText(notice_content);
        noticeBoardContentName.setText(notice_title);
        noticeBoardUserName.setText(notice_username);
        Glide.with(this)
                .load(notice_image)
                .into(photo_image);
        noticeBoardDay.setText(notice_time);


        if(uid.equals(notice_idToken)) {
            noticeBoardContentDelete.setEnabled(true);
            noticeBoardContentMod.setEnabled(true);

            noticeBoardContentMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), NoticeEdit.class);
                        intent.putExtra("id", notice_idToken);
                        intent.putExtra("title", notice_title);
                        intent.putExtra("content", notice_content);
                        intent.putExtra("key", notice_key);
                        intent.putExtra("image", notice_image);
                        Log.i("id", notice_idToken);
                        Log.i("title", notice_title);
                        Log.i("content", notice_content);
                        Log.i("key", notice_key);
                        Log.i("image", notice_image);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            // trash button
            noticeBoardContentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(NoticeDetails.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                                startActivity(intent);
                                ref.child("notice Board").child(notice_key).removeValue();
                                Toast.makeText(NoticeDetails.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();

                    }
                }
            });
        }else{
            noticeBoardContentMod.setEnabled(false);
            noticeBoardContentDelete.setEnabled(false);
        }
                // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });

        //채팅창 이동버튼 추가하기(김한용)


        // 댓글창 이동 버튼
        noticeBoardContentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeBoardComment.class);
                intent.putExtra("key", notice_key);
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

