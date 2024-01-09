package com.example.petsns.ui.route;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.petsns.R;
import com.example.petsns.ui.dashboard.DashboardFragment;

public class routeFragment extends DashboardFragment {


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



        Button btn1 = view.findViewById(R.id.route1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route4);
            }
        });


        Button btn = view.findViewById(R.id.start1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.action_navigation_route_to_navigation_route7);
            }
        });

        Button ro = view.findViewById(R.id.set);//投稿削除確認ポップアップ画面
        ro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_route2);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button a = dialog.findViewById(R.id.distance1);
                Button b = dialog.findViewById(R.id.time);

                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });




    }


//    -----------------------------------------------------------------------------------------------------------

}



