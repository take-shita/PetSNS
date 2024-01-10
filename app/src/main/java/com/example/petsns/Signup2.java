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
public class Signup2 extends AppCompatActivity{
    private SigupViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup2);

        Button btnNext=findViewById(R.id.btnNext);
        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, Signup3.class);
                startActivity(intent);
            }
        });
    }
}
