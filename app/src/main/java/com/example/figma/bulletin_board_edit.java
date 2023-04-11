package com.example.figma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bulletin_board_edit extends Activity {
    private EditText textView1; //제목
    private TextView textView2; //글쓴이
    private EditText textView4; //내용
    private TextView textView3; //날짜
    private Button edit_button; //수정버튼


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board_edit);

        textView1 = findViewById(R.id.textView1);
        textView4 = findViewById(R.id.textView4);
        edit_button = findViewById(R.id.edit_button);

        Intent third_intent = getIntent(); //bulletin_detail intent.putExtra 정보 받아오기

        String bulletin_edit_title = third_intent.getStringExtra("title");
        String bulletin_edit_content = third_intent.getStringExtra("content");
        String bulletin_key = third_intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid();//uid 가져오기
        Log.d("Uid", uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        textView1.setText(bulletin_edit_title);
        textView4.setText(bulletin_edit_content);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();
                //수정하기 위해 sharing Board밑에 현재 선택된 shar_key와 같은 것을 찾아서 title과 content에 수정된 글 저장하기.
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("bulletin Board").child(bulletin_key);
                ref.child("title").setValue(textView1.getText().toString());
                ref.child("content").setValue(textView4.getText().toString());

                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);

            }
        });
    }
}
