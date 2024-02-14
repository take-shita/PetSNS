package com.example.petsns.ui.contest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Contest_rankingFragment extends Fragment {

    private FirebaseFirestore firestore;
    String document1=null;
    String document2=null;
    String document3=null;

    String imageUrl1=null;
    String imageUrl2=null;
    String imageUrl3=null;

    int likeCount1;
    int likeCount2;
    int likeCount3;

    private FirebaseFirestore db;
    String userId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contest_ranking, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnBack = view.findViewById(R.id.btnContestTopBack);
        ImageView rank1 = view.findViewById(R.id.imagePost1);
        ImageView rank2 = view.findViewById(R.id.imagePost2);
        ImageView rank3 = view.findViewById(R.id.imagePost3);

        likeCount1 = 0;
        likeCount2 = 0;
        likeCount3 = 0;

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("contestPosts").orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> data = document.getData();
                    String documentId = document.getId();

                    String imageUrl = (String) data.get("imageUrl");
                    int likeCount = ((Number) data.get("likeCount")).intValue();

                    if (likeCount1 < likeCount) {
                        likeCount2 = likeCount1;
                        document2 = document1;
                        imageUrl2 = imageUrl1;

                        likeCount1 = likeCount;
                        document1 = documentId;
                        imageUrl1 = imageUrl;


                    } else if (likeCount2 < likeCount) {
                        likeCount3 = likeCount2;
                        document3 = document2;
                        imageUrl3 = imageUrl2;

                        likeCount2 = likeCount;
                        document2 = documentId;
                        imageUrl2 = imageUrl;
                    } else if (likeCount3 < likeCount) {
                        likeCount3 = likeCount;
                        document3 = documentId;
                        imageUrl3 = imageUrl;
                    }
                }

                if (imageUrl1 != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl1);
                    try {
                        final File localFile = File.createTempFile("images", "jpg");
                        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                            // ローカルファイルから画像を読み込んで ImageView にセット
                            if (rank1 != null) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                rank1.setImageBitmap(bitmap);

                            } else {
                                // holder.imagePostがnullの場合の処理（ログなど）
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imageUrl2 != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl2);
                    try {
                        final File localFile = File.createTempFile("images", "jpg");
                        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                            // ローカルファイルから画像を読み込んで ImageView にセット
                            if (rank2 != null) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                rank2.setImageBitmap(bitmap);
                            } else {
                                // holder.imagePostがnullの場合の処理（ログなど）
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imageUrl3 != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl3);
                    try {
                        final File localFile = File.createTempFile("images", "jpg");
                        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                            // ローカルファイルから画像を読み込んで ImageView にセット
                            if (rank3 != null) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                rank3.setImageBitmap(bitmap);
                            } else {
                                // holder.imagePostがnullの場合の処理（ログなど）
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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

                                    firestore = FirebaseFirestore.getInstance();
                                    firestore.collection("contestPosts").orderBy("likeCount", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> data = document.getData();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
        });

        rank1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogInfo("1",imageUrl1,likeCount1);
            }
        });
        rank2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo("2",imageUrl2,likeCount2);
            }
        });
        rank3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo("3",imageUrl3,likeCount3);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_ranking_to_navigation_contest);
            }
        });


    }

    public void dialogInfo(String number,String imageUrl,int likeCount){
        Context context = requireContext();
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.fragment_contest_rank_info);
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 600; // 幅を変更
        params.height = 650; // 高さを変更
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        ImageView imagePost=dialog.findViewById(R.id.imagePost);
        Button btnClose=dialog.findViewById(R.id.btnClose);
        TextView txtNumber=dialog.findViewById(R.id.txtNumber);
        TextView txtLikeCount=dialog.findViewById(R.id.txtLikeCount);

        txtLikeCount.setText(Integer.toString(likeCount));
        txtNumber.setText(number+".");
        if(imageUrl!=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
            try {
                final File localFile = File.createTempFile("images", "jpg");
                storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    if (imagePost != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        imagePost.setImageBitmap(bitmap);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
