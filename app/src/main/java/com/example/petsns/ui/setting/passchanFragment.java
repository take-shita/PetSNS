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

    private PasschanViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(PasschanViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText passwordEditText = view.findViewById(R.id.passwordEditText);
        EditText editTextTextPassword3 = view.findViewById(R.id.editTextTextPassword3);
        EditText editTextTextPassword4 = view.findViewById(R.id.editTextTextPassword4);
        Button button14 = view.findViewById(R.id.button14);
        errorTextView = view.findViewById(R.id.errorTextView);

        button14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pass = editTextTextPassword3.getText().toString();
                String word = editTextTextPassword4.getText().toString();
                String password = passwordEditText.getText().toString();

                if (password.isEmpty() || pass.isEmpty() || word.isEmpty()) {
                    // パスワードが未入力の場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("全てのパスワードを入力してください");
                } else if (password.length() < 8 || pass.length() < 8 || word.length() < 8) {
                    // パスワードが短すぎる場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("パスワードは 8 文字以上入力してください");
                } else if (!password.equals(password) || !password.equals(word)) {
                    // パスワードが一致しない場合、エラーメッセージを表示
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText("パスワードが一致しません");
                } else {
                    // パスワードが一致していれば、エラーメッセージを非表示にして次の画面に遷移
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
                                        Navigation.findNavController(v).navigate(R.id.action_navigation_passchan_to_navigation_setting);
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


        Button btncan = view.findViewById(R.id.btncan);

        btncan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_passchan_to_navigation_setting);
            }
        });
    }
}
