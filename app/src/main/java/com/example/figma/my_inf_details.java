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

import com.google.firebase.database.annotations.Nullable;


public class my_inf_details extends Activity {
    static final int REQUEST_CODE=0;
    private static final int CALL_MY_INF_DETAILS2 =0; // intent 객체와 상수값을 전달
    EditText et1, et2, et3, et4;


    ImageView imageView;
    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inf_details);

        imageView = findViewById(R.id.changeImage);
        imageButton = findViewById(R.id.cameraButton);

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
    public void onButton1Click(View view) { // 수정 버튼
        et1 = findViewById(R.id.password1);
        String name = et1.getText().toString();

        et2 = findViewById(R.id.ch_password1);
        String birthdate = et2.getText().toString();

        et3 = findViewById(R.id.grade1);
        String phone = et3.getText().toString();

        et4 = findViewById(R.id.class2);
        String address = et4.getText().toString();

        Intent intent = new Intent(this.getApplicationContext(), my_inf_details2.class);
        intent.putExtra("name", name); // 인텐트에 정보를 실어줌
        intent.putExtra("birthdate", birthdate);
        intent.putExtra("phone", phone);
        intent.putExtra("address", address);

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
                    String name = data.getStringExtra("name");
                    String birthdate = data.getStringExtra("birthdate");
                    String phone = data.getStringExtra("phone");
                    String address = data.getStringExtra("address");

                    et1.setText(name); // 인텐트에서 읽어온 값 다시 설정
                    et2.setText(birthdate);
                    et3.setText(phone);
                    et4.setText(address);
                }
                break;
        }
    }
}