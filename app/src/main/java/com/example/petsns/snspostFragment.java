package com.example.petsns;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class snspostFragment extends Fragment {

    private SnspostViewModel mViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db;
    private TagPostViewModel viewModel;

    Uri selectedImageUri;

    public static snspostFragment newInstance() {
        return new snspostFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snspost, container, false);
        // 画像選択ボタンのクリックリスナー
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
        mViewModel = new ViewModelProvider(this).get(SnspostViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtTag=view.findViewById(R.id.tagText);
        EditText sentene=view.findViewById(R.id.editTextTextMultiLine);
        db = FirebaseFirestore.getInstance();
        Button back = view.findViewById(R.id.cancel_btn);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagPostViewModel();
        } else {
            // エラーハンドリング
        }

        if(viewModel.getLikemom()!=null){
            txtTag.setText(viewModel.getLikemom());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snspost_to_navigation_snstop);
            }
        });

        Button post = view.findViewById(R.id.post_btn);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String userId = user.getUid();

//                DocumentReference docRef = db.collection("post").document(userId);
                CollectionReference postCollection = db.collection("posts");

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference().child("post");

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
                                        data.put("sentence",sentene.getText().toString());
                                        data.put("imageUrl", uri.toString());
                                        data.put("tag",viewModel.getArraylikeMom());
                                        data.put("timestamp", FieldValue.serverTimestamp());
                                        // Firestoreにドキュメントを作成
                                        postCollection.document(UUID.randomUUID().toString()).set(data)
                                                .addOnSuccessListener(documentReference -> {
                                                    // documentReference.getId() で作成されたドキュメントのIDを取得できます
                                                })
                                                .addOnFailureListener(e -> {

                                                });
                                    })

                                    .addOnFailureListener(e -> {
                                        // ダウンロードURLの取得が失敗した場合の処理
                                    });
                        });


                Navigation.findNavController(v).navigate(R.id.action_navigation_snspost_to_navigation_snstop);
            }
        });

        ImageButton tag_post = view.findViewById(R.id.tag_btn);
        tag_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snspost_to_navigation_tag_post);
            }
        });

    }
}