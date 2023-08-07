package com.swt.figma.controller.notice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.swt.figma.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.swt.figma.databinding.NoticeBoardDetailsBinding;

public class NoticeDetails extends Activity {
    private NoticeBoardDetailsBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = NoticeBoardDetailsBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

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

        mBinding.noticeBoardContent.setText(notice_content);
        mBinding.noticeBoardContentName.setText(notice_title);
        mBinding.noticeBoardUserName.setText(notice_username);
        Glide.with(this)
                .load(notice_image)
                .into(mBinding.photoImage);
        mBinding.noticeBoardDay.setText(notice_time);

        if(uid.equals(notice_idToken)) {
            mBinding.noticeBoardContentDelete.setEnabled(true);
            mBinding.noticeBoardContentMod.setEnabled(true);
            mBinding.noticeBoardContentMod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), NoticeEdit.class);
                        intent.putExtra("idToken", notice_idToken);
                        intent.putExtra("title", notice_title);
                        intent.putExtra("content", notice_content);
                        intent.putExtra("key", notice_key);
                        intent.putExtra("image", notice_image);
                        intent.putExtra("time", notice_time);
                        intent.putExtra("username", notice_username);
                        startActivity(intent);
                    } else {
                    }
                }
            });
            // 삭제하기
            mBinding.noticeBoardContentDelete.setOnClickListener(new View.OnClickListener() {
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
            mBinding.noticeBoardContentMod.setEnabled(false);
            mBinding.noticeBoardContentDelete.setEnabled(false);
        }
        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoticeList.class);
                startActivity(intent);
            }
        });
        // 댓글창 이동 버튼
        mBinding.noticeBoardContentComment.setOnClickListener(new View.OnClickListener() {
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

