package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;



import androidx.appcompat.app.AppCompatActivity;

public class sharing_details extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);

        // 수정 버튼
        ImageButton btn_sha_amend = findViewById(R.id.btn_sha_amend);

        btn_sha_amend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_writing.class);
                startActivity(intent);
            }
        });


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
