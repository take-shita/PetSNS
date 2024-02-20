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

public class routefavoriteFragment extends Fragment {


    public static routefavoriteFragment newInstance() {
        return new routefavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routefavorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt = view.findViewById(R.id.start1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_routefavorite_to_navigation_route);
            }
        });
    }
}