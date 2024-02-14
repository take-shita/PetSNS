package com.example.petsns.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.ProfileIinepostViewModel;
import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class profile_iinepostFragment extends Fragment {

    private ProfileIinepostViewModel mViewModel;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;
    private String userId;
    private OtherPostAdapter postAdapter; // PostViewHolderのインスタンスをメンバ変数として宣言
    private List<Profile_TestPost> postList = new ArrayList<>();
    private RecyclerView recyclerView; // recyclerView をフィールド変数として定義

    public static profile_iinepostFragment newInstance() {
        return new profile_iinepostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_iinepost, container, false);
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
                                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    if (documentSnapshot.exists()) {
                                                        // ユーザーのドキュメントが存在する場合の処理
                                                        List<String> postList = (List<String>) documentSnapshot.get("iinePostId");

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

                                                            if(postList.contains(documentId)){

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
                }            }
        });
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileIinepostViewModel.class);
        // TODO: Use the ViewModel
    }

}