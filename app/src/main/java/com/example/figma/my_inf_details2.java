package com.example.figma;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class my_inf_details2 extends Activity {
    EditText et5, et6, et7, et8;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inf_details2);

        Intent intent = getIntent(); // 실려온 인텐트 정보를 담음
        String newpw = intent.getStringExtra("name");
        String newpwcheck = intent.getStringExtra("birthdate");
        String grade = intent.getStringExtra("phone");
        String abclass = intent.getStringExtra("address");

        et5 = findViewById(R.id.password2);
        et5.setText(newpw);

        et6 = findViewById(R.id.ch_password2);
        et6.setText(newpwcheck);

        et7 = findViewById(R.id.grade2);
        et7.setText(grade);

        et8 = findViewById(R.id.class3);
        et8.setText(abclass);

    }

    public void onButton2Click(View view) { // 확인 버튼
        Intent data = new Intent();
        Intent intent = new Intent(getApplicationContext(), mypage.class);
        startActivity(intent);

        String newpw = et5.getText().toString(); // 읽어와서 변수에 담음
        String newpwcheck = et6.getText().toString();
        String grade = et7.getText().toString();
        String abclass = et8.getText().toString();

        data.putExtra("name", newpw); // 인텐트에 내용 실음
        data.putExtra("birthdate", newpwcheck);
        data.putExtra("phone", grade);
        data.putExtra("address", abclass);

        /* 현재 액티비티 넘기고 메인 메소드 호출하며 종료 */
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}