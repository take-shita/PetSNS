package com.example.petsns;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.EditText;
import android.widget.Button;

import android.content.Intent;

public class LoginActivity  extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // ログイン済み

        } else {
            // 未ログイン
            setContentView(R.layout.activity_login);
        }

        EditText textID=findViewById(R.id.loginID);
        EditText textPass=findViewById(R.id.loginPass);
        Button btnLogin=findViewById(R.id.btnLogin);
        Button btnSignUp=findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, Signup.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=textID.getText().toString();
                String pass=textPass.getText().toString();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(id, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // ログイン成功
                                FirebaseUser user = task.getResult().getUser();
                                String userId = user.getUid();
                                // ユーザー情報を利用してUIを更新するなどの処理を行う
                                textID.setText("a????");
                            } else {
                                // ログイン失敗
                                textID.setText("a?");
                            }
                        });

//                FirebaseAuth.getInstance().createUserWithEmailAndPassword("user@example.com", "password")
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                // ユーザー作成成功
//                                FirebaseUser user = task.getResult().getUser();
//                                // ユーザー情報を利用してUIを更新するなどの処理を行う
//                            } else {
//                                // ユーザー作成失敗
//                            }
//                        });
            }


        });
    }

}
