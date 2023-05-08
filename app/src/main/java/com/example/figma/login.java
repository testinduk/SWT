package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

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


public class login extends Activity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private Button buttonSignUp, findButton, findButton2;
    private CheckBox checkBox;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        findButton = findViewById(R.id.findButton);
        findButton2 = findViewById(R.id.findButton2);
        checkBox = findViewById(R.id.checkBox);

        //이전에 로그인 정보 저장되어 있는 경우 자동 로그인
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        saveLogin = sharedPreferences.getBoolean("saveLogin", false);
        if(saveLogin){
            editTextEmail.setText(sharedPreferences.getString("username",""));
            editTextPassword.setText(sharedPreferences.getString("password",""));
            checkBox.setChecked(true);
            loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }

        findButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), find_pw.class);
                startActivity(intent);
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), find_id.class);
                startActivity(intent);
            }
        });

        Button signup = findViewById(R.id.sign); //회원가입 버튼 클릭시 회원가입 페이지로 이동
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sign_up.class);
                startActivity(intent);
            }
        });

        buttonSignUp = (Button) findViewById(R.id.sign);// 회원가입 버튼 클릭시
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SignUpActivity 연결
                Intent intent = new Intent(getApplicationContext(), sign_up.class); //회원가입 화면으로 전환
                startActivity(intent);
            }
        });


        buttonLogIn = (Button) findViewById(R.id.btn_login); //로그인 버튼 클릭시
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), main_home.class);
                    startActivityForResult(intent, 1); //메인 홈으로 이동.

                } else {
                    Toast.makeText(login.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(), main_home.class);
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
                            Toast.makeText(login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                            Intent intent = new Intent(getApplicationContext(), main_home.class);
                            startActivityForResult(intent, 1);

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            Query query = databaseReference.child("sign_up").orderByChild("emailId").equalTo(email);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        String id = dataSnapshot.child("emailId").getValue(String.class);

                                        if(id.equals(email)){
                                            DatabaseReference userRef = databaseReference.child("sign_up").child(dataSnapshot.getKey());
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
                            Toast.makeText(login.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), login.class);
                            startActivityForResult(intent,1);
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

    private class override {
    }
}