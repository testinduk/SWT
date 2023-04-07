package com.example.figma;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class bullentin_board_writing extends Activity {
    Button button;
    EditText editTextTextPersonName5, editTextTextPersonName6;
    String title, content;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullentin_board_writing);


        button = findViewById(R.id.button); //버튼 아이디 연결
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5); // 제목 적는 곳
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6); // 내용 적는 곳

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String uid = mAuth.getCurrentUser().getUid();

                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
                startActivity(intent);

//                add_sharing(edit1.getText().toString(), edit2.getText().toString());

                Query query = databaseReference.child("sign_up").orderByChild("idToken").equalTo(uid);

                query.addListenerForSingleValueEvent(new ValueEventListener() { //sign_up 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);
                            String idToken = dataSnapshot.child("idToken").getValue(String.class);
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String userName = dataSnapshot.child("userName").getValue(String.class);

                            String title = editTextTextPersonName5.getText().toString();
                            String content = editTextTextPersonName6.getText().toString();


                            DatabaseReference boardRef = databaseReference.child("bullentin Board").push();
                            boardRef.child("emailId").setValue(emailId);
                            boardRef.child("idToken").setValue(idToken);
                            boardRef.child("studentNumber").setValue(studentNumber);
                            boardRef.child("userName").setValue(userName);
                            boardRef.child("title").setValue(title);
                            boardRef.child("content").setValue(content);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // 뒤로가기 버튼
                ImageButton backButton = findViewById(R.id.backButton);
                backButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), main_home.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
