package com.example.figma.controller.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.figma.databinding.MyTimeTableBinding;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.figma.controller.MainHome;


public class MyTimeTable extends AppCompatActivity {

    private MyTimeTableBinding mBinding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private ArrayList<String> dp_sp, grade_sp, class_sp;
    private String dp_time;
    private String grade_time;
    private String class_time;

    private String uid = mAuth.getCurrentUser().getUid();

    private List<Board> deleteList = new ArrayList<>();
    private List<Board> mjfieldList = new ArrayList<>();
    private List<Board> gefieldList = new ArrayList<>();
    private List<Board> itemList = new ArrayList<>();
    private List<Board> updateList = new ArrayList<>();
    private List<Board> backList = new ArrayList<>();
    private LinearLayoutManager layoutManager_mj, layoutManager2_ge, layoutManager_pic;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 액티비티 바인딩 객체에 할당 및 뷰 설정
        mBinding = MyTimeTableBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        layoutManager_mj = new LinearLayoutManager(this);
        layoutManager2_ge = new LinearLayoutManager(this);
        layoutManager_pic = new LinearLayoutManager(this);

        mBinding.MajorRecycler.setLayoutManager(layoutManager_mj);
        mBinding.GeRecycler.setLayoutManager(layoutManager2_ge);
        mBinding.PicRecycler.setLayoutManager(layoutManager_pic);

