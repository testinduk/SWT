package com.example.figma;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class notice_details extends Activity {
    private ImageButton delete_button, edit_button, backButton;
    private TextView tv_content, tv_title, tv_username, tv_time;
    private ImageView photo_image;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_details);

        photo_image = findViewById(R.id.photo_image);    // 이미지뷰
        edit_button = findViewById(R.id.btn_notice_amend); //수정 버튼
        tv_content = findViewById(R.id.textView4); //내용
        tv_title = findViewById(R.id.textView1); //제목
        delete_button = findViewById(R.id.Button3);   // 삭제 버튼
        backButton = findViewById(R.id.backButton); // back 버튼
        tv_username =findViewById(R.id.textView2);   // 글쓴이
        tv_time = findViewById(R.id.textView3); // 날짜


        Intent second_intent = getIntent();

        String notice_username = second_intent.getStringExtra("username");
        String notice_title = second_intent.getStringExtra("title");
        String notice_content = second_intent.getStringExtra("content");
        String notice_idToken = second_intent.getStringExtra("idToken");
        String notice_key = second_intent.getStringExtra("key");
        String notice_time = second_intent.getStringExtra("time");
        String notice_image = second_intent.getStringExtra("image");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        tv_content.setText(notice_content);
        tv_title.setText(notice_title);
        tv_username.setText(notice_username);
        Glide.with(this)
                .load(notice_image)
                .into(photo_image);
        tv_time.setText(notice_time);


        if(uid.equals(notice_idToken)) {
            delete_button.setEnabled(true);
            edit_button.setEnabled(true);

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), notice_edit.class);
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
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(notice_details.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                                startActivity(intent);
                                ref.child("notice Board").child(notice_key).removeValue();
                                Toast.makeText(notice_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();

                    }
                }
            });
        }else{
            edit_button.setEnabled(false);
            delete_button.setEnabled(false);
        }
                // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });
    }
}