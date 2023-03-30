package com.example.figma;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.android.auth.AuthResult;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser; //안드로이드와 파이어베이스 사이의 인증을 확인하기 위한 인스턴스 선언
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText editTextTextPersonName4, editTextTextPassword, editTextTextPersonName, editTextTextPersonName2, editTextNumberPassword, editTextTextPersonName3; //회원가입 입력필드
    private Button finishBT; //회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        mAuth = FirebaseAuth.getInstance(); //선언한 인스턴스를 초기화
        mUser = mAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("sign_up");

        editTextTextPersonName4 = findViewById(R.id.editTextTextPersonName4);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        finishBT = findViewById(R.id.finishBT);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2 = findViewById(R.id.editTextTextPersonName2);
        editTextNumberPassword = findViewById(R.id.editTextNumberPassword);
        ImageButton backButton = findViewById(R.id.backButton);// 뒤로가기 버튼


        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });


        finishBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String strEmail = editTextTextPersonName4.getText().toString();
                String strPwd = editTextTextPassword.getText().toString();
                String strUserName = editTextTextPersonName.getText().toString();
                String strStudentNumber = editTextTextPersonName2.getText().toString();
                String pwdCheck = editTextNumberPassword.getText().toString();

                if (strUserName.length() > 0 && strStudentNumber.length() > 0 && strEmail.length() > 0 && strPwd.length() > 0 && pwdCheck.length() > 0) {
                    if (strPwd.equals(pwdCheck)) {
                        //FirebaseAuth 진행
                        mAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(sign_up.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    sign_up_DB sign_up_db = new sign_up_DB();
                                    sign_up_db.setEmailId(firebaseUser.getEmail());
                                    sign_up_db.setPassword(strPwd);
                                    sign_up_db.setIdToken(firebaseUser.getUid());
                                    sign_up_db.setUserName(strUserName);
                                    sign_up_db.setStudentNumber(strStudentNumber);


                                    //setValue는 database에 insert 행휘
                                    mDatabaseRef.child(firebaseUser.getUid()).setValue(sign_up_db);

                                    Toast.makeText(sign_up.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), sign_up_email.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(sign_up.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(sign_up.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(sign_up.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(editTextTextPersonName4 != null){
//                    mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(sign_up.this, "메일 전송 완료",Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(sign_up.this,"메일 전송 실패",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }else{
//                    Toast.makeText(sign_up.this,"메일을 입력해주세요",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}


//                button.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder().setUrl("https://induk-project.firebaseapp.com/__/auth/action?mode=action&oobCode=code")
//                                .setHandleCodeInApp(true).setAndroidPackageName("com.example.figma", true, "12").build();
//                        FirebaseAuth auth = FirebaseAuth.getInstance();
//                        auth.sendSignInLinkToEmail(strEmail, actionCodeSettings).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Log.d(TAG, "Email sent");
//                                }
//                            }
//                        });
//
//                    }
//                });



//                @Override
//                public void onClick(View view) {
//                    mAuth.sendSignInLinkToEmail(email, actionCode {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "Email sent");
//                                Toast.makeText(sign_up.this, "메일 확인 요망" + mUser.getEmail(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Log.e(TAG, "메세지 보내기 실패", task.getException());
//                                Toast.makeText(sign_up.this, "메일 실패", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//            });
        
        
                                      


//                findViewById(R.id.finishBT).setOnClickListener(onClickListener);

//             뒤로가기 버튼


//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.finishBT:
//                    signUp();
//                    break;
//            }
//        }
//    };
//}
//    private void signUp() {
//        String id = ((EditText) findViewById(R.id.editTextTextPersonName2)).getText().toString();
//        String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();
//        String passwordCheck = ((EditText) findViewById(R.id.editTextNumberPassword)).getText().toString();
//
//        if (id.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
//            if (password.equals(passwordCheck)) {
//                mAuth.createUserWithEmailAndPassword(id, password)
//                        .addOnCompleteListener(this, new
//                                OnCompleteListener<AuthResult>() //사용자가 입력한 아이디와 비밀번호를 파이어베이스에 저장시켜주는 코드
//                                {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) //정상적으로 회원정보가 저장된 경우
//                                        {
//                                            Toast.makeText(com.example.figma.sign_up.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
//                                            ;
//                                        } else {
//                                            if (task.getException().toString() != null) //정상적으로 회원정보가 저장되지 않는 경우
//                                            {
//                                                Toast.makeText(com.example.figma.sign_up.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
//                                            } //Toast.makeText()토스트 알림을 띄워주는 함수
//                                        }
//                                    }
//                                });
//            } else {
//                Toast.makeText(com.example.figma.sign_up.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(com.example.figma.sign_up.this, "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
