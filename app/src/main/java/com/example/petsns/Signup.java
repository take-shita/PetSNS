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
    MyApplication myApplication = (MyApplication) getApplication();
    private SigupViewModel viewModel = myApplication.getSharedViewModel();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        Button btnNext=findViewById(R.id.btnNext);
        EditText textID=findViewById(R.id.textID);
        EditText textPass=findViewById(R.id.textPass);
        EditText textMail=findViewById(R.id.textMail);
//        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);

        String id=textID.getText().toString();
        String pass=textPass.getText().toString();
        String mail=textMail.getText().toString();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
