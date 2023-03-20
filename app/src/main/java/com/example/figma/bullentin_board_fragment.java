package com.example.figma;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class bullentin_board_fragment extends Fragment {
    RecyclerView recyclerView;
    bullentin_board_adpter adpter;

    bullentin_board bullentin_board;
    ArrayList<HashMap<String, String>> fileList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        bullentin_board = (com.example.figma.bullentin_board) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.item_main, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView); //recyclerview 변수

        //Linerlayoutmanger 변수에 Layoutmanager 할당 (VERTICAL은 세로 리스트, HORIZONTAL은 가로 리스트)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerview 어댑터 설정
        adpter = new bullentin_board_adpter(getContext());
        recyclerView.setAdapter(adpter);

        int permissionCheck1 = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);//스토리지 읽기
        if (permissionCheck1 == PackageManager.PERMISSION_GRANTED) {

            fileList = bullentin_board.getFilelist(Environment.getExternalStorageDirectory().getAbsolutePath());

            for (int i=0; i < fileList.size(); i++){
                adpter.addItem(fileList.get(i));
                adpter.notifyDataSetChanged();
            }
        }

        return rootView;
    }
}
