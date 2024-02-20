package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.app.Dialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;
import android.widget.Toast;

import com.example.petsns.LoginActivity;
import com.example.petsns.MainActivity;
import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.CompletableFuture;

public class settingFragment extends Fragment {

    private SettingViewModel mViewModel;
    private FirebaseAuth mAuth;
    private Firebase db;
    private String userId;
    public static settingFragment newInstance() {
        return new settingFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        // TODO: Use the ViewModel
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt1 = view.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_setting_to_navigation_pass1);
            }
        });
        Button bt2 = view.findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_pass2));
            }
        });
        Button bt3 = view.findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_passchan));
            }
        });
        Button bt4 = view.findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Context context = requireContext();
                Dialog dialog= new Dialog(context);
                // レイアウトファイルをインフレート
                dialog.setContentView(R.layout.fragment_delete);
                // ダイアログのサイズを設定
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 500; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                // FirebaseAuth インスタンスを取得
                mAuth = FirebaseAuth.getInstance();
                // btnyes ボタンを取得
                Button btnYes = dialog.findViewById(R.id.btnyes);
                // btnno ボタンを取得
                Button btnNo = dialog.findViewById(R.id.btnno);
                // btnyes ボタンにクリックリスナーを設定
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 現在ログインしているユーザーを取得
                        FirebaseUser user = mAuth.getCurrentUser();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
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

                                                            db.collection("users") // コレクション名
                                                                    .document(userId) // ドキュメント名
                                                                    .get()
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                            if (documentSnapshot.exists()) {

                                                                                String pass = documentSnapshot.getString("password");

                                                                                if (user != null) {
                                                                                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), pass); // ユーザーの実際のパスワードを使用してください
                                                                                    user.reauthenticate(credential)
                                                                                            .addOnCompleteListener(reauthTask -> {
                                                                                                if (reauthTask.isSuccessful()) {
                                                                                                    user.delete().addOnCompleteListener(task -> {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            // Firebase Authenticationでの削除が成功した場合の処理
                                                                                                            // ここでFirestoreのユーザーデータを削除するコードを追加
                                                                                                            deleteFirestoreUserData(userId);
                                                                                                            Log.d("FirebaseAuth", "User account deleted successfully!");

                                                                                                            Toast.makeText(requireContext(), "アカウントが削除されました", Toast.LENGTH_SHORT).show();
                                                                                                            // ログアウトや画面遷移などの処理が必要であればここで行う
                                                                                                            Context context = v.getContext();

                                                                                                            Intent intent = new Intent(context, LoginActivity.class);
                                                                                                            startActivity(intent);

                                                                                                        } else {
                                                                                                            // Firebase Authenticationでの削除が失敗した場合の処理
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

                                                                            }
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            // エラーが発生した場合の処理
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            });
                                });
                    }
                    private void deleteFirestoreUserData(String userId) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("users").document(userId)
                                .delete()
                                .addOnSuccessListener(aVoid -> {

                                    db.collection("userId").document(userId)
                                            .delete()
                                            .addOnCompleteListener(aVoid2 ->{
                                                Log.d("Firestore", "DocumentSnapshot successfully deleted!");
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error deleting document", e);
                                    // エラー時の処理を追加する
                                    // ドキュメントの削除が失敗した場合の処理
                                    Toast.makeText(requireContext(), "ユーザーデータの削除に失敗しました", Toast.LENGTH_SHORT).show();
                                    if (e != null) {
                                        e.printStackTrace();
                                    }
                                });
                    }
                });
                Button btnClose = dialog.findViewById(R.id.btnno);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });
                    dialog.show();
                }
        });

        Button btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Context context = requireContext();
                Dialog dialog= new Dialog(context);
                dialog.setContentView(R.layout.fragment_logout);
                // レイアウトファイルをインフレート
                dialog.setContentView(R.layout.fragment_logout);
                // ダイアログのサイズを設定
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 500; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                Button btnYes = dialog.findViewById(R.id.btnyes);
                Button btnClose = dialog.findViewById(R.id.btnno);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ログアウト
                        FirebaseAuth.getInstance().signOut();
                        Context context = v.getContext();

                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });
                dialog.show();
            }
        });
        Button bt6 = view.findViewById(R.id.bt6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_icon));
            }
        });
        Button bt7 = view.findViewById(R.id.bt7);
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_userchan));
            }
        });
        Button bt8 = view.findViewById(R.id.bt8);
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_tag));
            }
        });

        Button bt9 = view.findViewById(R.id.bt9);
        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_repot));
            }
        });
    }
}