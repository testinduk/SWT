package com.example.figma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frage_Class1 extends Fragment {
    private View view;


    public static Frage_Class1 newInstance() {
        Frage_Class1 frage_class1 = new Frage_Class1();
        return frage_class1;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timetable_detail1a, container, false);
        return view;

    }
}
