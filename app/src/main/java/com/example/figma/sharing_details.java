package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;



import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class sharing_details extends Activity {
    private TextView tv_username;
    private TextView tv_content;
    private TextView tv_title;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);


        tv_username = findViewById(R.id.textView2);
        tv_title = findViewById(R.id.textView1);
        tv_content = findViewById(R.id.textView4);


        Intent seconed_intent = getIntent();

        String shar_username = seconed_intent.getStringExtra("username");
        String shar_title = seconed_intent.getStringExtra("title");
        String shar_content = seconed_intent.getStringExtra("content");



        tv_username.setText(shar_username);
        tv_content.setText(shar_content);
        tv_title.setText(shar_title);






        // 수정 버튼
        ImageButton btn_sha_amend = findViewById(R.id.btn_sha_amend);

        btn_sha_amend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_writing.class);
                startActivity(intent);
            }
        });


        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });

    }
}
