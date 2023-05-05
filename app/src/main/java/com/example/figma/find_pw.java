package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class find_pw extends Activity {
    private EditText userName, studentNumber, studentId;
    private Button finishBT;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_pw);

        userName = findViewById(R.id.userName);// 이름 입력
        studentNumber = findViewById(R.id.studentNumber); // 학번 입력
        studentId = findViewById(R.id.studentId); // 아이디 입력
        finishBT = findViewById(R.id.finishBT); // 재설정 메일 보내기 버튼
        backButton = findViewById(R.id.backButton);


        finishBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sign_up");
                String name = userName.getText().toString().trim();
                String number = studentNumber.getText().toString().trim();
                String id = studentId.getText().toString().trim();

                Query query = databaseReference.orderByChild("studentNumber").equalTo(number); // studentNumber와 number가 일치할 때

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String userName = dataSnapshot.child("userName").getValue(String.class);
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);

                            if(name.equals(userName) && number.equals(studentNumber) && id.equals(emailId)){
                                FirebaseAuth.getInstance().sendPasswordResetEmail(emailId)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"비밀번호 재설정을 위해 이메일을 발송하였습니다.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), set_pw.class);
                                                    intent.putExtra("id", id);
                                                    intent.putExtra("name",name);
                                                    intent.putExtra("number",number);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }else if(name.equals(userName) && number.equals(studentNumber) && !id.equals(emailId)){
                                Toast.makeText(getApplicationContext(), "아이디가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }else if(name.equals(userName) && !number.equals(studentNumber) && id.equals(emailId)){
                                Toast.makeText(getApplicationContext(), "학번이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }else if(!name.equals(userName) && number.equals(studentNumber) && id.equals(emailId)){
                                Toast.makeText(getApplicationContext(), "이름이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });





    }
}
