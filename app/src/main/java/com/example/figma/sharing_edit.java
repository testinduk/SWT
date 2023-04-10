package com.example.figma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sharing_edit extends Activity {
    private EditText edit_title;
    private EditText edit_content;
    private Button edit_button;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_edit);

        edit_title = findViewById(R.id.edit_title);
        edit_content = findViewById(R.id.edit_content);
        edit_button = findViewById(R.id.edit_button);

        Intent third_intent = getIntent(); //sharing_detail intent.putExtra 정보 받아오기

        String shar_edit_title = third_intent.getStringExtra("title");
        String shar_edit_content = third_intent.getStringExtra("content");
        String shar_key = third_intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid();//uid 가져오기
        Log.d("Uid", uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        edit_title.setText(shar_edit_title);
        edit_content.setText(shar_edit_content);

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();
                //수정하기 위해 sharing Board밑에 현재 선택된 shar_key와 같은 것을 찾아서 title과 content에 수정된 글 저장하기.
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("sharing Board").child(shar_key);
                ref.child("title").setValue(edit_title.getText().toString());
                ref.child("content").setValue(edit_content.getText().toString());

                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);

            }
        });
    }
}
