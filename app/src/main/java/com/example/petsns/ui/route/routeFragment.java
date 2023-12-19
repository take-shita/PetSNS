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

public class routeFragment extends Fragment {


    private RouteViewModel mViewModel;

    public static routeFragment newInstance() {
        return new routeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route, container, false);
    }


    //    画面遷移---------------------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt_jude = view.findViewById(R.id.set);
        bt_jude.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route2);
            }
        });


        Button btn1 = view.findViewById(R.id.route1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route4);
            }
        });

    }
//    -----------------------------------------------------------------------------------------------------------




}



