package com.swt.figma.controller.find;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.swt.figma.R;
import com.swt.figma.controller.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.swt.figma.databinding.FindIdBinding;

public class FindId extends AppCompatActivity {
    private FindIdBinding mBinding;
    private String question;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id);

        mBinding = FindIdBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.findQuestionIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mBinding.editTextTextPersonName5.setText((CharSequence) adapterView.getItemAtPosition(position));
                question = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        mBinding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("SignUp"); //"SignUp" 노드 가져오기
                String studentNumber1 = mBinding.enterStudentNumber.getText().toString().trim();
                String userName1 = mBinding.enterName.getText().toString().trim();
                String answer1 = mBinding.enterAnswer.getText().toString().trim();
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
                                mBinding.result.setText(emailId);
                                return;
                            }
                        }
                        mBinding.result.setText("일치하는 정보가 없습니다.");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        mBinding.result.setText("불러오기 실패");

                    }
                });
            }
        });



    }

}
