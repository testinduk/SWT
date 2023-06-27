package com.example.figma.controller.mypage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figma.R;
import com.example.figma.controller.Login;
import com.example.figma.controller.MainHome;
import com.example.figma.controller.myinformation.MyInfDetails;
import com.example.figma.controller.timetable.SelectItemAdapter;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.figma.databinding.MypageBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mypage extends AppCompatActivity {
    private MypageBinding mBinding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private LinearLayoutManager myListLayoutManager;
    private List<Board> arrayList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView.Adapter MypageAdapter;
    private RecyclerView.LayoutManager layoutManager;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = MypageBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        arrayList = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String idToken = currentUser.getUid();

        DatabaseReference signUpRef = FirebaseDatabase.getInstance().getReference("signUp").child(idToken);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String idToken = snapshot.child("idToken").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        signUpRef.addListenerForSingleValueEvent(valueEventListener);

        myListLayoutManager = new LinearLayoutManager(this);
        mBinding.mypageWroteRecycler.setLayoutManager(myListLayoutManager);

        // 내 프로필 내용 불러오기
        db.collection("signUp").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                String userName = document.getString("userName");
                                String studentNumber = document.getString("studentNumber");
                                String profileUri = document.getString("profileUri");

                                mBinding.mypageName.setText(userName);
                                mBinding.mypageClassGrade.setText(studentNumber);
                                Glide.with(Mypage.this)
                                        .load(profileUri)
                                        .into(mBinding.mypageProfilePicture);
                            }
                        }
                    }
                });

        // 내가 쓴 글 불러오기
        arrayList = new ArrayList<>();
        MypageAdapter = new MypageAdapter(arrayList, this);
        mBinding.mypageWroteRecycler.setAdapter(MypageAdapter);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("sharing Board");

        Query query = databaseReference.orderByChild("idToken").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Board board = dataSnapshot.getValue(Board.class);
                    if(board != null){
                        arrayList.add(board);
                    }
                }
                MypageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Query query = databaseRef.child("SignUp").orderByChild("idToken").equalTo(uid);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        String userName = snapshot.child("userName").getValue(String.class);
//                        String studentNumber = snapshot.child("studentNumber").getValue(String.class);
//
//                        mBinding.mypageName.setText(userName);
//                        mBinding.mypageClassGrade.setText(studentNumber);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        // 종합정보시스템 버튼 하이퍼 링크
        mBinding.schoolPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://student.induk.ac.kr/KR/login.do"));
                startActivity(urlIntent);
            }
        });

        // 스마트클래스 버튼 하이퍼링크
        mBinding.smartClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lms.induk.ac.kr/login.php"));
                startActivity(urlIntent);
            }
        });


        // 뒤로가기 버튼
        mBinding.backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });

        // 정보수정 버튼
        mBinding.myInfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyInfDetails.class);
                startActivity(intent);
            }
        });

        // 로그아웃
        mBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("로그아웃버튼",": 클릭");
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });
    }

    private void documentRoute(String documentType) {
        db.collection("board").document(documentType).collection(uid)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {


                                }
                            }
                        }

                    }
                });

    }
}