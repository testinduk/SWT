package com.example.figma.controller.find;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.databinding.BulletinBoardEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.figma.databinding.FindPwBinding;

public class FindPw extends Activity {
    private FindPwBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = FindPwBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SignUp");
                String name = mBinding.enterName.getText().toString().trim();
                String number = mBinding.enterStudentNumber.getText().toString().trim();
                String id = mBinding.enterId.getText().toString().trim();

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

                                                    Intent intent = new Intent(getApplicationContext(), Login.class);
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

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });





    }
}
