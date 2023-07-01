package com.example.figma.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.figma.R;
import com.example.figma.controller.find.FindId;
import com.example.figma.controller.find.FindPw;
import com.example.figma.controller.signup.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.figma.databinding.LoginBinding;

public class Login extends Activity {
    private LoginBinding mBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = LoginBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();

        mBinding.loginFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPw.class);
                startActivity(intent);
            }
        });

        mBinding.loginFindID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindId.class);
                startActivity(intent);
            }
        });
        mBinding.loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        mBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.loginID.getText().toString().equals("") && !mBinding.loginPassword.getText().toString().equals("")) {
                    loginUser(mBinding.loginID.getText().toString(), mBinding.loginPassword.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainHome.class);
                    startActivityForResult(intent, 1); //메인 홈으로 이동.

                } else {
                    Toast.makeText(Login.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), MainHome.class);
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };
    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(Login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                            Intent intent = new Intent(getApplicationContext(), MainHome.class);
                            startActivityForResult(intent, 1);

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            Query query = databaseReference.child("SignUp").orderByChild("emailId").equalTo(email);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String id = dataSnapshot.child("emailId").getValue(String.class);

                                        if (id.equals(email)) {
                                            DatabaseReference userRef = databaseReference.child("SignUp").child(dataSnapshot.getKey());
                                            userRef.child("password").setValue(password);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // 에러 처리
                                }
                            });
                        } else {
                            // 로그인 실패
                            Toast.makeText(Login.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivityForResult(intent, 1);
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    public void onBackPressed(){
    }
}