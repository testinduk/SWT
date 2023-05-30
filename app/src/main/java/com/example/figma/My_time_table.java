package com.example.figma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.figma.databinding.MyTimeTableBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class My_time_table extends AppCompatActivity {

    private MyTimeTableBinding mBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private LinearLayoutManager layoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 액티비티 바인딩 객체에 할당 및 뷰 설정
        mBinding = MyTimeTableBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);



        layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView1.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        db  = FirebaseFirestore.getInstance();

        // 확인 버튼 클릭시 스피너 내용 들어와야함
        mBinding.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Major_Time").document("정보통신공학").collection("1학년").document("A반")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document != null && document.exists()) {
                                        Map<String, Object> data = document.getData();
                                        handleData(data);
                                    }
                                } else {
                                    Log.i("log", "실패");
                                }

                            }
                        });
            }
        });




        // 뒤로가기
        mBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_home.class);
                startActivity(intent);
            }
        });

    }

    private void handleData(Map<String, Object> data) {
        List<DataField> fieldList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Create a Field object and add it to the list
            DataField field = new DataField(fieldName, fieldValue);
            fieldList.add(field);
        }

        // Set up the RecyclerView adapter
        Major_adapter adapter = new Major_adapter(fieldList);
        mBinding.recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
