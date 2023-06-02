package com.example.figma.controller.find;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.figma.R;
import com.example.figma.controller.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class find_id extends AppCompatActivity {
    private EditText editTextTextPersonName, editTextTextPersonName1, editTextTextPersonName6;
    private TextView editTextTextPersonName5, textView38;
    private Button finishBT;
    private ImageButton backButton;
    private String question;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName); //이름 입력
        editTextTextPersonName1 = findViewById(R.id.editTextTextPersonName1); //학번 입력
        editTextTextPersonName6 = findViewById(R.id.editTextTextPersonName6); //질문에 대한 답
        editTextTextPersonName5 = findViewById(R.id.editTextTextPersonName5); //선택지 질문지
        backButton = findViewById(R.id.backButton);
        finishBT = findViewById(R.id.finishBT);
        textView38 = findViewById(R.id.textView38);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                editTextTextPersonName5.setText((CharSequence) adapterView.getItemAtPosition(position));
                question = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("sign_up"); //"sign_up" 노드 가져오기
//                String uid = String.valueOf(editTextTextPersonName.getText());
                String studentNumber1 = editTextTextPersonName1.getText().toString().trim();
                String userName1 = editTextTextPersonName.getText().toString().trim();
                String answer1 = editTextTextPersonName6.getText().toString().trim();
                Query query = databaseRef.orderByChild("userName").equalTo(userName1); //userName1이랑 userName이랑 같은 거 찾기

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String userName = dataSnapshot.child("userName").getValue(String.class);
                            String answer = dataSnapshot.child("answer").getValue(String.class);
                            String emailId = dataSnapshot.child("emailId").getValue(String.class);
                            String question1 = dataSnapshot.child("question").getValue(String.class);

                            if(studentNumber1.equals(studentNumber) && userName1.equals(userName) && answer1.equals(answer) && question.equals(question1)){
                                textView38.setText(emailId);
                                return;
                            }
                        }
                        textView38.setText("일치하는 정보가 없습니다.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        textView38.setText("불러오기 실패");

                    }
                });
            }
        });



    }

}
