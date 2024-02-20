package com.example.petsns.ui.setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TagLikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TagLikeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TagViewModel viewModel;
    public TagLikeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TagLikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TagLikeFragment newInstance(String param1, String param2) {
        TagLikeFragment fragment = new TagLikeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        MyApplication myApplication = (MyApplication) requireActivity().getApplication();

// MyApplicationの初期化が行われていることを確認する
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagViewModel();
        } else {
            // エラーハンドリング
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_like, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

                        dialog.dismiss();
                    }
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

                CheckBox chkCt=dialog.findViewById(R.id.chkSn);
                CheckBox chkDg=dialog.findViewById(R.id.chkLz);
                CheckBox chkRb=dialog.findViewById(R.id.chkTt);
                CheckBox chkHg=dialog.findViewById(R.id.chkAl);
                CheckBox chkHm=dialog.findViewById(R.id.chkGc);
                CheckBox chkOt=dialog.findViewById(R.id.chkCm);

                Button btnClose = dialog.findViewById(R.id.btnno);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { viewModel.setArrayLikeRip(chkCt.isChecked(),chkDg.isChecked(),chkRb.isChecked(),
                            chkHg.isChecked(),chkHm.isChecked(),chkOt.isChecked());
                        dialog.dismiss(); }
                });
                dialog.show();
            }
        });
    }
}