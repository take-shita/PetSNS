package com.example.petsns;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class tag_postFragment extends Fragment {

    private TagPostViewModel viewModel;
    private Boolean value;
    private ViewGroup buttonContainer;
    public static tag_postFragment newInstance() {
        return new tag_postFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_post, container,false);
//        buttonContainer = view.findViewById(R.id.buttonContainer);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagPostViewModel();
        } else {
            // エラーハンドリング
        }
        Bundle args = getArguments();
        if (args != null) {
            value = args.getBoolean("key");
            // データを使用して何かを行う
            // 例: TextViewにセットするなど
        }
        displayButtons();

        return view;

    }

    private void displayButtons() {
        // 表示するボタンの数
//        int buttonCount = 10;
//
//        for (int i = 0; i < buttonCount; i++) {
//            Button button = new Button(requireContext());
//            button.setText("Button " + (i + 1));
//
//            // ボタンがクリックされたときの処理を追加
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // ボタンがクリックされたときの処理を記述
//                }
//            });
//
//            // ボタンをコンテナに追加
//            buttonContainer.addView(button);
//        }
    }


    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button select_tag = view.findViewById(R.id.cancel_btn);
        select_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.tagCancel();

                if(value != null && value){
                    Navigation.findNavController(v).navigate(R.id.action_navigation_tag_post_to_navigation_contest_post);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_tag_post_to_navigation_snspost);
                }
            }
        });

        Button btnDec=view.findViewById(R.id.decision_btn);
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value != null && value){
                    Navigation.findNavController(v).navigate(R.id.action_navigation_tag_post_to_navigation_contest_post);
                }else {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_tag_post_to_navigation_snspost);
                }

            }
        });

        Button bth = view.findViewById(R.id.bth);

        bth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_mammalian);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                CheckBox chkCt=dialog.findViewById(R.id.chkCt);
                CheckBox chkDg=dialog.findViewById(R.id.chkDg);
                CheckBox chkRb=dialog.findViewById(R.id.chkRb);
                CheckBox chkHg=dialog.findViewById(R.id.chkHg);
                CheckBox chkHm=dialog.findViewById(R.id.chkHm);
                CheckBox chkOt=dialog.findViewById(R.id.chkOt);
                CheckBox chkCh=dialog.findViewById(R.id.chkCh);
                CheckBox chkFl=dialog.findViewById(R.id.chkFl);
                CheckBox chkMg=dialog.findViewById(R.id.chkMg);
                CheckBox chkFx=dialog.findViewById(R.id.chkFx);
                CheckBox chkSq=dialog.findViewById(R.id.chkSq);
                Button btnClose = dialog.findViewById(R.id.btnno);


                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {
                        viewModel.setArrayLikeMom(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                                chkHg.isChecked(),chkHm.isChecked(),chkOt.isChecked(),chkCh.isChecked(),
                                chkFl.isChecked(),chkMg.isChecked(),chkFx.isChecked(),chkSq.isChecked());

                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
        Button btb = view.findViewById(R.id.btb);

        btb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_bird);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                CheckBox chkCt=dialog.findViewById(R.id.chkPrt);
                CheckBox chkDg=dialog.findViewById(R.id.chkPrk);
                CheckBox chkRb=dialog.findViewById(R.id.chkSp);
                CheckBox chkHg=dialog.findViewById(R.id.chkCn);
                CheckBox chkHm=dialog.findViewById(R.id.chkOw);
                CheckBox chkOt=dialog.findViewById(R.id.chkBc);
                CheckBox chkCh=dialog.findViewById(R.id.chkCh);
                CheckBox chkFl=dialog.findViewById(R.id.chkDc);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        viewModel.setArrayLikeBir(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                                chkHg.isChecked(),chkHm.isChecked(),chkOt.isChecked(),chkCh.isChecked(),
                                chkFl.isChecked());
                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
        Button bti = view.findViewById(R.id.bti);

        bti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_insect);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                CheckBox chkCt=dialog.findViewById(R.id.chkBt);
                CheckBox chkDg=dialog.findViewById(R.id.chkSt);
                CheckBox chkRb=dialog.findViewById(R.id.chkMt);
                CheckBox chkHg=dialog.findViewById(R.id.chkCc);
                CheckBox chkHm=dialog.findViewById(R.id.chkSp);
                CheckBox chkOt=dialog.findViewById(R.id.chkLc);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        viewModel.setArrayLikeIns(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                                chkHg.isChecked(),chkHm.isChecked(),chkOt.isChecked());
                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
        Button btr = view.findViewById(R.id.btr);

        btr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_bisexual);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                CheckBox chkCt=dialog.findViewById(R.id.chkFr);
                CheckBox chkDg=dialog.findViewById(R.id.chkNw);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        viewModel.setArrayLikeBis(chkCt.isChecked(),chkDg.isChecked());
                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
        Button bts = view.findViewById(R.id.bts);

        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_aquatic);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                CheckBox chkCt=dialog.findViewById(R.id.chkCr);
                CheckBox chkDg=dialog.findViewById(R.id.chkMd);
                CheckBox chkRb=dialog.findViewById(R.id.chkGf);
                CheckBox chkHg=dialog.findViewById(R.id.chkJr);

                Button btnClose = dialog.findViewById(R.id.btnno);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        viewModel.setArrayLikeAqua(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                                chkHg.isChecked());
                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
        Button btha = view.findViewById(R.id.btha);

        btha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_popup_reptiles);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 600; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnno);

                CheckBox chkCt=dialog.findViewById(R.id.chkSn);
                CheckBox chkDg=dialog.findViewById(R.id.chkLz);
                CheckBox chkRb=dialog.findViewById(R.id.chkTt);
                CheckBox chkHg=dialog.findViewById(R.id.chkAl);
                CheckBox chkHm=dialog.findViewById(R.id.chkGc);
                CheckBox chkOt=dialog.findViewById(R.id.chkCm);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        viewModel.setArrayLikeRip(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                                chkHg.isChecked(),chkHm.isChecked(),chkOt.isChecked());
                        dialog.dismiss(); }
                });

                dialog.show();
            }
        });
    };
}