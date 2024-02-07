package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageView;

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class iconFragment extends Fragment {

    private IconViewModel mViewModel;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri selectedImageUri;
    private FirebaseAuth mAuth;
    String userId;
    public static iconFragment newInstance() {
        return new iconFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icon, container, false);
        mAuth = FirebaseAuth.getInstance();
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

            ImageView imageView = getView().findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(IconViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btncan = view.findViewById(R.id.btncan);

        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate((R.id.action_navigation_icon_to_navigation_setting));
            }
        });

        Button btnhe = view.findViewById(R.id.btnhe);
        btnhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(getView()).navigate(R.id.action_navigation_icon_to_navigation_setting);
                // 選択された画像がある場合のみ処理を実行
                if (selectedImageUri != null) {
                    // Firebase Storage へのアップロード処理
                    uploadImageToFirebaseStorage(selectedImageUri);
                }else {
                    Toast.makeText(requireContext(),"画像が選択されていません",Toast.LENGTH_SHORT).show();

                }
            }
        });
        ImageView iconNow = view.findViewById(R.id.imageView);
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                            // ドキュメントが見つかった場合、ID を取得
                                            userId = document1.getId();
                                            db.collection("users") // コレクション名
                                                    .document(userId) // ドキュメント名
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            if (documentSnapshot.exists()) {

                                                                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(documentSnapshot.getString("icon"));
                                                                try {

                                                                    final File localFile = File.createTempFile("images", "png");
                                                                    storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                                                        // ローカルファイルから画像を読み込んで ImageView にセット
                                                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                                        iconNow.setImageBitmap(bitmap);

                                                                    }).addOnFailureListener(exception -> {
                                                                        // 失敗時の処理

                                                                    });

                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // エラーが発生した場合の処理
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

        // ... 他の処理を追加

    }

    // Firebase Storage へ画像をアップロードするメソッド
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String storagePath = "images/";

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            String userUid = currentUser.getUid();
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                CollectionReference collectionRefId = db.collection("userId");
                collectionRefId.whereEqualTo("uid", userUid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task1) {

                                if (task1.isSuccessful()) {
                                    for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                        // ドキュメントが見つかった場合、ID を取得
                                        userId = document1.getId();
                                        StorageReference storageRef = storage.getReference().child(storagePath + userId + ".png");

                                        storageRef.putFile(imageUri)
                                                .addOnSuccessListener(taskSnapshot -> {
                                                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                                String downloadUrl = uri.toString();
                                                                // Firebase Firestore にダウンロード URL を保存するメソッド
                                                                saveImageDownloadUrlToFirestore(downloadUrl);
                                                            })
                                                            .addOnFailureListener(exception -> {
                                                                // アップロードが失敗した場合の処理
                                                                exception.printStackTrace();
                                                                Toast.makeText(requireContext(), "画像のアップロードに失敗しました", Toast.LENGTH_SHORT).show();
                                                            });
                                                })
                                                .addOnFailureListener(exception -> {
                                                    // アップロードが失敗した場合の処理
                                                    exception.printStackTrace();
                                                    Toast.makeText(requireContext(), "画像のアップロードに失敗しました", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            }
                        });
            });

        }
    }

    // Firebase Firestore へダウンロード URL を保存するメソッド
    private void saveImageDownloadUrlToFirestore(String downloadUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DocumentReference userDocRef = db.collection("users").document(userId);

            // ダウンロード URL を Firestore に保存
            userDocRef.update("icon", downloadUrl)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(requireContext(), "画像の更新が完了しました", Toast.LENGTH_SHORT).show();
                        // 画像変更成功の場合、前の画面に戻る
                        Navigation.findNavController(getView()).navigateUp();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "画像の更新に失敗しました", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        }
    }
}


    // Firebase Firestore へダウンロード URL を保存するメソッド
//    private void saveImageDownloadUrlToFirestore(String downloadUrl) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//
//        if (currentUser != null) {
//            String userId = currentUser.getUid();
//            DocumentReference userDocRef = db.collection("users").document(userId);
//
//            // ダウンロード URL を Firestore に保存
//            userDocRef.update("icon", downloadUrl)  // フィールド名は適切なものに変更すること
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(requireContext(), "画像の更新が完了しました", Toast.LENGTH_SHORT).show();
//                        // 画像変更成功の場合、前の画面に戻る
//                        Navigation.findNavController(getView()).navigateUp();
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(requireContext(), "画像の更新に失敗しました", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    });
//        }
//    }
//}