package com.example.figma;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.figma.databinding.TimetableBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Timetable extends Fragment {


    private TimetableBinding mBidng;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBidng = TimetableBinding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        db  = FirebaseFirestore.getInstance();

        db.collection("test").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    // 결과 값이 있을 시 배열에 담기
                    if (document.exists()) {
                        ArrayList<String> subjects = new ArrayList<>();
                        for (String fieldName : document.getData().keySet()) {
                            String sub = document.getString(fieldName);
                            subjects.add(sub);
                        }

                        // 배열안에서 특정값 찾기
                        for (String subject : subjects) {
                            // 1A
                            if(subject.equals("C언어(A)")) {
                                mBidng.time62.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time72.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time82.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time62.setText("C언어");
                                mBidng.time72.setText("유봉선\n302");
                            }
                            if(subject.equals("대학수학(A)")) {
                                mBidng.time61.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time71.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time81.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time61.setText("대학수학");
                                mBidng.time71.setText("한성일\n203");
                            }
                            if(subject.equals("정보통신개론(A)")) {
                                mBidng.time32.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time32.setText("정보통신\n개론");
                                mBidng.time42.setText("송재철\n306");
                            }
                            if(subject.equals("인공지능개론(A)")) {
                                mBidng.time63.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time73.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time83.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time63.setText("인공지능개론");
                                mBidng.time73.setText("황선철\n305");
                            }
                            if(subject.equals("채플")) {
                                mBidng.time22.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time22.setText("채플\n강당");
                            }
                            if(subject.equals("비전과진로(A)")) {
                                mBidng.time23.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time23.setText("비전과\n진로\n이보영\n304");
                            }
                            if(subject.equals("컴퓨터공학개론(A)")) {
                                mBidng.time33.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time43.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time33.setText("컴퓨터\n공학\n개론");
                                mBidng.time43.setText("이보영\n304");
                            }
                            if(subject.equals("전자통신공학(이론)(A)")) {
                                mBidng.time25.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time35.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time25.setText("전자통신\n공학\n(이론)");
                                mBidng.time35.setText("전광일\n305");
                            }
                            if(subject.equals("전자통신공학(실습)(A)")) {
                                mBidng.time45.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time55.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time45.setText("전자통신\n공학\n(실습)");
                                mBidng.time55.setText("전광일\n106");
                            }

                            //2A
                            if(subject.equals("데이터통신(A)")) {
                                mBidng.time21.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time31.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time41.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time21.setText("데이터\n통신");
                                mBidng.time31.setText("최흥택\n304");
                            }
                            if(subject.equals("아날로그통신시스템(A)")) {
                                mBidng.time22.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time32.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time22.setText("아날로그\n통신\n시스템");
                                mBidng.time32.setText("유봉선\n305");
                            }
                            if(subject.equals("서버시스템(A)")) {
                                mBidng.time62.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time72.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time82.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time62.setText("서버\n시스템");
                                mBidng.time72.setText("신범수\n203");
                            }
                            if(subject.equals("데이터베이스(A)")) {
                                mBidng.time63.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time73.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time83.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time63.setText("데이터\n베이스");
                                mBidng.time73.setText("채성수\n305");
                            }
                            if(subject.equals("모바일프로그래밍(A)")) {
                                mBidng.time14.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time24.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time34.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time14.setText("모바일\n프로그래밍");
                                mBidng.time24.setText("송재철\n203");
                            }

                            //3A
                            if(subject.equals("네트워크보안(A)")) {
                                mBidng.time21.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time31.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time41.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time21.setText("네트워크\n보안");
                                mBidng.time31.setText("이보영\n206");
                            }
                            if(subject.equals("AI와영상처리(A)")) {
                                mBidng.time61.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time71.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time81.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time61.setText("AI와\n영상처리");
                                mBidng.time71.setText("황선철\n302");
                            }
                            if(subject.equals("네트워크2(A)")) {
                                mBidng.time22.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time32.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time22.setText("네트워크2");
                                mBidng.time32.setText("김행민\n205");
                            }
                            if(subject.equals("네트워크프로그래밍(A)")) {
                                mBidng.time62.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time72.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time82.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time62.setText("네트워크\n프로그래밍");
                                mBidng.time72.setText("한성일\n206");
                            }
                            if(subject.equals("캡스톤디자인1(A)")) {
                                mBidng.time23.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time33.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time43.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time23.setText("캡스톤\n디자인1");
                                mBidng.time33.setText("나경필\n204");
                            }
                            if(subject.equals("사물인터넷(A)")) {
                                mBidng.time63.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time73.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time83.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time63.setText("사물\n인터넷");
                                mBidng.time73.setText("조돈제\n302");
                            }
                            if(subject.equals("알고리즘(A)")) {
                                mBidng.time14.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time24.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time34.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time14.setText("알고리즘");
                                mBidng.time24.setText("전광일\n302");
                            }
                            if(subject.equals("실전취업(A)")) {
                                mBidng.time15.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time15.setText("실전취업\n송재철\n304");
                            }
                            if(subject.equals("디지털전송시스템(A)")) {
                                mBidng.time25.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time35.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time45.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time25.setText("디지털\n전송\n시스템");
                                mBidng.time35.setText("유봉선\n304");
                            }

                            //1B
                            if(subject.equals("C언어(B)")) {
                                mBidng.time64.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time74.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time84.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time64.setText("C언어");
                                mBidng.time74.setText("유봉선\n302");
                            }
                            if(subject.equals("대학수학(B)")) {
                                mBidng.time25.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time35.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time45.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time25.setText("대학수학");
                                mBidng.time35.setText("한성일\n203");
                            }
                            if(subject.equals("정보통신개론(B)")) {
                                mBidng.time62.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time72.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time62.setText("정보통신\n개론");
                                mBidng.time72.setText("송재철\n306");
                            }
                            if(subject.equals("인공지능개론(B)")) {
                                mBidng.time14.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time24.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time34.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time14.setText("인공지능개론");
                                mBidng.time24.setText("황선철\n305");
                            }
                            if(subject.equals("비전과진로(B)")) {
                                mBidng.time12.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time12.setText("비전과\n진로\n이보영\n304");
                            }
                            if(subject.equals("컴퓨터공학개론(B)")) {
                                mBidng.time32.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time32.setText("컴퓨터\n공학\n개론");
                                mBidng.time42.setText("이보영\n304");
                            }
                            if(subject.equals("전자통신공학(이론)(B)")) {
                                mBidng.time23.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time33.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time23.setText("전자통신\n공학\n(이론)");
                                mBidng.time33.setText("전광일\n305");
                            }
                            if(subject.equals("전자통신공학(실습)(B)")) {
                                mBidng.time43.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time53.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time43.setText("전자통신\n공학\n(실습)");
                                mBidng.time53.setText("전광일\n106");
                            }

                            //2B
                            if(subject.equals("데이터통신(B)")) {
                                mBidng.time61.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time71.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time81.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time61.setText("데이터\n통신");
                                mBidng.time71.setText("최흥택\n304");
                            }
                            if(subject.equals("아날로그통신시스템(B)")) {
                                mBidng.time63.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time73.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time83.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time63.setText("아날로그\n통신\n시스템");
                                mBidng.time73.setText("유봉선\n305");
                            }
                            if(subject.equals("서버시스템(B)")) {
                                mBidng.time22.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time32.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time22.setText("서버\n시스템");
                                mBidng.time32.setText("신범수\n203");
                            }
                            if(subject.equals("데이터베이스(B)")) {
                                mBidng.time23.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time33.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time43.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time23.setText("데이터\n베이스");
                                mBidng.time33.setText("채성수\n305");
                            }
                            if(subject.equals("모바일프로그래밍(B)")) {
                                mBidng.time64.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time74.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time84.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time64.setText("모바일\n프로그래밍");
                                mBidng.time74.setText("송재철\n203");
                            }

                            //3B
                            if(subject.equals("네트워크보안(B)")) {
                                mBidng.time14.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time24.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time34.setBackgroundResource(R.drawable.b_outside);
                                mBidng.time14.setText("네트워크\n보안");
                                mBidng.time24.setText("이보영\n206");
                            }
                            if(subject.equals("AI와영상처리(B)")) {
                                mBidng.time21.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time31.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time41.setBackgroundResource(R.drawable.g_outside);
                                mBidng.time21.setText("AI와\n영상처리");
                                mBidng.time31.setText("황선철\n302");
                            }
                            if(subject.equals("네트워크2(B)")) {
                                mBidng.time62.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time72.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time82.setBackgroundResource(R.drawable.a_outside);
                                mBidng.time62.setText("네트워크2");
                                mBidng.time72.setText("김행민\n205");
                            }
                            if(subject.equals("네트워크프로그래밍(B)")) {
                                mBidng.time64.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time74.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time84.setBackgroundResource(R.drawable.e_outside);
                                mBidng.time64.setText("네트워크\n프로그래밍");
                                mBidng.time74.setText("한성일\n206");
                            }
                            if(subject.equals("캡스톤디자인1(B)")) {
                                mBidng.time63.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time73.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time83.setBackgroundResource(R.drawable.d_outside);
                                mBidng.time63.setText("캡스톤\n디자인1");
                                mBidng.time73.setText("나경필\n204");
                            }
                            if(subject.equals("사물인터넷(B)")) {
                                mBidng.time23.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time33.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time43.setBackgroundResource(R.drawable.i_outside);
                                mBidng.time23.setText("사물\n인터넷");
                                mBidng.time33.setText("조돈제\n302");
                            }
                            if(subject.equals("알고리즘(B)")) {
                                mBidng.time22.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time32.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time42.setBackgroundResource(R.drawable.h_outside);
                                mBidng.time22.setText("알고리즘");
                                mBidng.time32.setText("전광일\n302");
                            }
                            if(subject.equals("실전취업(B)")) {
                                mBidng.time13.setBackgroundResource(R.drawable.c_outside);
                                mBidng.time13.setText("실전취업\n송재철\n304");
                            }
                            if(subject.equals("디지털전송시스템(B)")) {
                                mBidng.time65.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time75.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time85.setBackgroundResource(R.drawable.f_outside);
                                mBidng.time65.setText("디지털\n전송\n시스템");
                                mBidng.time75.setText("유봉선\n304");
                            }




                        }


                    } else {
                    Log.d("log", "No such document");
                    }
                } else {
                    Log.d("log", "get failed with ", task.getException());
                }
            }
        });

        return mBidng.getRoot();
    }


}
