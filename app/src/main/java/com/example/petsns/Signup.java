package com.example.petsns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    private SigupViewModel viewModel;
    String mail;
    String id;
    String pass;
    String name;
    String editTextTextPassword6;

    @Override
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

        Button btnNext = findViewById(R.id.button6);
        EditText textId = findViewById(R.id.txtId);
        EditText textName = findViewById(R.id.txtName);
        EditText textPass = findViewById(R.id.txtPass);
        EditText editTextTextPassword6 = findViewById(R.id.editTextTextPassword6);
        EditText textMail = findViewById(R.id.txtMail);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = textName.getText().toString();
                mail = textMail.getText().toString();
                id = textId.getText().toString();
                pass = textPass.getText().toString();

                if (pass.equals(editTextTextPassword6.getText().toString())) {
                    // パスワードが一致する場合の処理
                    viewModel.setUserName(name);
                    viewModel.setUserId(id);
                    viewModel.setEmail(mail);
                    viewModel.setPassword(pass);

                    Context context = v.getContext();
                    Intent intent = new Intent(context, Signup2.class);
                    startActivity(intent);
                } else {
                    // パスワードが一致しない場合の処理
                    // ユーザーにエラーメッセージを表示するなどの適切な対処を行う
                    showError("パスワードが間違っています");
                }
            }
        });

        Button btnBack = findViewById(R.id.button5);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showError(String errorMessage) {
        TextView errorTextView = findViewById(R.id.errorMessage);
        errorTextView.setText(errorMessage);
        errorTextView.setVisibility(View.VISIBLE);
    }
}

