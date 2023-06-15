package com.example.figma.controller.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.figma.databinding.SignUpSendEmailBinding;

public class SignUpEmail extends Activity {
    private SignUpSendEmailBinding mBinding;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser; //안드로이드와 파이어베이스 사이의 인증을 확인하기 위한 인스턴스 선언
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mBinding = SignUpSendEmailBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.btnMoveLogin.setEnabled(false);
        mBinding.btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpEmail.this, "이메일을 발송하였습니다.", Toast.LENGTH_SHORT).show();
                                mBinding.btnMoveLogin.setEnabled(true);

                            }else {
                                Toast.makeText(SignUpEmail.this, "이메일을 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

        });
        mBinding.btnMoveLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
    }
}
