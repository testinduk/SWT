package com.example.figma;

import static android.content.ContentValues.TAG;

import static com.example.figma.my_inf_details.REQUEST_CODE;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
//import com.google.android.auth.AuthResult;

public class sign_up extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser; //안드로이드와 파이어베이스 사이의 인증을 확인하기 위한 인스턴스 선언
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText editTextTextPersonName4, editTextTextPassword, editTextTextPersonName, editTextTextPersonName2, editTextNumberPassword, editTextTextPersonName3; //회원가입 입력필드
    private Button finishBT; //회원가입 버튼
    ImageView imageView;
    ImageButton imageButton;
    String TAG = "sign_up";

    private FirebaseStorage storage;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);

//                    Random random = new Random();

                    String strEmail1 = editTextTextPersonName4.getText().toString();

                    StorageReference storageRef = storage.getReference();
                    StorageReference riversRef = storageRef.child("sign_up/" + strEmail1);
                    UploadTask uploadTask = riversRef.putFile(uri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(sign_up.this, "성공", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }

    private StorageReference storageRef;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);

                    String email = editTextTextPersonName4.getText().toString();
                    StorageReference imageRef = storageRef.child("sign_up/" + email);
                    imageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // 이미지 업로드 성공
                            Log.i("log", "Image upload successful");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // 이미지 업로드 실패
                            Log.e("log", "Image upload failed: " + e.getMessage());
                        }
                    });

                }

                break;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

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

        imageView = findViewById(R.id.imageView10);
        imageButton = findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

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


                StorageReference storageRef = storage.getReference();


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

                                    storageRef.child("sign_up/" + strEmail).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Log.i ("log", String.valueOf(uri));
                                            sign_up_db.setSign_up_image(String.valueOf(uri));
                                            Log.i("log", String.valueOf(uri));
                                            if (uri != null) {
                                                sign_up_db.setSign_up_image(uri.toString());

                                                //setValue는 database에 insert 행휘
                                                mDatabaseRef.child(firebaseUser.getUid()).setValue(sign_up_db);
                                            } else {
                                                Log.e("loggg", "실패패패");
                                            }


                                        }
                                    });


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

    }


}