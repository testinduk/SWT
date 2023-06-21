package com.example.figma.controller.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.figma.databinding.ChatMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

public class ChatMain extends AppCompatActivity {
    private ChatMainBinding mBinding;
    private FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance(); //FirebaseAuth 선언
        mFireStore = FirebaseFirestore.getInstance(); // 파이어베이스 초기화
        String senderUUID = mAuth.getCurrentUser().getUid(); // 보내는 사람의 UUID


        mBinding = ChatMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        Intent Chat_intent = getIntent();
        String receiverUUID = Chat_intent.getStringExtra("receiverUUID");


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
                                        Log.d("fuck", "개같이 멸망... 채팅하지마!!");
                                    });
                        } else {
                            Log.d("Error","ChatRoomKey가 없다.");
                        }
                    }
                });

            }
        });


    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

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
}
