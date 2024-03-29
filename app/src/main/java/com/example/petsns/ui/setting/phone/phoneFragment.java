package com.example.petsns.ui.setting.phone;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.petsns.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.TextView;

import java.util.concurrent.CompletableFuture;

public class phoneFragment extends Fragment {

    String phone;
    private EditText phoneNumberEditText;
    private int editTextPhoneMax = 11;
    String userId;
    public static phoneFragment newInstance() {
        return new phoneFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        phoneNumberEditText = view.findViewById(R.id.editTextPhone);

        // TextWatcherを追加
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > editTextPhoneMax) {
                    String truncatedText = editable.toString().substring(0, editTextPhoneMax);
                    phoneNumberEditText.setText(truncatedText);
                    phoneNumberEditText.setSelection(truncatedText.length()); // カーソル位置を最後に設定
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView numberinfo = view.findViewById(R.id.numberinfo);
        Button btnTw = view.findViewById(R.id.btnTw);
        Button btncan = view.findViewById(R.id.btncan);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userUid = user.getUid();


            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                CollectionReference collectionRefId = db.collection("userId");
                collectionRefId.whereEqualTo("uid", userUid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task1) {
                                if (task1.isSuccessful()) {
                                    for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                        // ドキュメントが見つかった場合、IDを取得
                                        userId = document1.getId();

                                        DocumentReference userRef = db.collection("users").document(userId);
                                        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    String currentPhoneNumber = documentSnapshot.getString("phoneNumber");
                                                    if (currentPhoneNumber != null) {
                                                        // 現在の電話番号が登録されている場合は表示する
                                                        numberinfo.setText("現在の登録電話番号は " + currentPhoneNumber + " です。");
                                                    } else {
                                                        // 現在の電話番号が登録されていない場合は適切なメッセージを表示する
                                                        numberinfo.setText("現在電話番号は登録されていません。");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
            });
        }
        btnTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 電話番号が11桁かどうかを確認
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                if (phoneNumber.length() != 11) {
                    // 電話番号が11桁でない場合はエラーを表示
                    Toast.makeText(requireContext(), "電話番号は11桁で入力してください", Toast.LENGTH_SHORT).show();
                    return; // エラーが発生したため、以降の処理を中断
                }
                    // Cloud Firestoreから現在の電話番号を取得

                    if (user != null) {
                        String userUid = user.getUid();


                        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                                    CollectionReference collectionRefId = db.collection("userId");
                                    collectionRefId.whereEqualTo("uid", userUid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(Task<QuerySnapshot> task1) {
                                                    if (task1.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                            // ドキュメントが見つかった場合、IDを取得
                                                            userId = document1.getId();

                                                            DocumentReference userRef = db.collection("users").document(userId);
                                                            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    if (documentSnapshot.exists()) {
                                                                        String currentPhoneNumber = documentSnapshot.getString("phoneNumber");
                                                                        if (currentPhoneNumber != null) {
                                                                            // 現在の電話番号が登録されている場合は表示する
                                                                            numberinfo.setText("現在の登録電話番号は " + currentPhoneNumber + " です。");
                                                                        } else {
                                                                            // 現在の電話番号が登録されていない場合は適切なメッセージを表示する
                                                                            numberinfo.setText("現在電話番号は登録されていません。");
                                                                        }
                                                                        if (currentPhoneNumber != null && currentPhoneNumber.equals(phoneNumber)) {
                                                                            // 入力された電話番号と現在の電話番号が同じ場合はエラーを表示
                                                                            Toast.makeText(requireContext(), "入力された電話番号は既に登録されています", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            // 入力された電話番号と現在の電話番号が異なる場合は更新を試みる
                                                                            userRef.update("phoneNumber", phoneNumber)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void aVoid) {
                                                                                            // 11桁の場合は設定画面に戻る
                                                                                            Toast.makeText(requireContext(), "電話番号の登録完了しました", Toast.LENGTH_SHORT).show();
                                                                                            Navigation.findNavController(v).navigate(R.id.action_navigation_phone_to_navigation_setting);
                                                                                        }
                                                                                    })
                                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {
                                                                                            Toast.makeText(requireContext(), "電話番号の登録に失敗しました", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                });


                    }
                }

        });


        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btncanがクリックされたときの処理
                Navigation.findNavController(v).navigate(R.id.action_navigation_phone_to_navigation_pass1);
            }
        });
    }
}

