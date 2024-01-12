package com.example.petsns.ui.contest;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import  com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ContestFragment extends Fragment {

    private ContestViewModel mViewModel;
    private TextView popupText;

    private TextView sampleText;
    Button btnView;
    Button btnPost;
    Button btnInfo;
    Button btnEntry;
    TextView txtTest;

    public static ContestFragment newInstance() {
        return new ContestFragment();
    }


    private FirebaseFirestore db;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        // Firestoreの参照を取得



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

                addAccountInfo();
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
        txtTest=view.findViewById(R.id.txtTest);
        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                DocumentReference docRef = db.collection("users").document(userId);

                docRef.update("contestEntry", true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // 成功時の処理
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 失敗時の処理
                            }
                        });


                btnEntry.setEnabled(false);
                btnPost.setEnabled(true);
                showPopup();
            }
        });



    }
    private void showPopup() {
        // ポップアップ表示
        popupText.setVisibility(View.VISIBLE);

        // 3秒後にポップアップを非表示にする
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupText.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }



    public void addAccountInfo() {

        CollectionReference collectionRef = db.collection("sample01");
        collectionRef.orderBy("account_name", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {

                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        String documentId = documentSnapshot.getId();
                        int intDocumentID = Integer.parseInt(documentId)+1;
                        String nextDocumentID=Integer.toString(intDocumentID);

                        DocumentReference documentRef = db.collection("sample01").document(nextDocumentID);
                        // アカウント情報のデータ
                        Map<String, Object> accountData = new HashMap<>();
                        accountData.put("account_name", "example_user");
                        accountData.put("password", "password123");
                        documentRef.set(accountData)
                                .addOnSuccessListener(aVoid -> {
                                    sampleText.setText(nextDocumentID+"を追加しました");
                                })
                                .addOnFailureListener(e -> {
                                    sampleText.setText("追加失敗");
                                });

                        // フィールドの取得
//                        String fieldName = documentSnapshot.getString("account_name");

                        // ここで取得した date を使用できます

                    }
                })
                .addOnFailureListener(e -> {
                    // 失敗時の処理
                    sampleText.setText("一番新しいの参照できません");
                });

    }


}