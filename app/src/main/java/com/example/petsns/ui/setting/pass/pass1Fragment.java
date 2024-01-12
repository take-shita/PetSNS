package com.example.petsns.ui.setting.pass;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.petsns.R;

public class pass1Fragment extends Fragment {

    private Pass1ViewModel mViewModel;

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

        Button bt01 = view.findViewById(R.id.bt01);
        EditText passwordField = view.findViewById(R.id.passwordField);

        bt01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String enteredPassword = passwordField.getText().toString();

                // パスワードが 8 文字以上であることを確認
                if (TextUtils.isEmpty(enteredPassword) || enteredPassword.length() < 8) {
                    showToast("パスワードは 8 文字以上で入力してください");
                } else {
                    Navigation.findNavController(v).navigate((R.id.action_navigation_pass1_to_navigation_phone));
                }
            }

            private void showToast(String s) {
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