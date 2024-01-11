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


        Button btnNext=findViewById(R.id.btnNext);
        EditText textID=findViewById(R.id.textID);
        EditText textPass=findViewById(R.id.textPass);
        EditText textMail=findViewById(R.id.textMail);
//        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=textMail.getText().toString();
                id=textID.getText().toString();
                pass=textPass.getText().toString();
                viewModel.setUserName(id);
                viewModel.setEmail(mail);
                viewModel.setPassword(pass);

                Context context = v.getContext();

                Intent intent = new Intent(context, Signup2.class);
                startActivity(intent);

            }
        });
    }
}
