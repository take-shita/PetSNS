package com.example.petsns;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petsns.ui.setting.TagViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.Button;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Signup4 extends AppCompatActivity{
    private FirebaseFirestore db;
    private SigupViewModel viewModel;
    private TagViewModel tagViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup4);


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



            }
        });
    }
}
