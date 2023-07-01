package com.example.figma.controller.sharing;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
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

import com.example.figma.databinding.SharingBoardCommentBinding;


public class SharingBoardComment extends Activity {
    private SharingBoardCommentBinding mBinding;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = SharingBoardCommentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.sharingBoardCommentRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mBinding.sharingBoardCommentRecycler.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();

        String sharing_key = intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });

        //댓글 추가하기
        mBinding.sharingBoardCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = databaseReference.child("signUp").orderByChild("idToken").equalTo(uid);
                String comment_content = mBinding.sharingBoardCommentEdit.getText().toString();

                query.addListenerForSingleValueEvent(new ValueEventListener() { //SignUp 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String username = dataSnapshot.child("userName").getValue(String.class);
                            String current_time = getCurrentTime();
                            String comment_UUID = UUID.randomUUID().toString();

                            Map<String, Object> sharing_comment = new HashMap<>();
                            sharing_comment.put("name", username);
                            sharing_comment.put("studentNumber", studentNumber);
                            sharing_comment.put("content", comment_content);
                            sharing_comment.put("time", current_time);
                            db.collection(sharing_key).document(comment_UUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection(sharing_key).document(comment_UUID).update(sharing_comment);
                                        } else {
                                            db.collection(sharing_key).document(comment_UUID).set(sharing_comment);

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

        db.collection(sharing_key).orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                arrayList.clear();
                for (QueryDocumentSnapshot document : snapshots) {
                    Board user = document.toObject(Board.class);
                    arrayList.add(user);
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new SharingComAdapter(arrayList, this);
        mBinding.sharingBoardCommentRecycler.setAdapter(adapter);
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
