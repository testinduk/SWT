package com.example.figma;


import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class my_inf_details extends Activity {
    static final int REQUEST_CODE=0;
    ImageView imageView;
    ImageButton imageButton;
    EditText chpw1, chpw2, chname, chnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inf_details);

        imageView = findViewById(R.id.changeImage);     // 이미지
        imageButton = findViewById(R.id.cameraButton);  // 이미지 변경 버튼
        chpw1 = findViewById(R.id.password1);           // 새로운 비밀번호
        chpw2 = findViewById(R.id.ch_password1);        // 비밀번호 확인
        chname = findViewById(R.id.grade1);             // 이름
        chnum = findViewById(R.id.class2);              // 학번


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();    //현재 사용자 uid 저장

        Query query = databaseReference.child("sign_up").orderByChild("idToken").equalTo(uid);










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



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
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
    }

}