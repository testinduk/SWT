package com.example.figma.controller.bulletin;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.example.figma.model.bulletin_com_DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class bulletin_board_details extends Activity {
    private TextView textView1; //제목
    private TextView textView2; //글쓴이
    private TextView textView4; //내용
    private TextView textView3; //날짜
    private ImageButton btn_bul_amend; //수정버튼
    private ImageButton btn_bul_del; //삭제버튼
    private ImageButton backButton; //뒤로가기
    private ImageView view2;
    private Button EditText2; //댓글 쓰기
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<bulletin_com_DB> arrayList;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board_details);



        layoutManager = new LinearLayoutManager(this);

        arrayList = new ArrayList<>();
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        textView3 = findViewById(R.id.textView3);
        btn_bul_amend = findViewById(R.id.btn_bul_amend);
        btn_bul_del = findViewById(R.id.btn_bul_del);
        backButton = findViewById(R.id.backButton);
        view2 = findViewById(R.id.view2);

        EditText2 = findViewById(R.id.EditText2);



        Intent second_intent = getIntent();

        String bulletin_username = second_intent.getStringExtra("username");
        String bulletin_title = second_intent.getStringExtra("title");
        String bulletin_content = second_intent.getStringExtra("content");
        String bulletin_idToken = second_intent.getStringExtra("idToken");
        String bulletin_key = second_intent.getStringExtra("key");
        String bulletin_image = second_intent.getStringExtra("image");
        String bulletin_time = second_intent.getStringExtra("time");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        textView2.setText(bulletin_username);
        textView4.setText(bulletin_content);
        textView1.setText(bulletin_title);
        Glide.with(this)
                .load(bulletin_image)
                .into(view2);
        textView3.setText(bulletin_time);

        if (uid.equals(bulletin_idToken)) {
            btn_bul_amend.setEnabled(true);
            btn_bul_del.setEnabled(true);
            //수정 버튼
            btn_bul_amend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bulletin_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), bulletin_board_edit.class);
                        intent.putExtra("id", bulletin_idToken);
                        intent.putExtra("title",bulletin_title);
                        intent.putExtra("content",bulletin_content);
                        intent.putExtra("key",bulletin_key);
                        intent.putExtra("image",bulletin_image);
                        Log.i("id", bulletin_idToken);
                        Log.i("title",bulletin_title);
                        Log.i("content",bulletin_content);
                        Log.i("key",bulletin_key);
                        startActivity(intent);
                    } else {
                        Log.i("id", "bulletin_idToken is null");
                    }
                }
            });
            //글 삭제하기.
            btn_bul_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bulletin_idToken != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(bulletin_board_details.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                                startActivity(intent);
                                ref.child("bulletin Board").child(bulletin_key).removeValue();
                                Toast.makeText(bulletin_board_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();
                    }
                }
            });

        } else {
            btn_bul_amend.setEnabled(false);
            btn_bul_del.setEnabled(false);
        }

        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);
            }
        });

        //채팅창 이동버튼 추가하기(김한용)

        // 댓글창 이동 버튼
        EditText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board_comment.class);
                intent.putExtra("key", bulletin_key);
                startActivity(intent);
            }
        });

        //댓글 추가하기


    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}