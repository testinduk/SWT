package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;



import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sharing_details extends Activity {
    private TextView tv_username;
    private TextView tv_content;
    private TextView tv_title;

    private Button edit_button;
    private ImageButton delete_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);

       
        // 수정 버튼
        tv_username = findViewById(R.id.textView2);
        tv_title = findViewById(R.id.textView1);
        tv_content = findViewById(R.id.textView4);
        edit_button = findViewById(R.id.btn_sha_amend);
        delete_button = findViewById(R.id.Button3);

        Intent second_intent = getIntent();

        String shar_username = second_intent.getStringExtra("username");
        String shar_title = second_intent.getStringExtra("title");
        String shar_content = second_intent.getStringExtra("content");
        String shar_idToken = second_intent.getStringExtra("idToken");
        String shar_key = second_intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Log.e("uid", uid);
        Log.i("shar_idToken", shar_idToken);

        tv_username.setText(shar_username);
        tv_content.setText(shar_content);
        tv_title.setText(shar_title);

        if (uid.equals(shar_idToken)) {
            edit_button.setEnabled(true);
            delete_button.setEnabled(true);
            //수정 버튼
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shar_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), sharing_edit.class);
                        intent.putExtra("id", shar_idToken);
                        intent.putExtra("title",shar_title);
                        intent.putExtra("content",shar_content);
                        intent.putExtra("key",shar_key);
                        Log.i("id", shar_idToken);
                        Log.i("title",shar_title);
                        Log.i("content",shar_content);
                        Log.i("key",shar_key);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            //글 삭제하기.
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shar_idToken != null){
                        Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                        startActivity(intent);
                        ref.child("sharing Board").child(shar_key).removeValue();
                    }
                }
            });

        } else {
            edit_button.setEnabled(false);
            delete_button.setEnabled(false);
        }

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
