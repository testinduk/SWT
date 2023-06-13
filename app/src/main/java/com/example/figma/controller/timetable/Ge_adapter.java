package com.example.figma.controller.timetable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figma.R;
import com.example.figma.model.Board;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ge_adapter extends RecyclerView.Adapter<Ge_adapter.ViewHolder> {
    private List<Board> gefieldList;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private int buttonClickCount = 1;
    private List<String> fieldValues = new ArrayList<>();




    public Ge_adapter(List<Board> gefieldList) {
        this.gefieldList = gefieldList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Board field = gefieldList.get(position);
        // Bind the data to the ViewHolder
        holder.bind(field);
    }

    @Override
    public int getItemCount() {
        return gefieldList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private Button fieldName;
        private Button fieldValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.time_rcy_detail);
            fieldValue = itemView.findViewById(R.id.time_rcy_detail2);
        }

        public void bind(Board field) {
            String sub_name = field.getFieldName();
            String sub_value = String.valueOf(field.getFieldValue());
            fieldName.setText(sub_name);
            fieldValue.setText(sub_value);
            fieldName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = FirebaseFirestore.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    String uid = mAuth.getCurrentUser().getUid();

                    List<String> update_list = new ArrayList<>();
                    DocumentReference documentRef = db.collection("Time_table").document(uid);

                    documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                // 이미 데이터가 있는 경우
                                if (document.exists()) {
                                    Map<String, Object> update = document.getData();
                                    for (Map.Entry<String, Object> entry : update.entrySet()) {


                                        String check_list = entry.getKey();
                                        update_list.add(check_list);

                                    }
                                    buttonClickCount = update_list.size();
                                    String fieldName = String.valueOf(buttonClickCount +1);

                                    Map<String, Object> update_data = document.getData();
                                    update_data.put(fieldName, sub_name);
                                    documentRef.set(update_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.e("log", "데이터 입력 성공");
                                        }
                                    });

                                } else {
                                    // 데이터가 없는 경우(새로 작성)
                                    String fieldName = String.valueOf(buttonClickCount);

                                    Map<String, Object> create_data = document.getData();
                                    create_data.put(fieldName, sub_name);
                                    documentRef.set(create_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            buttonClickCount++;
                                            Log.i("데이터 새로 생성 후 +1", String.valueOf(buttonClickCount));

                                        }
                                    });
                                }

                            }
                        }
                    });
                }
            });
        }
    }
}
