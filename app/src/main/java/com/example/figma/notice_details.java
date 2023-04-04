package com.example.figma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class notice_details extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_details);



        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });
        // 연필 버튼
        ImageButton penButton = findViewById(R.id.pencilButton);
        penButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_writing.class);
                startActivity(intent);
            }
        });
        // trash button
        ImageButton trashButton = findViewById(R.id.trashButton);
        trashButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(notice_details.this);
                builder.setTitle("삭제");
                builder.setMessage("삭제하실겁니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // delete item from data source and update UI
                    }
                });
                builder.setNegativeButton("아니요", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}