package com.example.petsns;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;

import android.widget.TextView;

import com.example.petsns.ui.setting.TagViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Signup3 extends AppCompatActivity {
    private SigupViewModel viewModel;
    private TagViewModel tagViewModel;
    private FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        Button btnSb=findViewById(R.id.btnSubmit);


        TextView txtId=findViewById(R.id.textId);
        TextView txtName=findViewById(R.id.textName);
        TextView txtMail=findViewById(R.id.textMail);
        TextView txtFv=findViewById(R.id.textFavorite);
        db = FirebaseFirestore.getInstance();

        MyApplication myApplication = (MyApplication) getApplication();

        // MyApplicationの初期化が行われていることを確認する
        if (myApplication != null) {
            viewModel = myApplication.getSharedViewModel();
            tagViewModel=myApplication.getSharedTagViewModel();
        } else {
            // エラーハンドリング
        }

        txtId.setText(viewModel.getUserId());
        txtName.setText(viewModel.getUserName());
        txtMail.setText(viewModel.getEmail());
        txtFv.setText(tagViewModel.getLikemom());

        btnSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(viewModel.getEmail(), viewModel.getPassword())
                            .addOnCompleteListener(task -> {


                                if (task.isSuccessful()) {
                                    // ユーザー作成成功
                                    FirebaseUser user = task.getResult().getUser();
                                    String uid = user.getUid();//id取得


                                    DocumentReference documentRef = db.collection("users").document(viewModel.getUserId());
                                    DocumentReference documentRefUid=db.collection("userId").document(viewModel.getUserId());
                                    // アカウント情報のデータ
                                    Map<String, Object> accountData = new HashMap<>();
                                    Map<String, Object> accountDataUid =new HashMap<>();
                                    accountData.put("icon","https://firebasestorage.googleapis.com/v0/b/sample-eaf65.appspot.com/o/icon%2Fdefault_icon.png?alt=media&token=b500b036-ed2f-4d2a-837e-efe91c107d53");
                                    accountData.put("id", viewModel.getUserId());
                                    accountData.put("name", viewModel.getUserName());
                                    accountData.put("mail", viewModel.getEmail());
                                    accountData.put("password", viewModel.getPassword());
                                    accountData.put("likeMom", tagViewModel.getArraylikeMom());
                                    accountData.put("likeBir", tagViewModel.getArraylikeBir());
                                    accountData.put("likeRip", tagViewModel.getArraylikeRip());
                                    accountData.put("likeBis", tagViewModel.getArraylikeBis());
                                    accountData.put("likeAqua", tagViewModel.getArraylikeAqua());
                                    accountData.put("likeIns", tagViewModel.getArraylikeIns());
                                    accountData.put("DisMom", tagViewModel.getArrayDislikeMom());
                                    accountData.put("DisBir", tagViewModel.getArrayDislikeBir());
                                    accountData.put("DisRip", tagViewModel.getArrayDislikeRip());
                                    accountData.put("DisBis", tagViewModel.getArrayDislikeBis());
                                    accountData.put("DisAqua", tagViewModel.getArrayDislikeAqua());
                                    accountData.put("DisIns", tagViewModel.getArrayDislikeIns());
                                    accountData.put("follow",new ArrayList<>());
                                    accountData.put("contestEntry",false);
                                    accountData.put("contestPost",false);
                                    accountData.put("iiePostId",new ArrayList<>());
                                    documentRef.set(accountData)
                                            .addOnSuccessListener(aVoid -> {

                                                accountDataUid.put("uid",uid);
                                                documentRefUid.set(accountDataUid)
                                                        .addOnSuccessListener(aVoid2 ->{
                                                            Context context = v.getContext();
                                                            Intent intent = new Intent(context, MainActivity.class);
                                                            startActivity(intent);
                                                        });

                                            });




                                } else {

                                }
                            });
            }
        });
        Button btnBack=findViewById(R.id.button);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, Signup.class);
                startActivity(intent);
            }
        });
    }
}