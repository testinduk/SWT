package com.example.figma.controller.timetable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.figma.R;
import com.example.figma.databinding.TimetableBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TimeTable extends Fragment {
    private TimetableBinding mBinding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = TimetableBinding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        db  = FirebaseFirestore.getInstance();

        db.collection("timeTable").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                                mBinding.time62.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time72.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time82.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time62.setText("C언어");
                                mBinding.time72.setText("유봉선\n302");
                            }
                            if(subject.equals("대학수학(A)")) {
                                mBinding.time61.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time71.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time81.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time61.setText("대학수학");
                                mBinding.time71.setText("한성일\n203");
                            }
                            if(subject.equals("정보통신개론(A)")) {
                                mBinding.time32.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time32.setText("정보통신\n개론");
                                mBinding.time42.setText("송재철\n306");
                            }
                            if(subject.equals("인공지능개론(A)")) {
                                mBinding.time63.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time73.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time83.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time63.setText("인공지능개론");
                                mBinding.time73.setText("황선철\n305");
                            }
                            if(subject.equals("채플")) {
                                mBinding.time22.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time22.setText("채플\n강당");
                            }
                            if(subject.equals("비전과진로(A)")) {
                                mBinding.time23.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time23.setText("비전과\n진로\n이보영\n304");
                            }
                            if(subject.equals("컴퓨터공학개론(A)")) {
                                mBinding.time33.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time43.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time33.setText("컴퓨터\n공학\n개론");
                                mBinding.time43.setText("이보영\n304");
                            }
                            if(subject.equals("전자통신공학(이론)(A)")) {
                                mBinding.time25.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time35.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time25.setText("전자통신\n공학\n(이론)");
                                mBinding.time35.setText("전광일\n305");
                            }
                            if(subject.equals("전자통신공학(실습)(A)")) {
                                mBinding.time45.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time55.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time45.setText("전자통신\n공학\n(실습)");
                                mBinding.time55.setText("전광일\n106");
                            }

                            //2A
                            if(subject.equals("데이터통신(A)")) {
                                mBinding.time21.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time31.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time41.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time21.setText("데이터\n통신");
                                mBinding.time31.setText("최흥택\n304");
                            }
                            if(subject.equals("아날로그통신시스템(A)")) {
                                mBinding.time22.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time32.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time22.setText("아날로그\n통신\n시스템");
                                mBinding.time32.setText("유봉선\n305");
                            }
                            if(subject.equals("서버시스템(A)")) {
                                mBinding.time62.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time72.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time82.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time62.setText("서버\n시스템");
                                mBinding.time72.setText("신범수\n203");
                            }
                            if(subject.equals("데이터베이스(A)")) {
                                mBinding.time63.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time73.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time83.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time63.setText("데이터\n베이스");
                                mBinding.time73.setText("채성수\n305");
                            }
                            if(subject.equals("모바일프로그래밍(A)")) {
                                mBinding.time14.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time24.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time34.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time14.setText("모바일\n프로그래밍");
                                mBinding.time24.setText("송재철\n203");
                            }

                            //3A
                            if(subject.equals("네트워크보안(A)")) {
                                mBinding.time21.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time31.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time41.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time21.setText("네트워크\n보안");
                                mBinding.time31.setText("이보영\n206");
                            }
                            if(subject.equals("AI와영상처리(A)")) {
                                mBinding.time61.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time71.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time81.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time61.setText("AI와\n영상처리");
                                mBinding.time71.setText("황선철\n302");
                            }
                            if(subject.equals("네트워크2(A)")) {
                                mBinding.time22.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time32.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time22.setText("네트워크2");
                                mBinding.time32.setText("김행민\n205");
                            }
                            if(subject.equals("네트워크프로그래밍(A)")) {
                                mBinding.time62.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time72.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time82.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time62.setText("네트워크\n프로그래밍");
                                mBinding.time72.setText("한성일\n206");
                            }
                            if(subject.equals("캡스톤디자인1(A)")) {
                                mBinding.time23.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time33.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time43.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time23.setText("캡스톤\n디자인1");
                                mBinding.time33.setText("나경필\n204");
                            }
                            if(subject.equals("사물인터넷(A)")) {
                                mBinding.time63.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time73.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time83.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time63.setText("사물\n인터넷");
                                mBinding.time73.setText("조돈제\n302");
                            }
                            if(subject.equals("알고리즘(A)")) {
                                mBinding.time14.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time24.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time34.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time14.setText("알고리즘");
                                mBinding.time24.setText("전광일\n302");
                            }
                            if(subject.equals("실전취업(A)")) {
                                mBinding.time15.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time15.setText("실전취업\n송재철\n304");
                            }
                            if(subject.equals("디지털전송시스템(A)")) {
                                mBinding.time25.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time35.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time45.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time25.setText("디지털\n전송\n시스템");
                                mBinding.time35.setText("유봉선\n304");
                            }

                            //1B
                            if(subject.equals("C언어(B)")) {
                                mBinding.time64.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time74.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time84.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time64.setText("C언어");
                                mBinding.time74.setText("유봉선\n302");
                            }
                            if(subject.equals("대학수학(B)")) {
                                mBinding.time25.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time35.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time45.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time25.setText("대학수학");
                                mBinding.time35.setText("한성일\n203");
                            }
                            if(subject.equals("정보통신개론(B)")) {
                                mBinding.time62.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time72.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time62.setText("정보통신\n개론");
                                mBinding.time72.setText("송재철\n306");
                            }
                            if(subject.equals("인공지능개론(B)")) {
                                mBinding.time14.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time24.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time34.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time14.setText("인공지능개론");
                                mBinding.time24.setText("황선철\n305");
                            }
                            if(subject.equals("비전과진로(B)")) {
                                mBinding.time12.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time12.setText("비전과\n진로\n이보영\n304");
                            }
                            if(subject.equals("컴퓨터공학개론(B)")) {
                                mBinding.time32.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time32.setText("컴퓨터\n공학\n개론");
                                mBinding.time42.setText("이보영\n304");
                            }
                            if(subject.equals("전자통신공학(이론)(B)")) {
                                mBinding.time23.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time33.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time23.setText("전자통신\n공학\n(이론)");
                                mBinding.time33.setText("전광일\n305");
                            }
                            if(subject.equals("전자통신공학(실습)(B)")) {
                                mBinding.time43.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time53.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time43.setText("전자통신\n공학\n(실습)");
                                mBinding.time53.setText("전광일\n106");
                            }

                            //2B
                            if(subject.equals("데이터통신(B)")) {
                                mBinding.time61.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time71.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time81.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time61.setText("데이터\n통신");
                                mBinding.time71.setText("최흥택\n304");
                            }
                            if(subject.equals("아날로그통신시스템(B)")) {
                                mBinding.time63.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time73.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time83.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time63.setText("아날로그\n통신\n시스템");
                                mBinding.time73.setText("유봉선\n305");
                            }
                            if(subject.equals("서버시스템(B)")) {
                                mBinding.time22.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time32.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time22.setText("서버\n시스템");
                                mBinding.time32.setText("신범수\n203");
                            }
                            if(subject.equals("데이터베이스(B)")) {
                                mBinding.time23.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time33.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time43.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time23.setText("데이터\n베이스");
                                mBinding.time33.setText("채성수\n305");
                            }
                            if(subject.equals("모바일프로그래밍(B)")) {
                                mBinding.time64.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time74.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time84.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time64.setText("모바일\n프로그래밍");
                                mBinding.time74.setText("송재철\n203");
                            }

                            //3B
                            if(subject.equals("네트워크보안(B)")) {
                                mBinding.time14.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time24.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time34.setBackgroundResource(R.drawable.b_outside);
                                mBinding.time14.setText("네트워크\n보안");
                                mBinding.time24.setText("이보영\n206");
                            }
                            if(subject.equals("AI와영상처리(B)")) {
                                mBinding.time21.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time31.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time41.setBackgroundResource(R.drawable.g_outside);
                                mBinding.time21.setText("AI와\n영상처리");
                                mBinding.time31.setText("황선철\n302");
                            }
                            if(subject.equals("네트워크2(B)")) {
                                mBinding.time62.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time72.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time82.setBackgroundResource(R.drawable.a_outside);
                                mBinding.time62.setText("네트워크2");
                                mBinding.time72.setText("김행민\n205");
                            }
                            if(subject.equals("네트워크프로그래밍(B)")) {
                                mBinding.time64.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time74.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time84.setBackgroundResource(R.drawable.e_outside);
                                mBinding.time64.setText("네트워크\n프로그래밍");
                                mBinding.time74.setText("한성일\n206");
                            }
                            if(subject.equals("캡스톤디자인1(B)")) {
                                mBinding.time63.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time73.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time83.setBackgroundResource(R.drawable.d_outside);
                                mBinding.time63.setText("캡스톤\n디자인1");
                                mBinding.time73.setText("나경필\n204");
                            }
                            if(subject.equals("사물인터넷(B)")) {
                                mBinding.time23.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time33.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time43.setBackgroundResource(R.drawable.i_outside);
                                mBinding.time23.setText("사물\n인터넷");
                                mBinding.time33.setText("조돈제\n302");
                            }
                            if(subject.equals("알고리즘(B)")) {
                                mBinding.time22.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time32.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time42.setBackgroundResource(R.drawable.h_outside);
                                mBinding.time22.setText("알고리즘");
                                mBinding.time32.setText("전광일\n302");
                            }
                            if(subject.equals("실전취업(B)")) {
                                mBinding.time13.setBackgroundResource(R.drawable.c_outside);
                                mBinding.time13.setText("실전취업\n송재철\n304");
                            }
                            if(subject.equals("디지털전송시스템(B)")) {
                                mBinding.time65.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time75.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time85.setBackgroundResource(R.drawable.f_outside);
                                mBinding.time65.setText("디지털\n전송\n시스템");
                                mBinding.time75.setText("유봉선\n304");
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

        return mBinding.getRoot();
    }


}
