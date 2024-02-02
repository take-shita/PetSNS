package com.example.petsns;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.widget.EditText;
import android.widget.Button;

import android.content.Intent;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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
            Button test = findViewById(R.id.buttonSample);

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
                                        .addOnCompleteListener(signInTask -> {
                                            if (signInTask.isSuccessful()) {
                                                // ログイン成功
                                                FirebaseUser user = signInTask.getResult().getUser();
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

            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // コピー元のドキュメントの参照
                    String sourceCollection = "users";
                    String sourceDocumentId = "mS8yhvIVEbVysAs4i1jDGAx1coX2";

                    DocumentReference sourceDocumentRef = db.collection(sourceCollection).document(sourceDocumentId);


                    // コピー先のドキュメントIDを生成（新しいIDを指定することもできます）

                    sourceDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                // コピー元のドキュメントを取得
                                DocumentSnapshot sourceDocumentSnapshot = task.getResult();


                                if (sourceDocumentSnapshot.exists()) {
                                    // コピー先のコレクションとドキュメントを指定してデータをセット
                                    CollectionReference destinationCollection = db.collection(sourceCollection);
                                    String newDocumentId=sourceDocumentSnapshot.getString("id");
                                    DocumentReference destinationDocumentRef = destinationCollection.document(newDocumentId);

                                    Map<String, Object> dataToCopy = sourceDocumentSnapshot.getData();
                                    if (dataToCopy != null) {
                                        destinationDocumentRef.set(dataToCopy)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(Task<Void> copyTask) {
                                                        if (copyTask.isSuccessful()) {
                                                            // コピーが成功した場合の処理
                                                            System.out.println("Document copied successfully!");
                                                        } else {
                                                            // コピーが失敗した場合の処理
                                                            System.err.println("Error copying document: " + copyTask.getException().getMessage());
                                                        }
                                                    }
                                                });
                                    } else {
                                        System.err.println("No data to copy from the source document.");
                                    }
                                } else {
                                    System.err.println("Source document does not exist.");
                                }
                            }
                        }
                    });
                }
            });
        }
    }
}



