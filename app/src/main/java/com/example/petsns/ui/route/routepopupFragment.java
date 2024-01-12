package com.example.petsns.ui.route;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.petsns.R;

public class routepopupFragment extends DialogFragment {

    private RoutepopupViewModel mViewModel;

    public static routepopupFragment newInstance() {
        return new routepopupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routepopup, container, false);
    }


//    画面遷移--------------------------------------------------------------------------------
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt= view.findViewById(R.id.distance1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_routepopup_to_navigation_route);
            }
        });


        Button btn= view.findViewById(R.id.time);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_routepopup_to_navigation_routetime);
            }
        });

    }


//------------------------------------------------------------------------------------------------------------------
}