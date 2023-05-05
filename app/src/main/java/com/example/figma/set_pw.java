package com.example.figma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;

public class set_pw extends AppCompatActivity {
    private EditText set_pw;
    private Button finishBT;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pw);

        set_pw = findViewById(R.id.set_pw);
        finishBT = findViewById(R.id.finishBT);



        finishBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent(); //find_pw 입력 정보 받아오기
                String number = intent.getStringExtra("number");
                String id = intent.getStringExtra("id");
                String name = intent.getStringExtra("name");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sign_up");

                String pw =set_pw.getText().toString().trim();

                Query query = databaseReference.orderByChild("studentNumber").equalTo(number);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String userName = dataSnapshot.child("userName").getValue(String.class);
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);

                            if (name.equals(userName) && number.equals(studentNumber) && id.equals(emailId)){
                                //"sign_up" 노드의 비밀번호를 업데이트한다.
                                DatabaseReference userRef = databaseReference.child(dataSnapshot.getKey());
                                userRef.child("password").setValue(pw);

                                Toast.makeText(getApplicationContext(),"재설정 비밀번호를 확인하였습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), login.class);
                                startActivity(intent);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}
