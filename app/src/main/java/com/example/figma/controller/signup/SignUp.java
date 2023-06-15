package com.example.figma.controller.signup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.figma.databinding.SignUpBinding;

import java.util.HashMap;
import java.util.Map;
//import com.google.android.auth.AuthResult;

public class SignUp extends AppCompatActivity {
    private SignUpBinding mBinding;
    private static final int PICK_IMAGE_REQUEST = 200;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private FirebaseStorage storage;
    private FirebaseFirestore storageDB;
    private String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance(); //선언한 인스턴스를 초기화
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("SignUp");

        // Firestorage 초기화
        storageDB = FirebaseFirestore.getInstance();

        mBinding = SignUpBinding.inflate(getLayoutInflater());
        View view =mBinding.getRoot();
        setContentView(view);

        mBinding.spinnerQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mBinding.spinnerSelectQuestion.setText((CharSequence) adapterView.getItemAtPosition(position));
                question = (String) adapterView.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        mBinding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


        mBinding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작

                String strEmail = mBinding.editTextEmail.getText().toString();
                String strPwd = mBinding.editTextPassword.getText().toString();
                String strUserName = mBinding.editTextUserName.getText().toString();
                String strStudentNumber = mBinding.editTextStudentNumber.getText().toString();
                String pwdCheck = mBinding.editTextPasswordCheck.getText().toString();
                String answer = mBinding.spinnerAnswer.getText().toString();
                String question = mBinding.spinnerSelectQuestion.getText().toString();

                if (strUserName.length() > 0 && strStudentNumber.length() > 0 && strEmail.length() > 0 && strPwd.length() >= 6 && pwdCheck.length() >= 6) {
                    if (strPwd.equals(pwdCheck)) {
                        //가입 진행
                        mAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = mAuth.getUid();

                                    Map<String, Object> signUp = new HashMap<>();
                                    signUp.put("userName", strUserName);
                                    signUp.put("password", strPwd);
                                    signUp.put("stduentNumber", strStudentNumber);
                                    signUp.put("email", strEmail);
                                    signUp.put("question", question);
                                    signUp.put("answer", answer);

                                    // Firestor에 저장
                                    storageDB.collection("signUp").document(uid).set(signUp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SignUp.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), SignUpEmail.class);
                                            startActivity(intent);
                                        }
                                    });

                                } else {
                                    Toast.makeText(SignUp.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                                /*mAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                            }
                        });*/


                                //FirebaseAuth 진행

                                //---------FirebaseRealTime-Database 이용 방법--------
//                        mAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//
//                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
//                                    Board sign_up_db = new Board();
//                                    sign_up_db.setEmailId(firebaseUser.getEmail());
//                                    sign_up_db.setPassword(strPwd);
//                                    sign_up_db.setIdToken(firebaseUser.getUid());
//                                    sign_up_db.setUserName(strUserName);
//                                    sign_up_db.setStudentNumber(strStudentNumber);
//                                    sign_up_db.setAnswer(answer);
//                                    sign_up_db.setQuestion(question);
//
//
//                                    //setValue는 database에 insert 행휘
//                                    mDatabaseRef.child(firebaseUser.getUid()).setValue(sign_up_db);
//
//                                    Toast.makeText(SignUp.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getApplicationContext(), SignUpEmail.class);
//                                    startActivity(intent);
//
//                                } else {
//                                    Toast.makeText(SignUp.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

                                //-------------------------------------------

                    } else {
                        Toast.makeText(SignUp.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    mBinding.profileImage.setImageURI(uri);
                    String strEmail1 = mBinding.editTextEmail.getText().toString();

                    StorageReference storageRef = storage.getReference();
                    StorageReference riversRef = storageRef.child("SignUp/" + strEmail1);
                    UploadTask uploadTask = riversRef.putFile(uri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
                }
                break;
        }
    }
}