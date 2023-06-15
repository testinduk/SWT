package com.example.figma.controller.bulletin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.figma.databinding.BulletinBoardDetailsBinding;

public class BulletinBoardDetails extends Activity {
    private BulletinBoardDetailsBinding mBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = BulletinBoardDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        Intent second_intent = getIntent();

        String bulletin_username = second_intent.getStringExtra("username");
        String bulletin_title = second_intent.getStringExtra("title");
        String bulletin_content = second_intent.getStringExtra("content");
        String bulletin_idToken = second_intent.getStringExtra("idToken");
        String bulletin_key = second_intent.getStringExtra("key");
        String bulletin_image = second_intent.getStringExtra("image");
        String bulletin_time = second_intent.getStringExtra("time");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        mBinding.writer.setText(bulletin_username);
        mBinding.content.setText(bulletin_content);
        mBinding.BulletinBoardDetailsTitle.setText(bulletin_title);
        Glide.with(this)
                .load(bulletin_image)
                .into(mBinding.imageView);
        mBinding.time.setText(bulletin_time);

        if (uid.equals(bulletin_idToken)) {
            mBinding.writingButton.setEnabled(true);
            mBinding.deleteButton.setEnabled(true);
            //수정 버튼
            mBinding.writingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bulletin_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), BulletinBoardEdit.class);
                        intent.putExtra("idToken", bulletin_idToken);
                        intent.putExtra("title",bulletin_title);
                        intent.putExtra("content",bulletin_content);
                        intent.putExtra("key",bulletin_key);
                        intent.putExtra("image",bulletin_image);
                        intent.putExtra("time",bulletin_time);
                        intent.putExtra("username", bulletin_username);
                        startActivity(intent);
                    } else {
                    }
                }
            });
            //글 삭제하기.
            mBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bulletin_idToken != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(BulletinBoardDetails.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                                startActivity(intent);
                                ref.child("bulletin Board").child(bulletin_key).removeValue();
                                Toast.makeText(BulletinBoardDetails.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("취소",null);
                        builder.create().show();
                    }
                }
            });
        } else {
            mBinding.writingButton.setEnabled(false);
            mBinding.deleteButton.setEnabled(false);
        }
        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });
        //채팅창 이동버튼 추가하기(김한용)
        // 댓글창 이동 버튼
        mBinding.commentMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoardComment.class);
                intent.putExtra("key", bulletin_key);
                startActivity(intent);
            }
        });
        //댓글 추가하기
    }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}