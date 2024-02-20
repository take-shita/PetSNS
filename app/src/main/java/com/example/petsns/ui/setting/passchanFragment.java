package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.petsns.R;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
public class passchanFragment extends Fragment {


    private TextView errorTextView; // クラスのメンバ変数として定義

    public static passchanFragment newInstance() {
        return new passchanFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passchan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        EditText editTextTextPassword3 = view.findViewById(R.id.editTextTextPassword3);
        EditText editTextTextPassword4 = view.findViewById(R.id.editTextTextPassword4);
        EditText editTextTextPasswordConfirm = view.findViewById(R.id.editTextTextPasswordConfirm); // 新しいパスワードの確認用
        Button button14 = view.findViewById(R.id.button14);
        errorTextView = view.findViewById(R.id.errorTextView);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = editTextTextPassword3.getText().toString();
                String newPassword = editTextTextPassword4.getText().toString();
                String newPasswordConfirm = editTextTextPasswordConfirm.getText().toString(); // 新しいパスワードの確認用

                if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) {
                    // パスワードが未入力の場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("現在のパスワードと新しいパスワードを入力してください");
                } else if (newPassword.length() < 8 || newPasswordConfirm.length() < 8) {
                    // 新しいパスワードが短すぎる場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("新しいパスワードは 8 文字以上入力してください");
                } else if (!newPassword.equals(newPasswordConfirm)) {
                    // 新しいパスワードが一致しない場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("新しいパスワードが一致しません");
                } else {
                    // パスワードが一致していれば、エラーメッセージを非表示にして次の画面に遷移
                    errorTextView.setVisibility(View.GONE);
                    // FirebaseUserを取得
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    // Firebaseのユーザーを再認証
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // パスワードの再認証が成功した場合、新しいパスワードに更新
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> updateTask) {
                                                        if (updateTask.isSuccessful()) {
                                                            // パスワードの変更が成功した場合
                                                            Toast.makeText(requireContext(), "パスワードの変更完了しました", Toast.LENGTH_SHORT).show();
                                                            Navigation.findNavController(v).navigate(R.id.action_navigation_passchan_to_navigation_setting);
                                                        } else {
                                                            // パスワードの変更が失敗した場合
                                                            errorTextView.setVisibility(View.VISIBLE);
                                                            errorTextView.setText("新しいパスワードの変更に失敗しました");
                                                        }
                                                    }
                                                });
                                    } else {
                                        // パスワードの再認証が失敗した場合、エラーメッセージを表示
                                        errorTextView.setVisibility(View.VISIBLE);
                                        errorTextView.setText("現在のパスワードが正しくありません");
                                    }
                                }
                            });
                }
            }
        });

        Button btncan = view.findViewById(R.id.btncan);

        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_passchan_to_navigation_setting);
            }
        });
    }
}
