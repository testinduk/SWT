package com.example.figma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bullentin_board extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;  //어댑터
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<bullentin_DB> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    ArrayList<item_bullentin_board> search_list = new ArrayList<>();  // 검색시 같은 이름이 있는 아이템이 담길 리스트
    ArrayList<item_bullentin_board> original_list = new ArrayList<>(); // recyclerView에 추가할 아이템 리스트

    //어댑터
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullentin_board);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("bullentin Board");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    bullentin_DB user = snapshot.getValue(bullentin_DB.class);
                    arrayList.add(user);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", String.valueOf(databaseError.toException()));
            }
        });

        adapter = new bullentin_board_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);


        // 글쓰기 버튼
        Button writingButton = findViewById(R.id.writingButton);
        writingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bullentin_board_writing.class);
                startActivity(intent);
            }
        });

        //채팅 버튼
        ImageButton chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chat_person.class);
                startActivity(intent);
            }
        });

        // 나눔 버튼
        ImageButton sharingButton = findViewById(R.id.sharingButton);
        sharingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });

        // 홈 버튼
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_home.class);
                startActivity(intent);
            }
        });

        // 게시판 버튼
        ImageButton boardButton = findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
                startActivity(intent);
            }
        });

        // 마이페이지 버튼
        ImageButton mypageButton = findViewById(R.id.mypageButton);
        mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_home.class);
                startActivity(intent);
            }
        });


        editText = findViewById(R.id.editText);

        //editText 리스트 작성
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    adapter.setItems(original_list);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < original_list.size(); a++) {
                        if (original_list.get(a).Name.toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(original_list.get(a));
                        }
                        adapter.setItems(search_list);
                    }
                }
            }
        });

        // 리사이클러뷰, 어댑터 연결
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new bullentin_board_adapter(original_list);
        recyclerView.setAdapter(adapter);
    }
}


//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<bullentin_DB> arrayList;
//    private FirebaseDatabase database;
//    private DatabaseReference databaseReference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bullentin_board);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        arrayList = new ArrayList<>();
//
//        database = FirebaseDatabase.getInstance();
//
//        databaseReference = database.getReference("bullentin Board");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                arrayList.clear();
//                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
//                    String uid = snapshot.getKey();
//                    bullentin_DB user = snapshot.getValue(bullentin_DB.class);
//                    arrayList.add(user);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//    //글쓰기 버튼
//    Button writingButton = findViewById(R.id.writingButton);
//        writingButton.setOnClickListener(new View.OnClickListener()
//
//    {
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), bullentin_board_writing.class);
//        startActivity(intent);
//    }
//    });
//
//    //채팅 버튼
//    ImageButton chatButton = findViewById(R.id.chatButton);
//        chatButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), chat_person.class);
//        startActivity(intent);
//    }
//    });
//
//    // 나눔 버튼
//    ImageButton sharingButton = findViewById(R.id.sharingButton);
//        sharingButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), sharing_board.class);
//        startActivity(intent);
//    }
//    });
//
//    // 홈 버튼
//    ImageButton homeButton = findViewById(R.id.homeButton);
//        homeButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), main_home.class);
//        startActivity(intent);
//    }
//    });
//
//    // 게시판 버튼
//    ImageButton boardButton = findViewById(R.id.boardButton);
//        boardButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), bullentin_board.class);
//        startActivity(intent);
//    }
//    });
//
//    // 마이페이지 버튼
//    ImageButton mypageButton = findViewById(R.id.mypageButton);
//        mypageButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), mypage.class);
//        startActivity(intent);
//    }
//    });
//
//    // 뒤로가기 버튼
//    ImageButton backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener()
//
//    {
//
//        @Override
//        public void onClick (View view){
//        Intent intent = new Intent(getApplicationContext(), main_home.class);
//        startActivity(intent);
//    }
//    });
//}


//
//    // 검색시 같은 이름이 있는 아이템이 담길 리스트
//    ArrayList<String> search_list = new ArrayList<>();
//    // recyclerView에 추가할 아이템 리스트
//    ArrayList<String> original_list = new ArrayList<>();
//    // 어댑터
//    bullentin_board_adpter adapter;
//    EditText editText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.bullentin_board);
//
//        // 리스트에 아이템 추가
//        for(int i=0; i<50; i++){
//            if(i % 2 == 0){
//                original_list.add(new Item("ITEM " + i,"foreground image " + i,R.drawable.ic_launcher_foreground));
//            }
//            else{
//                original_list.add(new card("ITEM " + i,"background image " + i,R.drawable.ic_launcher_background));
//            }
//        }
//
//        editText = findViewById(R.id.editText);
//
//        // editText 리스터 작성
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String searchText = editText.getText().toString();
//                search_list.clear();
//
//                if(searchText.equals("")){
//                    adapter.setItems(original_list);
//                }
//                else {
//                    // 검색 단어를 포함하는지 확인
//                    for (int a = 0; a < original_list.size(); a++) {
//                        if (original_list.get(a).title.toLowerCase().contains(searchText.toLowerCase())) {
//                            search_list.add(original_list.get(a));
//                        }
//                        adapter.setItems(search_list);
//                    }
//                }
//            }
//        });
//
//        // 리사이클러뷰, 어댑터 연결
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new bullentin_board_adpter(original_list);
//        recyclerView.setAdapter(adapter);


