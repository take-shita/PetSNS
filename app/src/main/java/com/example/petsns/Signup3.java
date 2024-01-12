package com.example.petsns;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;

import android.widget.TextView;


public class Signup3 extends AppCompatActivity {
    private SigupViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        Button btnSb=findViewById(R.id.btnSubmit);

        TextView txtId=findViewById(R.id.textId);
        TextView txtName=findViewById(R.id.textName);
        TextView txtMail=findViewById(R.id.textMail);
        TextView txtFv=findViewById(R.id.textFavorite);
        MyApplication myApplication = (MyApplication) getApplication();

        // MyApplicationの初期化が行われていることを確認する
        if (myApplication != null) {
            viewModel = myApplication.getSharedViewModel();
        } else {
            // エラーハンドリング
        }



        btnSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}