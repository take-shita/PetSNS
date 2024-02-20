package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.TextView;
import android.widget.Toast;

public class deleteFragment extends Fragment {
    private FirebaseAuth mAuth;

    public class deleteViewModel extends ViewModel {
        // ViewModel の実装を追加する
    }





    public static deleteFragment newInstance() {
        return new deleteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        // FirebaseAuth インスタンスを取得
//        mAuth = FirebaseAuth.getInstance();
//
//        // btnyes ボタンを取得
//        Button btnYes = view.findViewById(R.id.btnyes);
//        // btnno ボタンを取得
//        Button btnNo = view.findViewById(R.id.btnno);
//
//        // btnyes ボタンにクリックリスナーを設定
//        btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 現在ログインしているユーザーを取得
//                FirebaseUser user = mAuth.getCurrentUser();
//
//                if (user != null) {
//                    // Firebase Authenticationからユーザーを削除
//                    user.delete().addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            // Firebase Authenticationでの削除が成功した場合の処理
//                            // ここでFirestoreのユーザーデータを削除するコードを追加
//                            deleteFirestoreUserData(user.getUid());
//                            Log.d("FirebaseAuth", "User account deleted successfully!");
//
//                            Toast.makeText(requireContext(), "アカウントが削除されました", Toast.LENGTH_SHORT).show();
//                            // ログアウトや画面遷移などの処理が必要であればここで行う
//                            Navigation.findNavController(getView()).navigateUp();
//                        } else {
//                            // Firebase Authenticationでの削除が失敗した場合の処理
//                            Log.w("FirebaseAuth", "Error deleting user account", task.getException());
////                            Toast.makeText(requireContext(), "アカウントの削除に失敗しました", Toast.LENGTH_SHORT).show();
//                            if (task.getException() != null) {
//                                task.getException().printStackTrace();
//                                Log.e("DeleteFragment", "Error during account deletion", task.getException());
//                            }
//                        }
//                    });
//                }
//            }
//            private void deleteFirestoreUserData(String userId) {
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                db.collection("users").document(userId)
//                        .delete()
//                        .addOnSuccessListener(aVoid -> {
//                            Log.d("Firestore", "DocumentSnapshot successfully deleted!");
//                            // 他の処理を追加する
//                            // ドキュメントの削除が成功した場合の処理
//                            // ここに適切な処理を追加する（例: ユーザーのデータが正常に削除されたときの処理）
//                        })
//                        .addOnFailureListener(e -> {
//                            Log.w("Firestore", "Error deleting document", e);
//                            // エラー時の処理を追加する
//                            // ドキュメントの削除が失敗した場合の処理
//                            Toast.makeText(requireContext(), "ユーザーデータの削除に失敗しました", Toast.LENGTH_SHORT).show();
//                            if (e != null) {
//                                e.printStackTrace();
//                            }
//                        });
//            }
//        });

        // btnno ボタンにクリックリスナーを設定
//        btnNo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 前の画面に戻る
//                Navigation.findNavController(v).navigateUp();
//            }
//        });
    }
}

