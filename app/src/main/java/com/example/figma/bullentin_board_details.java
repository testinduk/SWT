package com.example.figma;


import android.content.DialogInterface;


import android.app.Activity;

import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.Nullable;
public class bullentin_board_details extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullentin_board_details);


        // 수정 버튼
        ImageButton btn_bul_amend = findViewById(R.id.btn_bul_amend);

        btn_bul_amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), bullentin_board_writing.class);
                startActivity(intent);
            }
        });


        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
                startActivity(intent);
            }
        });


        // 삭제 메시지
        ImageButton btn_dul_delete = findViewById(R.id.btn_bul_del);
        btn_dul_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(bullentin_board_details.this);
                builder.setTitle("경고메시지");
                builder.setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(bullentin_board_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("취소", null);
                builder.create().show();
            }
        });
    }
}