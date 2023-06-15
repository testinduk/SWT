package com.example.figma.controller.bulletin;


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

import com.example.figma.databinding.BulletinBoardCommentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BulletinBoardComment extends Activity {

    private BulletinBoardCommentBinding mBinding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Board> arrayList;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = BulletinBoardCommentBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        mBinding.bulletinBoardCommentRecyclerView.setHasFixedSize(true);
        mBinding.bulletinBoardCommentRecyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();

        String bulletin_key = intent.getStringExtra("key");

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();



        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });

        //댓글 추가하기
        mBinding.commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = databaseReference.child("SignUp").orderByChild("idToken").equalTo(uid);
                String comment_content = mBinding.comment.getText().toString();

                query.addListenerForSingleValueEvent(new ValueEventListener() { //SignUp 노드 불러오기
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String studentNumber = dataSnapshot.child("studentNumber").getValue(String.class);
                            String username = dataSnapshot.child("userName").getValue(String.class);

                            String current_time = getCurrentTime();


                            String comment_UUID = UUID.randomUUID().toString();
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
                    Board user = document.toObject(Board.class);
                    arrayList.add(user);
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();
            }
        });


        adapter = new BulletinComAdapter(arrayList, this);
        mBinding.bulletinBoardCommentRecyclerView.setAdapter(adapter);


    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}



