package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

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

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextUtils;
public class emailFragment extends Fragment {

    private EmailViewModel mViewModel;
    private EditText editTextNewEmail;  // EditText を格納する変数


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
        FirebaseUser user = mAuth.getCurrentUser();

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
                // 新しいメールアドレスを入力フォームから取得
                String newEmail = editTextNewEmail.getText().toString();
                // FirebaseAuth インスタンスを取得
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                // 現在のユーザーを取得
                FirebaseUser user = mAuth.getCurrentUser();
                // 新しいメールアドレスが空でないことを確認
                if (!TextUtils.isEmpty(newEmail)) {
                    // 現在のメールアドレスと新しいメールアドレスが異なる場合のみ更新
                    if (!newEmail.equals(user.getEmail())) {

                    // メールアドレスの更新
                    user.updateEmail(newEmail)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // メールアドレスの更新が成功した場合
                                        Toast.makeText(requireContext(), "メールアドレスが変更されました", Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(v).navigate(R.id.action_navigation_email_to_navigation_setting);
                                    } else {
                                        // メールアドレスの更新が失敗した場合
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(requireContext(), "メールアドレスの変更が失敗しました：" + errorMessage, Toast.LENGTH_SHORT).show();
                                        Log.d("error",errorMessage);
                                    }
                                }
                            });
                    } else {
                        // 現在のメールアドレスと同じ場合はエラーメッセージを表示
                        Toast.makeText(requireContext(), "新しいメールアドレスが現在のメールアドレスと同じです", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 新しいメールアドレスが空の場合はエラーメッセージを表示
                    Toast.makeText(requireContext(), "新しいメールアドレスを入力してください", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

