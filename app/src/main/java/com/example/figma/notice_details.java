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
import android.widget.TextView;

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
    private ImageButton trashButton, pencilButton, backButton;
    private TextView editTextTextPersonName1, title;
    private Button textView6;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_details);


        pencilButton = findViewById(R.id.pencilButton); //수정 버튼
        editTextTextPersonName1 = findViewById(R.id.editTextTextPersonName1); //내용
        title = findViewById(R.id.title); //제목
        trashButton = findViewById(R.id.trashButton);
        backButton = findViewById(R.id.backButton);
        textView6 =findViewById(R.id.textView6);


        Intent second_intent = getIntent();

        String notice_username = second_intent.getStringExtra("username");
        String notice_title = second_intent.getStringExtra("title");
        String notice_content = second_intent.getStringExtra("content");
        String notice_idToken = second_intent.getStringExtra("idToken");
        String notice_key = second_intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Log.e("uid", uid);
        Log.i("notice_idToken",notice_idToken);

        editTextTextPersonName1.setText(notice_content);
        title.setText(notice_title);

        if(uid.equals(notice_idToken)) {
            pencilButton.setEnabled(true);
            trashButton.setEnabled(true);

            pencilButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), notice_edit.class);
                        intent.putExtra("id", notice_idToken);
                        intent.putExtra("title", notice_title);
                        intent.putExtra("content", notice_content);
                        intent.putExtra("key", notice_key);
                        Log.i("id", notice_idToken);
                        Log.i("title", notice_title);
                        Log.i("content", notice_content);
                        Log.i("key", notice_key);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            // trash button
            trashButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), notice_list.class);
                        startActivity(intent);
                        ref.child("notice Board").child(notice_key).removeValue();
                    }
                }
            });
        }else{
            pencilButton.setEnabled(false);
            trashButton.setEnabled(false);
        }
                // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });

        textView6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });
                 //   AlertDialog.Builder builder = new AlertDialog.Builder(notice_details.this);
                //    builder.setTitle("삭제");
                //    builder.setMessage("삭제하실겁니까?");
                //    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                //        @Override
                //        public void onClick(DialogInterface dialogInterface, int i) {
                //            // delete item from data source and update UI
                 //       }
                //});
               // builder.setNegativeButton("아니요", null);
              //  AlertDialog dialog = builder.create();
              //  dialog.show();
    }
}