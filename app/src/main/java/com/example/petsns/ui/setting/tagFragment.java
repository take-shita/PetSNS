package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.petsns.R;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

public class tagFragment extends Fragment {

    private TagViewModel mViewModel;

    public static tagFragment newInstance() {
        return new tagFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_tag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnback = view.findViewById(R.id.btnback);

        btnback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_tag_to_navigation_setting);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);  // この行を適切なIDに変更してください

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
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);  // この行を適切なIDに変更してください
        transaction.commit();
    }
}

