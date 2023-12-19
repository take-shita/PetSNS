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

        Button bt3 = view.findViewById(R.id.bt3);

        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_pass1_to_navigation_phone);
            }
        });
    }

}