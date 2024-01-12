package com.example.petsns;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.EditText;
import android.widget.Button;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
public class Signup extends AppCompatActivity {
    private SigupViewModel viewModel;
    String mail;
    String id;
    String pass;
    String name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        MyApplication myApplication = (MyApplication) getApplication();

        // MyApplicationの初期化が行われていることを確認する
        if (myApplication != null) {
            viewModel = myApplication.getSharedViewModel();
        } else {
            // エラーハンドリング
        }


        Button btnNext=findViewById(R.id.button6);
        EditText textId=findViewById(R.id.txtId);
        EditText textName=findViewById(R.id.txtName);
        EditText textPass=findViewById(R.id.txtPass);
        EditText textMail=findViewById(R.id.txtMail);
//        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=textName.getText().toString();
                mail=textMail.getText().toString();
                id=textId.getText().toString();
                pass=textPass.getText().toString();
                viewModel.setUserName(name);
                viewModel.setUserId(id);
                viewModel.setEmail(mail);
                viewModel.setPassword(pass);


                Context context = v.getContext();

                Intent intent = new Intent(context, Signup2.class);
                startActivity(intent);

            }
        });
    }
}
