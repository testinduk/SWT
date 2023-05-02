package com.example.figma;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sharing_details extends Activity {
    private TextView tv_username;
    private TextView tv_content;
    private TextView tv_title;
    private TextView textView3;
    private ImageView photo_image;

    private Button edit_button;
    private ImageButton delete_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);

       
        // 수정 버튼
        tv_username = findViewById(R.id.textView2);
        tv_title = findViewById(R.id.textView1);
        tv_content = findViewById(R.id.textView4);
        edit_button = findViewById(R.id.btn_sha_amend);
        delete_button = findViewById(R.id.Button3);
        photo_image = findViewById(R.id.photo_image);
        textView3 = findViewById(R.id.textView3);

        Intent second_intent = getIntent();

        String shar_username = second_intent.getStringExtra("username");
        String shar_title = second_intent.getStringExtra("title");
        String shar_content = second_intent.getStringExtra("content");
        String shar_idToken = second_intent.getStringExtra("idToken");
        String shar_key = second_intent.getStringExtra("key");
        String sharing_image = second_intent.getStringExtra("image");
        String sharing_time = second_intent.getStringExtra("time");
//        Log.e("image",sharing_image);

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Log.i("image", sharing_image);

        tv_username.setText(shar_username);
        tv_content.setText(shar_content);
        tv_title.setText(shar_title);
        Glide.with(this)
                .load(sharing_image)
                .into(photo_image);
        textView3.setText(sharing_time);

        if (uid.equals(shar_idToken)) {
            edit_button.setEnabled(true);
            delete_button.setEnabled(true);
            //수정 버튼
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shar_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), sharing_edit.class);
                        intent.putExtra("id", shar_idToken);
                        intent.putExtra("title",shar_title);
                        intent.putExtra("content",shar_content);
                        intent.putExtra("key",shar_key);
                        intent.putExtra("image",sharing_image);
                        Log.i("id", shar_idToken);
                        Log.i("title",shar_title);
                        Log.i("content",shar_content);
                        Log.i("key",shar_key);
                        Log.i("image",sharing_image);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            //글 삭제하기.
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shar_idToken != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(sharing_details.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                                startActivity(intent);
                                ref.child("sharing Board").child(shar_key).removeValue();
                                Toast.makeText(sharing_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();
                    }
                }
            });

        } else {
            edit_button.setEnabled(false);
            delete_button.setEnabled(false);
        }

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });
    }
}
