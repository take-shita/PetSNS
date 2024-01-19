package com.example.petsns.ui.setting.pass;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import android.widget.EditText;
import android.widget.TextView;
import android.text.InputFilter;

public class pass1Fragment extends Fragment {

    private Pass1ViewModel mViewModel;

    private TextView errorTextView; // クラスのメンバ変数として定義


    public static pass1Fragment newInstance() {
        return new pass1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pass1, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Pass1ViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText passwordEditText = view.findViewById(R.id.passwordEditText); // ここで適切な ID を指定する
        Button bt01 = view.findViewById(R.id.bt01);
        errorTextView = view.findViewById(R.id.errorTextView); // TextView の初期化
        
        bt01.setOnClickListener(new View.OnClickListener() {

//            EditText passwordEditText = view.findViewById(R.id.passwordEditText);
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();

                if (password.isEmpty()) {
                    // パスワードが未入力の場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("パスワードを入力してください");
                } else if (password.length() < 8) {
                    // パスワードが8文字未満の場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("パスワードは8文字以上で入力してください");
                } else {
                    // パスワードが入力されており、8文字以上である場合
                    errorTextView.setVisibility(View.GONE);

                    // FirebaseUserを取得
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Firebaseのユーザーを再認証
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // パスワードの再認証が成功した場合、次の画面に遷移
                                        Navigation.findNavController(v).navigate(R.id.action_navigation_pass1_to_navigation_phone);
                                    } else {
                                        // パスワードの再認証が失敗した場合、エラーメッセージを表示
                                        errorTextView.setVisibility(View.VISIBLE);
                                        errorTextView.setText("パスワードが正しくありません");
                                    }
                                }
                            });
                }
            }
        });

        Button bt02 = view.findViewById(R.id.bt02);

        bt02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_pass1_to_navigation_setting));
            }
        });
    }

}