package com.example.petsns.ui.contest;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.Gravity;

import com.example.petsns.MainActivity;
import com.example.petsns.R;
import android.content.Context;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import  com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class ContestFragment extends Fragment {
    private TextView sampleText;
    private FirebaseFirestore db;
    private ContestViewModel mViewModel;
    private TextView popupText;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnView;
    Button btnPost;
    Button btnInfo;
    Button btnEntry;
    TextView txtTest;
    byte[] imageData;
    Uri selectedImageUri;
    public static ContestFragment newInstance() {
        return new ContestFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        // Firestore の参照を取得



        return inflater.inflate(R.layout.fragment_contest, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContestViewModel.class);

    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        // ボタンのクリックリスナーを設定
        Button addButton = view.findViewById(R.id.sample);
        sampleText=view.findViewById(R.id.textView38);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });




        TextView txt= view.findViewById(R.id.textView25);



        btnView = view.findViewById(R.id.btnContestView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_view);

            }
        });



        btnPost = view.findViewById(R.id.btnContestPost);
        popupText = view.findViewById(R.id.popupText);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_post);
//                btnPost.setEnabled(false);
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.fragment_contest_post);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 700; // 幅を変更
                params.height = 1200; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnContestTopBack);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfo= view.findViewById(R.id.btnContestInfo);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.fragment_contest_info);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 700; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnContestTopBack);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnEntry=view.findViewById(R.id.btnContestEntry);
//        txtTest=view.findViewById(R.id.txtTest);
        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent, PICK_IMAGE_REQUEST);



                btnEntry.setEnabled(false);
                btnPost.setEnabled(true);
                showPopup();
            }
        });



    }
    private void showPopup() {
        // ポップアップ表示
        popupText.setVisibility(View.VISIBLE);

        // 3 秒後にポップアップを非表示にする
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupText.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }



}