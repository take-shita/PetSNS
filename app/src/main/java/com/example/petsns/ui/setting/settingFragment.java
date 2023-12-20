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
import com.example.petsns.R;

public class settingFragment extends Fragment {

    private SettingViewModel mViewModel;

    public static settingFragment newInstance() {
        return new settingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt1 = view.findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_setting_to_navigation_pass1);
            }
        });
        Button bt2 = view.findViewById(R.id.bt2);

        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_pass2));
            }
        });
    }
}




