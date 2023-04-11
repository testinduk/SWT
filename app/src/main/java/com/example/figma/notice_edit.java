package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class notice_edit extends Activity {
    private EditText editTextTextPersonName2, editTextTextPersonName3;
    private Button button17;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_edit);

        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);// 제목
        editTextTextPersonName3 = findViewById(R.id.editTextTextPersonName3);// 내용
        button17 = findViewById(R.id.button17); //수정 완료 버튼

        Intent third_intent = getIntent(); // notice_detail intent.putExtra 정보 받아오기

        String notice_edit_title = third_intent.getStringExtra("title");
        String notice_edit_content = third_intent.getStringExtra("content");
        String notice_key = third_intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 가져오기
        String uid = mAuth.getCurrentUser().getUid(); //uid 가져오기
        Log.d("uid", uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        editTextTextPersonName2.setText(notice_edit_title);
        editTextTextPersonName3.setText(notice_edit_content);

        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notice Board").child(notice_key);
                ref.child("title").setValue(editTextTextPersonName2.getText().toString());
                ref.child("content").setValue(editTextTextPersonName3.getText().toString());

                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                startActivity(intent);
            }
        });
    }
}
