package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.petsns.LoginActivity;
import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class emailFragment extends Fragment {

    private EmailViewModel mViewModel;
    private EditText editTextNewEmail;  // EditText を格納する変数
    private String userId;
    private Boolean changeCheck;
    public static emailFragment newInstance() {
        return new emailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EmailViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // FirebaseAuth インスタンスを取得
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // 現在のユーザーを取得
//        FirebaseUser user = mAuth.getCurrentUser();

        // EditText を初期化
        editTextNewEmail = view.findViewById(R.id.editTextTextEmailAddress);

        Button btnChangeEmail = view.findViewById(R.id.button12);
        Button btncan = view.findViewById(R.id.btncan);

        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 前の画面に戻る
                Navigation.findNavController(v).navigateUp();
            }
        });
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEmail = editTextNewEmail.getText().toString();
                if (TextUtils.isEmpty(newEmail)) {
                    // メールアドレスが空の場合、エラーメッセージを表示
                    Toast.makeText(requireContext(), "メールアドレスを入力してください", Toast.LENGTH_SHORT).show();
                    return; // 以降の処理を中止
                }


                FirebaseUser userOld = mAuth.getCurrentUser();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userUid = userOld.getUid();


                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                    CollectionReference collectionRefId = db.collection("userId");
                    collectionRefId.whereEqualTo("uid", userUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task1) {

                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            // ドキュメントが見つかった場合、ID を取得
                                            userId = document1.getId();

                                            db.collection("users") // コレクション名
                                                    .document(userId) // ドキュメント名
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if (documentSnapshot.exists()) {

                                                                String pass = documentSnapshot.getString("password");
                                                                String newEmail = editTextNewEmail.getText().toString();

                                                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(newEmail, pass)
                                                                        .addOnCompleteListener(task -> {
                                                                            if (task.isSuccessful()) {
                                                                                FirebaseUser user = task.getResult().getUser();
                                                                                String userUid = user.getUid();

                                                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                                                DocumentReference docRef = db.collection("userId").document(userId);
                                                                                DocumentReference docRefUser=db.collection("users").document(userId);

                                                                                Map<String, Object>updateDataMail=new HashMap<>();
                                                                                updateDataMail.put("mail",newEmail);
                                                                                docRefUser.update(updateDataMail).addOnSuccessListener(unused -> {

                                                                                    docRef.get().addOnSuccessListener(documentSnapshot2 -> {
                                                                                        if (documentSnapshot2.exists()) {
                                                                                            Map<String, Object> updateData = new HashMap<>();
                                                                                            updateData.put("uid", userUid);

                                                                                            docRef.update(updateData).addOnSuccessListener(unused2 -> {
                                                                                                Log.d("aaaa", "aaaaaaaaaaaaa");
                                                                                                Log.d("？？？？？？",userUid+" "+userOld.getEmail());
                                                                                                if (userOld != null) {
                                                                                                    AuthCredential credential = EmailAuthProvider.getCredential(userOld.getEmail(), pass);
                                                                                                    userOld.reauthenticate(credential)
                                                                                                            .addOnCompleteListener(reauthTask -> {
                                                                                                                if (reauthTask.isSuccessful()) {
                                                                                                                    userOld.delete().addOnCompleteListener(task2 -> {
                                                                                                                        if (task2.isSuccessful()) {
                                                                                                                            // Firebase Authentication での削除が成功した場合の処理
                                                                                                                            Log.d("FirebaseAuth", "User account deleted successfully!");

                                                                                                                            Toast.makeText(requireContext(), "メールアドレスが変更されました", Toast.LENGTH_SHORT).show();
                                                                                                                            // ログアウトや画面遷移などの処理が必要であればここで行う
                                                                                                                            Context context = requireActivity();
                                                                                                                            Intent intent = new Intent(context, LoginActivity.class);
                                                                                                                            startActivity(intent);

                                                                                                                        } else {
                                                                                                                            // Firebase Authentication での削除が失敗した場合の処理
                                                                                                                            Log.w("FirebaseAuth", "Error deleting user account", task.getException());
                                                                                                                            //                            Toast.makeText(requireContext(), "アカウントの削除に失敗しました", Toast.LENGTH_SHORT).show();
                                                                                                                            if (task.getException() != null) {
                                                                                                                                task.getException().printStackTrace();
                                                                                                                                Log.e("DeleteFragment", "Error during account deletion", task.getException());
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                                }
                                                                                                            });
                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    });

                                                                                });


                                                                            }
                                                                        });

                                                                }
                                                            }
                                                    });
                                        }
                                    }
                                }
                            });
                });
            }
        });
    }
}

