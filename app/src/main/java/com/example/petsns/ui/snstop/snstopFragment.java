package com.example.petsns.ui.snstop;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.R;
import com.example.petsns.MyAdapter;
import com.example.petsns.ui.setting.TagDislikeFragment;
import com.example.petsns.ui.setting.TagLikeFragment;
import com.google.android.material.tabs.TabLayout;

import android.widget.Button;
import android.widget.ImageButton;

public class snstopFragment extends Fragment {

    private SnstopViewModel mViewModel;

    public static snstopFragment newInstance() {
        return new snstopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_snstop, container, false);

        // RecyclerViewを取得
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // レイアウトマネージャーを設定
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // アダプターを設定（自分のアダプタークラスに置き換える必要があります）
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SnstopViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton prof_bt = view.findViewById(R.id.top_prof);
        prof_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_profile);
            }
        });

        ImageButton search_btn = view.findViewById(R.id.search_tag);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_tag_search);
            }
        });


        ImageButton post = view.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_snspost);
            }
        });
    }
}