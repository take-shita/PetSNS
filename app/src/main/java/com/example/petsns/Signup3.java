package com.example.petsns;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.widget.EditText;
import android.widget.Button;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class Signup3 extends AppCompatActivity{
    private SigupViewModel viewModel;
    private FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        db = FirebaseFirestore.getInstance();
        Button btnFinish=findViewById(R.id.btnFinish);
        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(false){

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(viewModel.getEmail(), viewModel.getPassword())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // ユーザー作成成功
                                    FirebaseUser user = task.getResult().getUser();
                                    String uid = user.getUid();//id取得




                                    DocumentReference documentRef = db.collection("users").document(uid);
                                    // アカウント情報のデータ
                                    Map<String, Object> accountData = new HashMap<>();
                                    accountData.put("account_name", "example_user");
                                    accountData.put("password", "password123");
                                    documentRef.set(accountData)
                                            .addOnSuccessListener(aVoid -> {

                                            })
                                            .addOnFailureListener(e -> {

                                            });



                                } else {
                                    // ユーザー作成失敗
                                }
                            });


                }
            }
        });
    }
}
