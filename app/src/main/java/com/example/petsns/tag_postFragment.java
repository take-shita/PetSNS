package com.example.petsns;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class tag_postFragment extends Fragment {

    private TagPostViewModel mViewModel;

    private ViewGroup buttonContainer;
    public static tag_postFragment newInstance() {
        return new tag_postFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_post, container,false);
        buttonContainer = view.findViewById(R.id.buttonContainer);

        displayButtons();

        return view;

    }

    private void displayButtons() {
        // 表示するボタンの数
        int buttonCount = 10;

        for (int i = 0; i < buttonCount; i++) {
            Button button = new Button(requireContext());
            button.setText("Button " + (i + 1));

            // ボタンがクリックされたときの処理を追加
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ボタンがクリックされたときの処理を記述
                }
            });

            // ボタンをコンテナに追加
            buttonContainer.addView(button);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TagPostViewModel.class);
        // TODO: Use the ViewModel
    }
}