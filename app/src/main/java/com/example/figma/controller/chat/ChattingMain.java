package com.example.figma.controller.chat;

import androidx.appcompat.app.AppCompatActivity;

public class ChattingMain extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private RecyclerView.LayoutManager layoutManager;
//    private List<Chat> chatList;
//    private String nickname = "익명1";
//
//    private EditText chatText;
//    private ImageButton sendButton;
//
//    private DatabaseReference myRef;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ChattingMain);
//
//        chatText = findViewById(R.id.chatText);
//        sendButton = findViewById(R.id.sendButton);
//
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //입력창에 메시지를 입력 후 버튼클릭했을 때
//                String msg = chatText.getText().toString();
//
//                if(msg != null){
//                    Chat chat = new Chat();
//                    chat.setName(nickname);
//                    chat.setMsg(msg);
//
//                    //메시지를 파이어베이스에 보냄.
//                    myRef.push().setValue(chat);
//
//                    chatText.setText("");
//                }
//
//            }
//        });
//        //리사이클러뷰에 어댑터 적용
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        chatList = new ArrayList<>();
//        adapter = new ChatAdapter(chatList, nickname);
//        recyclerView.setAdapter(adapter);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//
//        //데이터들을 추가, 변경, 제거, 이동, 취소
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                //어댑터에 DTO추가
//                Chat chat = snapshot.getValue(Chat.class);
//                ((ChatAdapter)adapter).addChat(chat);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
//

}

        // 뒤로가기 버튼
        //ImageButton backButton = findViewById(R.id.backButton);
        //backButton.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), ChatPerson.class);
                //startActivity(intent);
            //}
        //});


