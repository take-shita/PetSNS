package com.example.petsns.ui.login_signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private SigupViewModel viewModel;
    String mail;
    String id;
    String pass;
    String name;
    String editTextTextPassword6;
    private FirebaseFirestore db;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;


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
                db = FirebaseFirestore.getInstance();
                if(!id.equals("")){
                    DocumentReference docRef = db.collection("users").document(id);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    showError("そのIDは既に使用されています");
                                } else {
                                    matcher = pattern.matcher(mail);
                                    if (matcher.matches()) {
                                        CollectionReference collectionRefId = db.collection("users");
                                        collectionRefId.whereEqualTo("mail", mail)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(Task<QuerySnapshot> task1) {
                                                        if (task1.isSuccessful()) {
                                                            QuerySnapshot querySnapshot = task1.getResult();

                                                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                                                // ドキュメントが見つかった場合の処理
                                                                showError("そのメールアドレスは既に使用されています");

                                                            } else {
                                                                Log.d("?",mail);
                                                                if (!name.equals("") && !mail.equals("") && !id.equals("") && !pass.equals("")) {
                                                                    if (pass.equals(editTextTextPassword6.getText().toString())) {
                                                                        // パスワードが一致する場合の処理
                                                                        if(pass.length()>8){
                                                                            viewModel.setUserName(name);
                                                                            viewModel.setUserId(id);
                                                                            viewModel.setEmail(mail);
                                                                            viewModel.setPassword(pass);

                                                                            Context context = v.getContext();
                                                                            Intent intent = new Intent(context, Signup2.class);
                                                                            startActivity(intent);
                                                                        }else{
                                                                            showError("パスワードは8文字以上でお願いします");
                                                                        }

                                                                    } else {
                                                                        showError("パスワードが一致しません");
                                                                    }
                                                                } else {
                                                                    showError("未入力の箇所があります");
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                    } else {
                                        showError("正しい形でメールアドレスを入力してください");
                                    }

                                }
                            }
                        }
                    });
                }

            }
        });



//                collectionRefId.whereEqualTo("documentIdField", id)
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(Task<QuerySnapshot> task1) {
//                                if (task1.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document1 : task1.getResult()) {
//                                        Log.d("?","1");
//                                        // ドキュメントが見つかった場合、IDを取得
//                                        showError("そのIDは既に使用されています");
//                                        checkID=true;
//                                    }
//                                    if(!checkID){
//                                        if(!name.equals("") && !mail.equals("") && !id.equals("") && !pass.equals("")){
//                                            if (pass.equals(editTextTextPassword6.getText().toString())) {
//                                                // パスワードが一致する場合の処理
//                                                viewModel.setUserName(name);
//                                                viewModel.setUserId(id);
//                                                viewModel.setEmail(mail);
//                                                viewModel.setPassword(pass);
//
//                                                Context context = v.getContext();
//                                                Intent intent = new Intent(context, Signup2.class);
//                                                startActivity(intent);
//                                            } else {
//                                                showError("パスワードが間違っています");
//                                            }
//                                        }else {
//                                            showError("未入力の箇所があります");
//                                        }
//                                    }else {
//                                        checkID=false;
//                                    }
//                                }
//                            }
//                        });
//                }


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

