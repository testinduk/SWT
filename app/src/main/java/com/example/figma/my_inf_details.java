package com.example.figma;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;

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

import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.ktx.Firebase;


public class my_inf_details extends Activity {
    static final int REQUEST_CODE=0;
    private static final int CALL_MY_INF_DETAILS2 =0; // intent 객체와 상수값을 전달
    private EditText et1, et2, et3, et4;


    private ImageView imageView;
    private ImageButton imageButton;
    private Button certify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inf_details);

        imageView = findViewById(R.id.changeImage);
        imageButton = findViewById(R.id.cameraButton);
        certify = findViewById(R.id.send_email);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
            }
        });

        // warningButton
        Button warningButton = findViewById(R.id.remove);
        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), warning_message.class);
                startActivity(intent);
            }
        });

        certify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });


            }
        });



    }
    public void onButton1Click(View view) { // 수정 버튼
        et1 = findViewById(R.id.password1);
        String newpw = et1.getText().toString();

        et2 = findViewById(R.id.ch_password1);
        String newpwcheck = et2.getText().toString();

        et3 = findViewById(R.id.grade1);
        String grade = et3.getText().toString();

        et4 = findViewById(R.id.class2);
        String abclass = et4.getText().toString();

        Intent intent = new Intent(this.getApplicationContext(), my_inf_details2.class);
        intent.putExtra("newpassword", newpw); // 인텐트에 정보를 실어줌
        intent.putExtra("newpasswordcheck", newpwcheck);
        intent.putExtra("grade", grade);
        intent.putExtra("class", abclass);

        //startActivity(intent);
        startActivityForResult(intent, CALL_MY_INF_DETAILS2); // SubActivity에서 수정된 값(리턴값)을 받음
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageView.setImageURI(uri);
                }
                break;
        }
        switch(requestCode){
            case CALL_MY_INF_DETAILS2:
                if(resultCode == RESULT_OK) { // 잘 넘어왔으면 resultCode가 ok값을 가져옴
                    String newpw = data.getStringExtra("newpassword");
                    String newpwcheck = data.getStringExtra("newpasswordcheck");
                    String grade = data.getStringExtra("grade");
                    String abclass = data.getStringExtra("class");

                    et1.setText(newpw); // 인텐트에서 읽어온 값 다시 설정
                    et2.setText(newpwcheck);
                    et3.setText(grade);
                    et4.setText(abclass);
                }
                break;
        }
    }
}