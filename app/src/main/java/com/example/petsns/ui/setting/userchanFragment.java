package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class userchanFragment extends Fragment {

    private UserchanViewModel mViewModel;

    private FirebaseAuth mAuth;
    private TextView textViewUsername;
    private EditText editTextNewUsername;
    private Button btnChangeUsername;
    String userId;
    private View view; // メンバ変数として宣言

    public static userchanFragment newInstance() {
        return new userchanFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userchan, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserchanViewModel.class);
        // TODO: Use the ViewModel
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewUsername = view.findViewById(R.id.textViewUsername);
        editTextNewUsername = view.findViewById(R.id.editTextNewUsername);
        btnChangeUsername = view.findViewById(R.id.btnChangeUsername);

        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });

        // 追加：「戻る」ボタンがクリックされたときの処理
        Button btncan = view.findViewById(R.id.btncan);
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ひとつ前の画面に戻る
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
        // ユーザー情報を表示
        displayUserInfo();
    }

        private void displayUserInfo() {
            FirebaseUser user = mAuth.getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

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

                                                    DocumentReference userDocRef = db.collection("users").document(userId);

                                                    userDocRef.get()
                                                            .addOnSuccessListener(documentSnapshot -> {
                                                                if (documentSnapshot.exists()) {
                                                                    // ドキュメントが存在する場合
                                                                    String username = documentSnapshot.getString("name");
                                                                    if (userId != null && !userId.isEmpty()) {
                                                                        textViewUsername.setText("" + username);
                                                                    } else {
                                                                        textViewUsername.setText("Username: (No username set)");
                                                                    }

                                                                } else {
                                                                    // ドキュメントが存在しない場合
                                                                    Toast.makeText(requireContext(), "ユーザードキュメントが存在しません", Toast.LENGTH_SHORT).show();

                                                                }
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                // 取得失敗時の処理
                                                                Toast.makeText(requireContext(), "ドキュメントの取得に失敗しました", Toast.LENGTH_SHORT).show();
                                                            });
                                                }
                                            }
                                        }
                                    });
                        });
                try {
                    future1.get(); // 非同期処理が終わるまでブロック

                } catch (InterruptedException | ExecutionException e) {
                    // 例外処理
                }



            }
        }

        private void changeUsername() {
            FirebaseUser user = mAuth.getCurrentUser();
            String newUsername = editTextNewUsername.getText().toString().trim();


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


            if (currentUser != null) {
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

                                            // Firestoreの"users"コレクション内のユーザードキュメントにアクセス
                                            DocumentReference userDocRef = db.collection("users").document(userId);

                                            // 新しい名前


                                            // ドキュメントの更新
                                            userDocRef.update("name", newUsername)
                                                    .addOnSuccessListener(aVoid -> {
                                                        // 更新成功時の処理
                                                        Toast.makeText(requireContext(), "名前が更新されました", Toast.LENGTH_SHORT).show();
                                                        displayUserInfo(); // 更新後のユーザー情報を再表示
                                                        Navigation.findNavController(getView()).navigateUp();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        // 更新失敗時の処理
                                                        Toast.makeText(requireContext(), "名前の更新に失敗しました", Toast.LENGTH_SHORT).show();
                                                    });

                                        }
                                    }
                                }
                            });
                });



            } else {
                // ユーザーがログインしていない場合の処理
                Toast.makeText(requireContext(), "ユーザーがログインしていません", Toast.LENGTH_SHORT).show();
            }

        }
    }