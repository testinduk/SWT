package com.example.figma.controller.myinformation;


import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.figma.R;
import com.example.figma.controller.mypage.Mypage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class MyInfDetails extends Activity {
    static final int REQUEST_CODE=0;
    private ImageView changeImage;
    private ImageButton SelectImageButton;
    private Button CompleteChangeButton;
    private EditText password1;
    private  FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inf_details);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        changeImage = findViewById(R.id.changeImage);
        SelectImageButton = findViewById(R.id.SelectImageButton);
        CompleteChangeButton = findViewById(R.id.CompleteChangeButton);
        password1 = findViewById(R.id.password1);
        mAuth = FirebaseAuth.getInstance();


        SelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // 뒤로가기 버튼
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mypage.class);
                startActivity(intent);
            }
        });

        // warningButton
        Button warningButton = findViewById(R.id.remove);
        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WarningMessage.class);
                startActivity(intent);
            }
        });

        CompleteChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = password1.getText().toString();
                currentUser.updatePassword(newPassword)
                        .addOnCompleteListener(MyInfDetails.this,new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    databaseRef.child("SignUp").child(uid).child("password").setValue(newPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Password updated successfully
                                                        Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        // Error updating password in Firebase Realtime Database
                                                        Toast.makeText(getApplicationContext(), "Error updating password in Firebase Realtime Database", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    Intent intent = new Intent(getApplicationContext(), Mypage.class);
                                    startActivity(intent);
                                } else {
                                    // Error updating password in Firebase Authentication
                                    Toast.makeText(getApplicationContext(), "Error updating password in Firebase Authentication", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    changeImage.setImageURI(uri);
                }
                break;
        }

    }
}