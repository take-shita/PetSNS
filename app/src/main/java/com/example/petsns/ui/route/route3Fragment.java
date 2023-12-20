package com.example.petsns.ui.route;

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

public class route3Fragment extends Fragment {

    private Route3ViewModel mViewModel;

    public static route3Fragment newInstance() {
        return new route3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route3, container, false);
    }



    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.time1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route3_to_navigation_route2);
            }
        });


        Button btn1 = view.findViewById(R.id.route1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route3_to_navigation_route4);
            }
        });

    }

    }

