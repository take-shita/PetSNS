package com.example.petsns.ui.profile;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.ProfilePostViewModel;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class profile_postFragment extends Fragment {

    private ProfilePostViewModel mViewModel;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;
    private String userId;
    private OtherPostAdapter postAdapter; // PostViewHolderのインスタンスをメンバ変数として宣言
    private List<Profile_TestPost> postList = new ArrayList<>();
    private RecyclerView recyclerView; // recyclerView をフィールド変数として定義





    public static profile_postFragment newInstance() {
        return new profile_postFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_post, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        try {
            postAdapter = new OtherPostAdapter(requireContext());
            recyclerView.setAdapter(postAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }catch (Exception e){
            e.printStackTrace();
        }

        fetchDataFromFirestore();

        return rootView;
    }


    public void fetchDataFromFirestore() {
        // FireStore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts").orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Profile_TestPost> posts = new ArrayList<>();

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

                                            DocumentReference docRef = db.collection("users").document(userId);


                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Profile_TestPost post = document.toObject(Profile_TestPost.class);  // クラスの型もProfile_TestPostに変更

                                                Map<String, Object> data = document.getData();
                                                String documentId = document.getId();

                                                List<Boolean> tagMom = (List<Boolean>) data.get("tagMom");
                                                List<Boolean> tagBir = (List<Boolean>) data.get("tagBir");
                                                List<Boolean> tagRip = (List<Boolean>) data.get("tagRip");
                                                List<Boolean> tagBis = (List<Boolean>) data.get("tagBis");
                                                List<Boolean> tagAqua = (List<Boolean>) data.get("tagAqua");
                                                List<Boolean> tagIns = (List<Boolean>) data.get("tagIns");
                                                Number likeCountDouble = ((Number) data.get("likeCount"));

                                                if(((String)data.get("id")).equals(userId)){

                                                    post.setId((String) data.get("id"));
                                                    post.setSentence((String) data.get("sentence"));
                                                    post.setImageUrl((String) data.get("imageUrl"));
                                                    post.setDocumentId(documentId);
                                                    post.setLikeCount(likeCountDouble.intValue());
                                                    post.setTagMom(tagMom);
                                                    post.setTagBir(tagBir);
                                                    post.setTagRip(tagRip);
                                                    post.setTagBis(tagBis);
                                                    post.setTagAqua(tagAqua);
                                                    post.setTagIns(tagIns);

                                                    posts.add(post);
                                                }

                                            }
                                            postAdapter.setPosts(posts);



                                        }
                                    }
                                }
                            });
                });
                try {
                    future1.get(); // 非同期処理が終わるまでブロック

                } catch (InterruptedException | ExecutionException e) {
                    // 例外処理
                }            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfilePostViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView prof_bt = view.findViewById(R.id.profileicon);
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


                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                                                prof_bt.setImageBitmap(bitmap);

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


    }


}