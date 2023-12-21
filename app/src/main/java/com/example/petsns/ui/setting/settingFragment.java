package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.app.Dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.petsns.R;
import android.content.Context;
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
        Button bt3 = view.findViewById(R.id.bt3);

        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_passchan));
            }
        });
        Button bt4 = view.findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(View v) {
                Context context = requireContext();
                Dialog dialog= new Dialog(context);
                dialog.setContentView(R.layout.fragment_delete);

                // レイアウトファイルをインフレート
                dialog.setContentView(R.layout.fragment_delete);

                // ダイアログのサイズを設定
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 500; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });

                    dialog.show();
                }

        });
        Button bt5 = view.findViewById(R.id.bt5);
        bt5.setOnClickListener(new View.OnClickListener() {

            @Override
            public  void onClick(View v) {
                Context context = requireContext();
                Dialog dialog= new Dialog(context);
                dialog.setContentView(R.layout.fragment_logout);

                // レイアウトファイルをインフレート
                dialog.setContentView(R.layout.fragment_logout);

                // ダイアログのサイズを設定
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 500; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });

                dialog.show();
            }

        });
        Button bt6 = view.findViewById(R.id.bt6);

        bt6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_icon));
            }
        });
        Button bt7 = view.findViewById(R.id.bt7);

        bt7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_userchan));
            }
        });
        Button bt8 = view.findViewById(R.id.bt8);

        bt8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_tag));
            }
        });
        Button bt9 = view.findViewById(R.id.bt9);

        bt9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_setting_to_navigation_repot));
            }
        });
    }
}




