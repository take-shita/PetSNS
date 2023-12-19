package com.example.petsns.ui.route;

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

public class route6Fragment extends Fragment {

    private Route6ViewModel mViewModel;

    public static route6Fragment newInstance() {
        return new route6Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route6, container, false);
    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt = view.findViewById(R.id.start1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route6_to_navigation_route);
            }
        });
    }
}