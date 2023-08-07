package com.swt.figma.controller.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swt.figma.databinding.ChatMainBinding;
import com.swt.figma.model.Board;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

public class ChatMain extends AppCompatActivity {
    private ChatMainBinding mBinding;
    private FirebaseFirestore mFireStore = FirebaseFirestore.getInstance(); // 파이어베이스 초기화
    private RecyclerView mChatRecyclerView;
    private ChatAdapter mChatAdapter;
    private List<Board> mChatList = new ArrayList<>();
    private boolean isFirstLoad = true;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 선언
    private String senderUUID = mAuth.getCurrentUser().getUid(); // 보내는 사람의 UUID


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ChatMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // 상대방 uid, name 받아옴
        Intent Chat_intent = getIntent();
        String receiverUUID = Chat_intent.getStringExtra("receiverUUID");
        String chattingPartner = Chat_intent.getStringExtra("userName");

        // RecyclerView 설정
        mChatRecyclerView = mBinding.chatRecyclerView;
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(mChatList, senderUUID);
        mChatRecyclerView.setAdapter(mChatAdapter);

        mBinding.chattingPartner.setText(chattingPartner);

        String chatRoomKey = getChatRoomKeyFromUUID(senderUUID, receiverUUID);

//
//        // 채팅 불러오기
//        loadMessage(chatRoomKey, receiverUUID);
//        // 불러온 내용 시간 순으로 정렬
//        timeCompare(mChatList);
//        // 어뎁터 내용에 변경사항이 있음을 알림
//        mChatAdapter.notifyDataSetChanged();


        // 새로운 채팅 메시지 있을 경우 그 메시지 불러옴
        mFireStore.collection("chat")
                .document(chatRoomKey)
                .collection(receiverUUID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("chatLog", "receiverUUID 새로운 채팅내용 불러오기 에러");
                            return;
                        }

                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if(dc.getType() == DocumentChange.Type.ADDED) {
                                    Log.e("LogADD", "addSnapshotListener ADDED");
                                    // Data 추가
                                    Board board = dc.getDocument().toObject(Board.class);
                                    if (board != null) {
                                        mChatList.add(board);
                                    }

                                } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                    Log.e("LogMOD", "addSnapshotListener MODIFIED");
                                } else if (dc.getType() == DocumentChange.Type.REMOVED){
                                    Log.e("LogREM", "addSnapshotListener REMOVED");
                                }
                            }
                            sortChatMessages();
                        }
                    }
                });

        mFireStore.collection("chat")
                .document(chatRoomKey)
                .collection(senderUUID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("chatLog", "senderUUID 새로운 채팅내용 불러오기 에러");
                            return;
                        }

                        if (value != null) {
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if(dc.getType() == DocumentChange.Type.ADDED) {
                                    Log.e("senderLogADD", "addSnapshotListener ADDED");

                                    // Data 추가
                                    Board board = dc.getDocument().toObject(Board.class);
                                    if (board != null) {
                                        mChatList.add(board);
                                    }

                                } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                    Log.e("LogMOD", "addSnapshotListener MODIFIED");
                                } else if (dc.getType() == DocumentChange.Type.REMOVED){
                                    Log.e("LogREM", "addSnapshotListener REMOVED");
                                }
                            }

                            sortChatMessages();
                        }
                    }
                });



        mBinding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireStore.collection("signUp").document(senderUUID).get().addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("userName");
                        String messageContent = mBinding.chatText.getText().toString(); //메시지 가져옴.
                        String messageKey = UUID.randomUUID().toString(); //랜덤 생성
                        String current_time = getCurrentTime();
                        String chatRoomKey = getChatRoomKeyFromUUID(senderUUID, receiverUUID);

                        if (chatRoomKey != null) {

                            Map<String, Object> messageData = new HashMap<>();
                            messageData.put("content", messageContent);
                            messageData.put("userName", userName);
                            messageData.put("timestamp", current_time);

                            mFireStore.collection("chat")
                                    .document(chatRoomKey)
                                    .collection(senderUUID)
                                    .document(messageKey)
                                    .set(messageData)
                                    .addOnCompleteListener(aVoid -> {
                                        mBinding.chatText.setText(""); //전송 후 입력 필드 초기화
                                    })
                                    .addOnFailureListener(e -> {
                                    });
                        } else {
                            Log.d("Error","ChatRoomKey가 없다.");
                        }
                    }
                });

            }
        });

        mBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatPerson.class);
                startActivity(intent);
            }
        });
    }



    // 현재 시간 불러오기
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    // 채팅방 키 값 생성
    private String getChatRoomKeyFromUUID(String senderUUID, String receiverUUID){
        if(senderUUID == null || receiverUUID == null){
            return null;
        }
        String chatRoomKey;
        if(senderUUID.compareTo(receiverUUID) < 0){
            chatRoomKey = senderUUID + "_" + receiverUUID;
        } else {
            chatRoomKey = receiverUUID + "_" + senderUUID;
        }
        return chatRoomKey;
    }

    // 시간 비교하기
    private void sortChatMessages() {
        Collections.sort(mChatList, new Comparator<Board>() {
            @Override
            public int compare(Board board1, Board board2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date date1 = dateFormat.parse(board1.getTimestamp());
                    Date date2 = dateFormat.parse(board2.getTimestamp());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        mChatAdapter.notifyDataSetChanged();
    }


}
