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

public class route2Fragment extends Fragment {

    private Route2ViewModel mViewModel;

    public static route2Fragment newInstance() {
        return new route2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route2, container, false);
    }


//    画面遷移--------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt= view.findViewById(R.id.distance1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route2_to_navigation_route);
            }
        });


        Button btn= view.findViewById(R.id.time);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route2_to_navigation_route3);
            }
        });

    }


//------------------------------------------------------------------------------------------------------------------
}