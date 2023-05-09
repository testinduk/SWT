package com.example.figma;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class test extends Activity {

    private ImageButton backButton; //뒤로가기
    private ImageView view2;
    private RecyclerView recyclerView;
    private EditText EditText2; //댓글 쓰기
    private ImageButton ImageButton2;//댓글 추가하기 버튼
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<bulletin_com_DB> arrayList;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        recyclerView = findViewById(R.id.recyclerView8);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        backButton = findViewById(R.id.backButton);
        EditText2 = findViewById(R.id.EditText2);
        ImageButton2 = findViewById(R.id.ImageButton2);
        String comment_UUID = UUID.randomUUID().toString();

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



        // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bulletin_board.class);
                startActivity(intent);
            }
        });

        //댓글 추가하기
        ImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = databaseReference.child("sign_up").orderByChild("idToken").equalTo(uid);
                String comment_content = EditText2.getText().toString();

                query.addListenerForSingleValueEvent(new ValueEventListener() { //sign_up 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String username = dataSnapshot.child("userName").getValue(String.class);

                            String current_time = getCurrentTime();


                            Map<String, Object> bulletin_comment = new HashMap<>();
                            bulletin_comment.put("name", username);
                            bulletin_comment.put("studentNumber", studentNumber);
                            bulletin_comment.put("content", comment_content);
                            bulletin_comment.put("time", current_time);
                            db.collection(bulletin_key).document(comment_UUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection(bulletin_key).document(comment_UUID).update(bulletin_comment);
                                        } else {
                                            db.collection(bulletin_key).document(comment_UUID).set(bulletin_comment);

                                        }
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        db.collection(bulletin_key).orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                arrayList.clear();
                for (QueryDocumentSnapshot document : snapshots) {
                    bulletin_com_DB user = document.toObject(bulletin_com_DB.class);
                    arrayList.add(user);
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();
            }
        });


        adapter = new bulletin_com_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);


    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}