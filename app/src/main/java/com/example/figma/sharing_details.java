package com.example.figma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class sharing_details extends Activity {
    private TextView tv_username;
    private TextView tv_content;
    private TextView tv_title;
    private TextView textView3;
    private ImageView photo_image, edit_button;
    private ImageButton delete_button;
    private ImageButton imageButton2;
    private RecyclerView recyclerView;
    private EditText EditText2;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<sharing_com_DB> arrayList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_details);

       
        // 수정 버튼
        tv_username = findViewById(R.id.textView2);
        tv_title = findViewById(R.id.textView1);
        tv_content = findViewById(R.id.textView4);
        edit_button = findViewById(R.id.btn_sha_amend);
        delete_button = findViewById(R.id.Button3);
        photo_image = findViewById(R.id.photo_image);
        textView3 = findViewById(R.id.textView3);
        imageButton2 = findViewById(R.id.ImageButton2); //댓글 쓰기 버튼
        EditText2 = findViewById(R.id.EditText2); // 댓글창
        recyclerView = findViewById(R.id.recy); //리싸이클러 아이디 연결
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        Intent second_intent = getIntent();

        String shar_username = second_intent.getStringExtra("username");
        String shar_title = second_intent.getStringExtra("title");
        String shar_content = second_intent.getStringExtra("content");
        String shar_idToken = second_intent.getStringExtra("idToken");
        String shar_key = second_intent.getStringExtra("key");
        String sharing_image = second_intent.getStringExtra("image");
        String sharing_time = second_intent.getStringExtra("time");
//        Log.e("image",sharing_image);


        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //현재 사용자의 파이어베이스 정보 불러오기
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseFirestore fs_db = FirebaseFirestore.getInstance();
        String sharing_comment_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정

        imageButton2.setOnClickListener(new View.OnClickListener() {
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


                            Map<String, Object> sharing_comment = new HashMap<>();
                            sharing_comment.put("name", username);
                            sharing_comment.put("studentNumber", studentNumber);
                            sharing_comment.put("content", comment_content);
                            sharing_comment.put("time", current_time);
                            fs_db.collection(shar_key).document(sharing_comment_UUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            fs_db.collection(shar_key).document(sharing_comment_UUID).update(sharing_comment);
                                        } else {
                                            fs_db.collection(shar_key).document(sharing_comment_UUID).set(sharing_comment);

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

        fs_db.collection(shar_key).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                arrayList.clear();
                for (QueryDocumentSnapshot document : snapshots) {
                    sharing_com_DB user = document.toObject(sharing_com_DB.class);
                    arrayList.add(user);
                }
                // -----시간 정렬 (역순)-----
                Collections.reverse(arrayList);

                adapter.notifyDataSetChanged();
            }
        });


        adapter = new sharing_com_adapter(arrayList, this);
        recyclerView.setAdapter(adapter);








        tv_username.setText(shar_username);
        tv_content.setText(shar_content);
        tv_title.setText(shar_title);
        Glide.with(this)
                .load(sharing_image)
                .into(photo_image);
        textView3.setText(sharing_time);

        if (uid.equals(shar_idToken)) {
            edit_button.setEnabled(true);
            delete_button.setEnabled(true);
            //수정 버튼
            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shar_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), sharing_edit.class);
                        intent.putExtra("id", shar_idToken);
                        intent.putExtra("title",shar_title);
                        intent.putExtra("content",shar_content);
                        intent.putExtra("key",shar_key);
                        intent.putExtra("image",sharing_image);
                        intent.putExtra("username", shar_username);
                        Log.i("id", shar_idToken);
                        Log.i("title",shar_title);
                        Log.i("content",shar_content);
                        Log.i("key",shar_key);
                        Log.i("image",sharing_image);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            //글 삭제하기.
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shar_idToken != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(sharing_details.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                                startActivity(intent);
                                ref.child("sharing Board").child(shar_key).removeValue();
                                Toast.makeText(sharing_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();
                    }
                }
            });

        } else {
            edit_button.setEnabled(false);
            delete_button.setEnabled(false);
        }

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sharing_board.class);
                startActivity(intent);
            }
        });
    }
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
