package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

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

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.widget.TextView;
import android.widget.Toast;

public class deleteFragment extends Fragment {
    public class deleteViewModel extends ViewModel {
        // ViewModel の実装を追加する
    }
    private deleteViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(requireActivity()).get(deleteViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // btnyes ボタンを取得
        Button btnYes = view.findViewById(R.id.btnyes);
        // btnno ボタンを取得
        Button btnNo = view.findViewById(R.id.btnno);

        // FirebaseAuth インスタンスを取得
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // btnyes ボタンにクリックリスナーを設定
        btnYes.setOnClickListener(new View.OnClickListener() {

            TextView sample;
            @Override
            public void onClick(View v) {
                // ボタンがクリックされた時の処理
                FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                sample=v.findViewById(R.id.textView29);
                sample.setText("sakujyo");
                if (users != null) {
                    users.delete()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // アカウントの削除が成功した場合の処理
                                    Toast.makeText(requireContext(), "アカウントが削除されました", Toast.LENGTH_SHORT).show();
                                } else {
                                    // アカウントの削除が失敗した場合の処理
                                    Toast.makeText(requireContext(), "アカウントの削除に失敗しました", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // 現在のユーザーがnullの場合の処理
                    Toast.makeText(requireContext(), "ユーザーがログインしていません", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // btnno ボタンにクリックリスナーを設定
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 前の画面に戻る
                Navigation.findNavController(v).navigateUp();
            }
        });
    }
}
