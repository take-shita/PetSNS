package com.example.petsns;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import android.content.Intent;
import com.example.petsns.ui.setting.TagLikeFragment;
import com.example.petsns.ui.setting.TagDislikeFragment;
import com.google.android.material.tabs.TabLayout;
import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Signup2 extends AppCompatActivity{
    private SigupViewModel viewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        TabLayout tabLayout = findViewById(R.id.tabLayout); // タブのIDに変更してください

        // タブが選択されたときのリスナー
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // タブが選択されたときに対応するFragmentを表示
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new TagLikeFragment());
                        break;
                    case 1:
                        replaceFragment(new TagDislikeFragment());
                        break;
                    // 必要に応じて他のタブに対する処理を追加
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 他のタブが選択されたときの処理
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 同じタブが再度選択されたときの処理
            }
        });

        // 最初のタブを表示
        replaceFragment(new TagLikeFragment());






        Button btnNext=findViewById(R.id.btnNext);
        viewModel = new ViewModelProvider(this).get(SigupViewModel.class);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context context = v.getContext();

                Intent intent = new Intent(context, Signup3.class);
                startActivity(intent);
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager(); // getSupportFragmentManager()を使用する
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // フラグメントのコンテナのIDに変更してください
        transaction.commit();
    }

}
