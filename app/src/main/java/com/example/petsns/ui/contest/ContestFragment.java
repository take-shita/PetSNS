package com.example.petsns.ui.contest;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.petsns.R;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ContestFragment extends Fragment {
    private TextView sampleText;
    private FirebaseFirestore db;
    private TextView popupText;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnView;
    Button btnPost;
    Button btnInfo;
    Button btnEntry;
    Button btnresult;
    TextView txtTest;
    Boolean contestEntry;
    Boolean contestPost;
    byte[] imageData;
    Uri selectedImageUri;
    String userId;

    Date entrySt;
    Date entryFn;
    Date postSt;
    Date postFn;
    Date resultSt;
    Date resultFn;

    Date currentDate;
    String info;
    String title;
    public static ContestFragment newInstance() {
        return new ContestFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        db = FirebaseFirestore.getInstance();
        // Firestore の参照を取得
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        String userUid = user.getUid();


        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                    CollectionReference collectionRefId = db.collection("userId");
                    collectionRefId.whereEqualTo("uid", userUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task1) {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            // ドキュメントが見つかった場合、IDを取得
                                            userId = document1.getId();
                                            db.collection("users")
                                                    .document(userId)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (document.exists()) {
                                                                    // ドキュメントが存在する場合
                                                                    Boolean contestEntry = document.getBoolean("contestEntry");
                                                                    Boolean contestPost=document.getBoolean("contestPost");

                                                                    if(!contestEntry){

                                                                    }else {
                                                                        btnEntry.setEnabled(false);
                                                                    }
                                                                    if(!contestPost&&contestEntry){

                                                                    }else {
                                                                        btnPost.setEnabled(false);
                                                                    }
                                                                    // fieldValueには指定したフィールドの値が含まれる
                                                                } else {
                                                                    // ドキュメントが存在しない場合
                                                                }
                                                            } else {
                                                                // 取得に失敗した場合
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                }
                            });
                });
        try {
            future1.get(); // 非同期処理が終わるまでブロック
        } catch (InterruptedException | ExecutionException e) {
            // 例外処理
        }

        Long currentTime=System.currentTimeMillis();
        currentDate=new Date(currentTime);

        db.collection("contestInfo")
                .document("ZGNHbqF7tAj3givX3bFB")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                entrySt= document.getDate("entryStart");
                                entryFn=document.getDate("entryFinish");
                                postSt=document.getDate("postStart");
                                postFn=document.getDate("postFinish");
                                resultSt=document.getDate("resultStart");
                                resultFn=document.getDate("resultFinish");
                                title=document.getString("info");
                                info=document.getString("info");

                                if(!currentDate.after(entrySt)){
                                    btnEntry.setEnabled(false);
                                }else if (!currentDate.before(entryFn)){
                                    btnEntry.setEnabled(false);
                                }

                                if(!currentDate.after(postSt)){
                                    btnPost.setEnabled(false);
                                }else if(!currentDate.before(entryFn)){
                                    btnPost.setEnabled(false);
                                }

                                if(!currentDate.after(resultSt)){
                                    btnresult.setVisibility(View.INVISIBLE);
                                }else if(!currentDate.before(resultFn)){
                                    btnresult.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                });


        return inflater.inflate(R.layout.fragment_contest, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }




    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnresult = view.findViewById(R.id.btnresult);
        btnresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_ranking);
            }
        });

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
//                btnPost.setEnabled(false);
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_post);
            }
        });

        btnInfo= view.findViewById(R.id.btnContestInfo);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.fragment_contest_info);

                TextView txtEntBefore=dialog.findViewById(R.id.txtEntryBefore);
                TextView txtEntAfter=dialog.findViewById(R.id.txtEntryAfter);
                TextView txtPostBefore=dialog.findViewById(R.id.txtPostBefore);
                TextView txtPostAfter=dialog.findViewById(R.id.txtPostAfter);

                TextView txtTitle=dialog.findViewById(R.id.txtTitle);
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 700; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));

                String entryStDate = sdf.format(entrySt);
                String entryFnDate = sdf.format(entryFn);
                String postStDate = sdf.format(postSt);
                String postFnDate = sdf.format(postFn);

                txtEntBefore.setText(entryStDate);
                txtEntAfter.setText(entryFnDate);
                txtPostBefore.setText(postStDate);
                txtPostAfter.setText(postFnDate);

                txtTitle.setText(title);
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




                db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = user.getUid();


                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                    CollectionReference collectionRefId = db.collection("userId");
                    collectionRefId.whereEqualTo("uid", userUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task1) {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            // ドキュメントが見つかった場合、IDを取得
                                            userId = document1.getId();
                                        }
                                    }
                                }
                            });
                });
                try {
                    future1.get(); // 非同期処理が終わるまでブロック
                } catch (InterruptedException | ExecutionException e) {
                    // 例外処理
                }

                DocumentReference docRef=db.collection("users").document(userId);

                Map<String,Object> updates=new HashMap<>();
                updates.put("contestEntry",true);

                docRef.update(updates)
                        .addOnSuccessListener(
                                new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        btnEntry.setEnabled(false);
                                        if(currentDate.after(postSt)){
                                            if(currentDate.before(entryFn)){
                                                btnPost.setEnabled(true);
                                            }
                                        }

                                        showPopup();
                                    }
                                })
                        .addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {

                                                  }
                                              });

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