package com.example.figma;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up_email extends Activity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser; //안드로이드와 파이어베이스 사이의 인증을 확인하기 위한 인스턴스 선언
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText editTextTextPersonName4; //회원가입 입력필드
    private Button button; //회원가입 버튼
    private Button log_button; //로그인 화면 이동


        @SuppressLint("MissingInflatedId")
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_email);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        button = findViewById(R.id.button);
        log_button = findViewById(R.id.log_button);
        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);

        log_button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(editTextTextPersonName4 != null){
                    mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(sign_up_email.this, "이메일을 발송하였습니다.", Toast.LENGTH_SHORT).show();
                                log_button.setEnabled(true);

                            }else {
                                Toast.makeText(sign_up_email.this, "이메일을 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

        });

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed(){

    }
}
