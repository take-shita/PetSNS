package com.example.petsns;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;

import android.widget.TextView;

import com.example.petsns.ui.setting.TagViewModel;


public class Signup3 extends AppCompatActivity {
    private SigupViewModel viewModel;
    private TagViewModel tagViewModel;

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
            tagViewModel=myApplication.getSharedTagViewModel();
        } else {
            // エラーハンドリング
        }

        txtId.setText(viewModel.getUserId());
        txtName.setText(viewModel.getUserName());
        txtMail.setText(viewModel.getEmail());
        txtFv.setText(tagViewModel.getMommalian());

        btnSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}