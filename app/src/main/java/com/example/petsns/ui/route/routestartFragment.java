package com.example.petsns.ui.route;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.View;
import android.widget.Button;
import com.example.petsns.R;

public class routestartFragment extends Fragment {

   public routestartFragment(){

   }
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bt = view.findViewById(R.id.stop);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                Navigation.findNavController(v).navigate(R.id.action_navigation_routestart_to_navigation_routestop);
            }
        });

    }
}