        // 백업을 위한 초기 데이터 저장
        db.collection("timeTable").document(uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data = document.getData();

                                backList.clear();

                                for (Map.Entry<String, Object> entry : data.entrySet()) {
                                    String fieldName = entry.getKey();
                                    Object fieldValue = entry.getValue();
                                    Board itemField = new Board(fieldName, fieldValue);
                                    backList.add(itemField);
                                }
                            }
                        }
                    }
                });
        // ----선택한 과목 리사이클러뷰에 표시 ---- //
        SelectItemAdapter select_adapter = new SelectItemAdapter(itemList);
        mBinding.PicRecycler.setAdapter(select_adapter);
        db.collection("timeTable").document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (snapshots != null && snapshots.exists()) {
                    Map<String, Object> data = snapshots.getData();

                    updateList.clear();

                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        String fieldName = entry.getKey();
                        Object fieldValue = entry.getValue();
                        Board itemField = new Board(fieldName, fieldValue);
                        updateList.add(itemField);
                    }

                    itemList.clear();
                    itemList.addAll(updateList);

                    select_adapter.notifyDataSetChanged();

                }
            }
        });

        // 스피너
        dp_sp = new ArrayList<>();
        grade_sp = new ArrayList<>();
        class_sp = new ArrayList<>();

        dp_sp.add("정보통신공학");
        dp_sp.add("산업경영공학");

        grade_sp.add("1학년");
        grade_sp.add("2학년");
        grade_sp.add("3학년");
        grade_sp.add("전공심화");

        class_sp.add("A반");
        class_sp.add("B반");

        ArrayAdapter<String> adapter_dp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dp_sp);
        adapter_dp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.dpSpinner.setAdapter(adapter_dp);

        ArrayAdapter<String> adapter_grade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, grade_sp);
        adapter_grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.gradeSpinner.setAdapter(adapter_grade);

        ArrayAdapter<String> adapter_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, class_sp);
        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.classSpinner.setAdapter(adapter_class);

        // 학과 스피너
        mBinding.dpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dp_time = (String) parent.getItemAtPosition(position);
                // 선택된 항목을 사용합니다
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 항목도 선택되지 않았을 때의 동작을 정의합니다
            }
        });

        mBinding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade_time = (String) parent.getItemAtPosition(position);
                // 선택된 항목을 사용합니다
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 항목도 선택되지 않았을 때의 동작을 정의합니다
            }
        });

        mBinding.classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class_time = (String) parent.getItemAtPosition(position);
                // 선택된 항목을 사용합니다
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무 항목도 선택되지 않았을 때의 동작을 정의합니다
            }
        });

        // 전공 리사이클러뷰
        mBinding.timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((dp_time != null) && (grade_time != null) && (class_time != null)) {

                    db.collection("Major_Time").document(dp_time).collection(grade_time).document(class_time)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null && document.exists()) {
                                            Map<String, Object> data = document.getData();
                                            mjHandleData(data);
                                        }
                                    } else {
                                    }

                                }
                            });
                } else {
                    Toast.makeText(MyTimeTable.this, "정보를 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // ---- 교양 리사이클러뷰에 표시 ---- //
        List<Board> ge_list = new ArrayList<>();
        GeAdapter geAdapter = new GeAdapter(ge_list);
        mBinding.GeRecycler.setAdapter(geAdapter);

        CollectionReference geClassRef = db.collection("Ge class");
        db.collection("GE class").document("선택").collection("금").document("5-6")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data1 = document.getData();
                                geHandleData(data1);
                            }
                        }
                    }
                });
        db.collection("GE class").document("선택").collection("금").document("7-8")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data2 = document.getData();
                                geHandleData(data2);
                            }
                        }
                    }
                });

        db.collection("GE class").document("선택").collection("목").document("2-3")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data3 = document.getData();
                                geHandleData(data3);
                            }
                        }
                    }
                });

        db.collection("GE class").document("선택").collection("온라인").document("비대면수업")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data4 = document.getData();
                                geHandleData(data4);
                            }
                        }
                    }
                });

        db.collection("GE class").document("선택").collection("월").document("1-2")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data5 = document.getData();
                                geHandleData(data5);
                            }
                        }
                    }
                });

        db.collection("GE class").document("선택").collection("월").document("3-4")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data6 = document.getData();
                                geHandleData(data6);
                            }
                        }
                    }
                });

        db.collection("GE class").document("필수").collection("온라인").document("비대면수업")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Map<String, Object> data7 = document.getData();
                                geHandleData(data7);
                            }
                        }
                    }
                });


        // 선택 삭제 버튼
        mBinding.timeDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택된 필드 가져오기
                deleteList = select_adapter.getDeleteList();

                // 가져온 필드 처리
                for (Board field : deleteList) {
                    String delFieldName = field.getFieldName();
                    deleteField(delFieldName);
                }

                deleteList.clear();

            }
        });

        // 완료 버튼
        mBinding.timeFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });

        // 뒤로가기
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("timeTable").document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.i("뒤로가기 클릭 시 데이터 삭제 ", "성공");
                        } else {
                            Log.i("뒤로가기 클릭 시 데이터 삭제 ", "성공");
                        }
                    }
                });

                Map<String, Object> timeTableData = new HashMap<>();
                for (Board board : backList) {
                    timeTableData.put(board.getFieldName(), board.getFieldValue());
                }

                db.collection("timeTable").document(uid).set(timeTableData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("새로운 데이터 입력", "성공");
                    }
                });

                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
    }

    private void deleteField(String delFieldName) {
        DocumentReference documentReference = db.collection("timeTable").document(uid);
        documentReference.update(delFieldName, FieldValue.delete())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("메소드", "지워진데이터");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("메소드", "실패한데이터");
                    }
                });
    }


    private void mjHandleData(Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Create a Field object and add it to the list
            Board field = new Board(fieldName, fieldValue);
            mjfieldList.add(field);
        }
        MajorAdapter mj_adapter = new MajorAdapter(mjfieldList);
        mBinding.MajorRecycler.setAdapter(mj_adapter);
    }

    private void geHandleData(Map<String, Object> data) {

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Create a Field object and add it to the list
            Board field = new Board(fieldName, fieldValue);
            gefieldList.add(field);
        }
        GeAdapter ge_adapter = new GeAdapter(gefieldList);
        mBinding.GeRecycler.setAdapter(ge_adapter);
    }
}