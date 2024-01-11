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
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Signup3 extends AppCompatActivity{
    private FirebaseFirestore db;
    private SigupViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);


        MyApplication myApplication = (MyApplication) getApplication();

        // MyApplicationの初期化が行われていることを確認する
        if (myApplication != null) {
            viewModel = myApplication.getSharedViewModel();
        } else {
            // エラーハンドリング
        }


        db = FirebaseFirestore.getInstance();
        Button btnFinish=findViewById(R.id.btnFinish);
        TextView text=findViewById(R.id.textsample);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text.setText(viewModel.getEmail());
                if(true){

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(viewModel.getEmail(), viewModel.getPassword())
                            .addOnCompleteListener(task -> {

//                                text.setText("kyaaaaaaaaaa");

                                if (task.isSuccessful()) {
                                    // ユーザー作成成功
                                    FirebaseUser user = task.getResult().getUser();
                                    String uid = user.getUid();//id取得




                                    DocumentReference documentRef = db.collection("users").document(uid);
                                    // アカウント情報のデータ
                                    Map<String, Object> accountData = new HashMap<>();
                                    accountData.put("id",viewModel.getUserName());
                                    accountData.put("mail",viewModel.getEmail());
                                    accountData.put("password", viewModel.getPassword());
                                    documentRef.set(accountData)
                                            .addOnSuccessListener(aVoid -> {

                                            })
                                            .addOnFailureListener(e -> {

                                            });



                                } else {
                                    // ユーザー作成失敗
                                    text.setText("nuaaaaaaaaaaaaaaaaa");
                                }
                            });


                }
            }
        });
    }
}
