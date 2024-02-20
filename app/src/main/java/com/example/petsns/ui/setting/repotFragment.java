package com.example.petsns.ui.setting;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.petsns.R;
import com.example.petsns.ui.setting.RepotViewModel;

public class repotFragment extends Fragment {

    private RepotViewModel mViewModel;

    public static repotFragment newInstance() {
        return new repotFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repot, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RepotViewModel.class);
        // TODO: Use the ViewModel
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btncan = view.findViewById(R.id.btncan);
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 前の画面に戻る
                Navigation.findNavController(v).navigate(R.id.action_navigation_repot_to_navigation_setting);
            }
        });

        Button btnsend = view.findViewById(R.id.btnsend);
        EditText editText = view.findViewById(R.id.editTextTextMultiLine);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText から文書を取得
                String subject = "報告の件名";  // 任意の件名を設定
                String message = editText.getText().toString();

                int minMessageLength = 1;

                // 文字数が制限を超えていないかチェック
                if (message.length() < minMessageLength) {
                    // 文字数が制限を超えている場合はエラーメッセージを表示するなどの処理を行う
                    // 例えば、Toast メッセージを表示
                    Toast.makeText(getContext(), "報告・要望を入力してください " , Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase Firestore に報告を送信
                    mViewModel.sendReportToFirestore(message, getContext());
                    Navigation.findNavController(getView()).navigateUp();
                    // 送信後の処理をここに追加（例えば、Toast メッセージを表示するなど）
                }
            }
        });
    }
}