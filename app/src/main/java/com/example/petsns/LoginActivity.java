package com.example.petsns;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.widget.EditText;
import android.widget.Button;

import android.content.Intent;

public class LoginActivity  extends AppCompatActivity {
    String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // ログイン済み
            Context context = this;

            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);

        } else {
            // 未ログイン
            setContentView(R.layout.activity_login);
            EditText textID = findViewById(R.id.loginID);
            EditText textPass = findViewById(R.id.loginPass);
            Button btnLogin = findViewById(R.id.btnLogin);
            Button btnSignUp = findViewById(R.id.btnSignUp);

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
                    id = textID.getText().toString();
                    String pass = textPass.getText().toString();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference usersCollection = db.collection("users");

                    String targetFieldValue = id; // 検索対象のフィールド値

                    usersCollection.whereEqualTo("id", targetFieldValue)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // sample フィールドが targetFieldValue と一致するドキュメントを取得
                                        String mail = document.getString("mail");
                                        // template フィールドの値を取得
                                        if (mail != null) {
                                            id = mail;
                                        } else {
                                            // template フィールドが存在しない場合の処理
                                        }
                                    }
                                } else {
                                    Exception exception = task.getException();
                                    if (exception != null) {
                                        Log.e("aaaa", "データの取得に失敗しました", exception);
                                    }
                                }
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(id, pass)
                                        .addOnCompleteListener(signInTask  -> {
                                            if (signInTask .isSuccessful()) {
                                                // ログイン成功
                                                FirebaseUser user = signInTask .getResult().getUser();
                                                String userId = user.getUid();
                                                // ユーザー情報を利用してUIを更新するなどの処理を行う
                                                Context context = v.getContext();

                                                Intent intent = new Intent(context, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // ログイン失敗
                                                textID.setText(id);
                                            }
                                        });

                            });

                }
            });
        }
    }
}



