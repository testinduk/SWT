package com.example.figma;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bullentin_board_writing extends Activity {
    //uid 불러오기.
    public String uid = null ;
    public Timestamp timestamp;
    //파이어베이스 데이터베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(데이블 또는 속성)의 위치까지는 들어가지는 않는 모습이다.
    private DatabaseReference databaseReference = database.getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    {
        this.uid = user.getUid();
    }

    Button btn;
    EditText edit1, edit2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullentin_board_writing);

        btn = findViewById(R.id.button); //버튼 아이디 연결
        edit1 = findViewById(R.id.editTextTextPersonName5); // 제목 적는 곳
        edit2 = findViewById(R.id.editTextTextPersonName6); // 내용 적는 곳

        //버튼 누르면 값을 저장
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
                startActivity(intent);

                //에딧 텍스트 값을 문자열로 바꾸어 함수에 넣어줍니다.
                add_bullentin_board(edit1.getText().toString(),edit2.getText().toString());

            }
        });


    }
    //값을 파이어베이스 Realtime database로 넘기는 함수
    public void add_bullentin_board(String title, String content) {
        //여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능합니다.
        // ex) 갓 태어난 동물만 입력해서 int age=1; 등을 넣는 경우
        //sharing_DB.java에서 선언했던 함수.
        Timestamp timestamp = Timestamp.now();
        bullentin_DB bullentin_db = new bullentin_DB(title, content, uid);

        DatabaseReference bullentin_boardRef = databaseReference.child("bullentin Board").push();
        bullentin_boardRef.setValue(bullentin_db);

//        Calendar expiration = Calendar.getInstance();
//        expiration.add(Calendar.HOUR_OF_DAY, 12);
//        long expirationTimestamp = expiration.getTimeInMillis();
//        //child는 해당 키 위치로 이동하는 함수입니다.
//        //키가 없는데 "sharing Board"와 title,content 같이 값을 지정한 경우 자동으로 생성합니다.
//        databaseReference.child("User").push().child(uid).child(sharingRef.getKey()).setValue(expirationTimestamp);

        Intent i = new Intent(bullentin_board_writing.this , bullentin_board.class);
        startActivity(i);
        finish();




    // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
                startActivity(intent);
            }
        });
    }
}
