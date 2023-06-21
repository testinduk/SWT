package com.example.figma.controller.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.controller.MainHome;
import com.example.figma.controller.bulletin.BulletinBoard;
import com.example.figma.controller.mypage.Mypage;
import com.example.figma.controller.sharing.SharingBoard;
import com.example.figma.databinding.ChatPersonBinding;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ChatPerson extends AppCompatActivity {
    private ChatPersonBinding mBinding;
    private RecyclerView mStudentRecyclerView , mProfessorRecyclerView, mAssistantRecyclerView;
    private StudentAdapter mAdapter;
    private ProfessorAdapter mProfessorAdapter;
    private AssistantAdapter mAssistantAdapter;
    private List<Board> mStudentList = new ArrayList<Board>();
    private List<Board> mProfessorList = new ArrayList<Board>();
    private List<Board> mAssistantList = new ArrayList<Board>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mBinding = ChatPersonBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        //RecyclerView 초기화
        mStudentRecyclerView = mBinding.StudentList;
        mStudentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStudentRecyclerView.setAdapter(mAdapter);

        mProfessorRecyclerView = mBinding.TeacherList;
        mProfessorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProfessorRecyclerView.setAdapter(mProfessorAdapter);

        mAssistantRecyclerView = mBinding.AssistantList;
        mAssistantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAssistantRecyclerView.setAdapter(mAssistantAdapter);

        FirebaseFirestore studentdb = FirebaseFirestore.getInstance();
        studentdb.collection("signUp")
                .whereEqualTo("position", "학생")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String name = documentSnapshot.getString("userName");
                                String studentNumber = documentSnapshot.getString("studentNumber");
                                String photo = documentSnapshot.getString("profileUri");
                                String receiverUUID = documentSnapshot.getString("uid");

                                Board student = new Board();
                                student.setUserName(name);
                                student.setStudentNumber(studentNumber);
                                student.setProfileUri(photo);
                                student.setReceiverUUID(receiverUUID);


                                mStudentList.add(student);
                            }
                            mAdapter = new StudentAdapter(getApplicationContext(),mStudentList);
                            mStudentRecyclerView.setAdapter(mAdapter);
                        }

                    }
                });

        FirebaseFirestore professordb = FirebaseFirestore.getInstance();
        professordb.collection("signUp")
                .whereEqualTo("position", "교수")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String name = documentSnapshot.getString("userName");
                                String studentNumber = documentSnapshot.getString("studentNumber");
                                String photo = documentSnapshot.getString("profileUri");

                                Board student = new Board();
                                student.setUserName(name);
                                student.setStudentNumber(studentNumber);
                                student.setProfileUri(photo);

                                mProfessorList.add(student);
                            }
                            mProfessorAdapter = new ProfessorAdapter(mProfessorList);
                            mProfessorRecyclerView.setAdapter(mProfessorAdapter);
                        }

                    }
                });
        FirebaseFirestore assistantdb = FirebaseFirestore.getInstance();
        assistantdb.collection("signUp")
                .whereEqualTo("position", "조교")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String name = documentSnapshot.getString("userName");
                                String studentNumber = documentSnapshot.getString("studentNumber");
                                String photo = documentSnapshot.getString("profileUri");

                                Board student = new Board();
                                student.setUserName(name);
                                student.setStudentNumber(studentNumber);
                                student.setProfileUri(photo);

                                mAssistantList.add(student);
                            }
                            mAssistantAdapter = new AssistantAdapter(mAssistantList);
                            mAssistantRecyclerView.setAdapter(mAssistantAdapter);
                        }

                    }
                });

        mBinding.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatPerson.class);
                startActivity(intent);
            }
        });

        mBinding.sharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingBoard.class);
                startActivity(intent);
            }
        });

        mBinding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
        mBinding.boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BulletinBoard.class);
                startActivity(intent);
            }
        });

        mBinding.mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainHome.class);
                startActivity(intent);
            }
        });
    }

}
