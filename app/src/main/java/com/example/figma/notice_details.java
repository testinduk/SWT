package com.example.figma;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class notice_details extends Activity {
    private ImageButton delete_button, edit_button, backButton, imageButton2;
    private TextView tv_content, tv_title, tv_username, tv_time;
    private ImageView photo_image;
    private RecyclerView notice_RecyclerView;
    private EditText EditText2;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
//    private ArrayList<notice_comment_DB> arrayList;
//    FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    String uid = mAuth.getCurrentUser().getUid();
//


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_details);

        photo_image = findViewById(R.id.photo_image);    // 이미지뷰
        edit_button = findViewById(R.id.btn_notice_amend); //수정 버튼
        tv_content = findViewById(R.id.textView4); //내용
        tv_title = findViewById(R.id.textView1); //제목
        delete_button = findViewById(R.id.Button3);   // 삭제 버튼
        backButton = findViewById(R.id.backButton); // back 버튼
        tv_username =findViewById(R.id.textView2);   // 글쓴이
        tv_time = findViewById(R.id.textView3); // 날짜
        imageButton2 = findViewById(R.id.ImageButton2); //댓글 쓰기 버튼
        EditText2 = findViewById(R.id.EditText2); // 댓글창


        Intent second_intent = getIntent();

        String notice_username = second_intent.getStringExtra("username");
        String notice_title = second_intent.getStringExtra("title");
        String notice_content = second_intent.getStringExtra("content");
        String notice_idToken = second_intent.getStringExtra("idToken");
        String notice_key = second_intent.getStringExtra("key");
        String notice_time = second_intent.getStringExtra("time");
        String notice_image = second_intent.getStringExtra("image");

        //db레퍼런스
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseFirestore fs_db = FirebaseFirestore.getInstance();


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
                            String notice_comment_UUID = UUID.randomUUID().toString();//랜덤함수로 이미지 이름 지정



                            Map<String, Object> notice_comment = new HashMap<>();
                            notice_comment.put("name", username);
                            notice_comment.put("studentNumber", studentNumber);
                            notice_comment.put("content", comment_content);
                            notice_comment.put("time", current_time);
                            fs_db.collection(notice_key).document(notice_comment_UUID).set(notice_comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("log", "성공입니다");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("log", "실패");
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




        tv_content.setText(notice_content);
        tv_title.setText(notice_title);
        tv_username.setText(notice_username);
        Glide.with(this)
                .load(notice_image)
                .into(photo_image);
        tv_time.setText(notice_time);


        if(uid.equals(notice_idToken)) {
            delete_button.setEnabled(true);
            edit_button.setEnabled(true);

            edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {
                        Intent intent = new Intent(getApplicationContext(), notice_edit.class);
                        intent.putExtra("id", notice_idToken);
                        intent.putExtra("title", notice_title);
                        intent.putExtra("content", notice_content);
                        intent.putExtra("key", notice_key);
                        intent.putExtra("image", notice_image);
                        Log.i("id", notice_idToken);
                        Log.i("title", notice_title);
                        Log.i("content", notice_content);
                        Log.i("key", notice_key);
                        Log.i("image", notice_image);
                        startActivity(intent);
                    } else {
                        Log.i("id", "shar_idToken is null");
                    }
                }
            });
            // trash button
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notice_idToken != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(notice_details.this);
                        builder.setTitle("경고메시지");
                        builder.setMessage("정말로 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), notice_list.class);
                                startActivity(intent);
                                ref.child("notice Board").child(notice_key).removeValue();
                                Toast.makeText(notice_details.this, "관련 내용이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("취소",null);
                        builder.create().show();

                    }
                }
            });
        }else{
            edit_button.setEnabled(false);
            delete_button.setEnabled(false);
        }
                // 뒤로가기 버튼
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notice_list.class);
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

