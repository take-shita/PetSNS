package com.example.petsns.ui.contest;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.TagPostViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ContestPostFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db;
    private TagPostViewModel viewModel;

    Uri selectedImageUri;
    String userId;
    public static ContestPostFragment newInstance() {
        return new ContestPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contest_post, container, false);
        view.findViewById(R.id.selectImageBtn).setOnClickListener(v -> pickImage());
        return view;
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getView() != null && requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            // ここで取得したURIを使用して画像を表示または処理する
            // 例: ImageViewに画像を表示
            ImageView imageView = getView().findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtTag=view.findViewById(R.id.tagText);
        db = FirebaseFirestore.getInstance();
        Button btnBack = view.findViewById(R.id.cancel_btn);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagPostViewModel();
        } else {
            // エラーハンドリング
        }
        if(viewModel.getTagNameAll()!=null){
            txtTag.setText(viewModel.getTagNameAll());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.tagCancel();
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_post_to_navigation_contest);
            }
        });

        Button post = view.findViewById(R.id.post_btn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String userUid = user.getUid();


                if(selectedImageUri!=null){
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

                                                CollectionReference postCollection = db.collection("contestPosts");

                                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                                StorageReference storageRef = storage.getReference().child("contestPost");

                                                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                                                String imageFileName = "image_" + timestamp + ".jpg";

                                                /* 画像のUriを取得するコード */;
                                                storageRef.child(imageFileName).putFile(selectedImageUri)
                                                        .addOnSuccessListener(taskSnapshot -> {

                                                            storageRef.child(imageFileName).getDownloadUrl()

                                                                    .addOnSuccessListener(uri -> {

                                                                        // Firestoreにドキュメントを作成してURLを保存
                                                                        Map<String, Object> data = new HashMap<>();
                                                                        data.put("id",userId);
                                                                        data.put("imageUrl", uri.toString());
                                                                        data.put("tagMom",viewModel.getArraylikeMom());
                                                                        data.put("tagBir",viewModel.getArraylikeBir());
                                                                        data.put("tagRip",viewModel.getArraylikeRip());
                                                                        data.put("tagBis",viewModel.getArraylikeBis());
                                                                        data.put("tagAqua",viewModel.getArraylikeAqua());
                                                                        data.put("tagIns",viewModel.getArraylikeIns());
                                                                        data.put("likeCount",0);
                                                                        data.put("timestamp", FieldValue.serverTimestamp());
                                                                        // Firestoreにドキュメントを作成
                                                                        postCollection.document(UUID.randomUUID().toString()).set(data)
                                                                                .addOnSuccessListener(documentReference -> {
                                                                                    // documentReference.getId() で作成されたドキュメントのIDを取得できます

                                                                                    DocumentReference docRef=db.collection("users").document(userId);

                                                                                    Map<String,Object> updates=new HashMap<>();
                                                                                    updates.put("contestPost",true);

                                                                                    docRef.update(updates)
                                                                                            .addOnSuccessListener(
                                                                                                    new OnSuccessListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Void unused) {
                                                                                                            Navigation.findNavController(v).navigate(R.id.action_navigation_contest_post_to_navigation_contest);
                                                                                                        }
                                                                                                    })
                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {

                                                                                                }
                                                                                            });
                                                                                })
                                                                                .addOnFailureListener(e -> {

                                                                                });
                                                                    })

                                                                    .addOnFailureListener(e -> {
                                                                        // ダウンロードURLの取得が失敗した場合の処理
                                                                    });
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
                }

//                DocumentReference docRef = db.collection("post").document(userId);



            }
        });

        ImageButton tag_post = view.findViewById(R.id.tag_btn);
        tag_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("key", true);

                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_post_to_navigation_tag_post,bundle);
            }
        });

    }

}