package com.example.petsns.ui.contest;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.TagSearchViewModel;
import com.example.petsns.ui.profile.OtherPostAdapter;
import com.example.petsns.ui.profile.Profile_TestPost;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class ContestViewFragment extends Fragment {

    private TagSearchViewModel viewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private ContestPostAdapter postAdapter;
    private static final String TAG = "YourClassName";
    private FirebaseFirestore db;
    List<Boolean> LikeMom;
    List<Boolean> LikeRip ;
    List<Boolean> LikeBir ;
    List<Boolean> LikeBis ;
    List<Boolean> LikeAqua ;
    List<Boolean> LikeIns;

    List<Boolean> DisMom;
    List<Boolean> DisRip;
    List<Boolean> DisBir ;
    List<Boolean> DisBis ;
    List<Boolean> DisAqua ;
    List<Boolean> DisIns;

    String userId;
    public static ContestViewFragment newInstance() {
        return new ContestViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contest_view, container, false);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagSearchViewModel();
        } else {
            // エラーハンドリング
        }

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new ContestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);

        // Firestore からデータを取得して表示
        fetchDataFromFirestore();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBack = view.findViewById(R.id.btnContestTopBack);
        ImageButton search_btn = view.findViewById(R.id.search_tag);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("key", true);
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_view_to_navigation_tag_search,bundle);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_view_to_navigation_contest);
            }
        });



    }

    private void fetchDataFromFirestore() {



        // Firestore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("contestPosts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ContestItemPost> posts = new ArrayList<>();

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

                                                            docRef.get().addOnSuccessListener(documentSnapshot -> {
                                                                if (documentSnapshot.exists()) {
//                                エラー直すために勝手に追加
                                                                    LikeMom = getListFromDocument(documentSnapshot, "likeMom");
                                                                    LikeRip = getListFromDocument(documentSnapshot, "likeRip");
                                                                    LikeBir = getListFromDocument(documentSnapshot, "LikeBir");
                                                                    LikeBis = getListFromDocument(documentSnapshot, "LikeBis");
                                                                    LikeAqua = getListFromDocument(documentSnapshot, "LikeAqua");
                                                                    LikeIns = getListFromDocument(documentSnapshot, "LikeIns");

                                                                    DisMom = getListFromDocument(documentSnapshot, "DisMom");
                                                                    DisRip = getListFromDocument(documentSnapshot, "DisRip");
                                                                    DisBir = getListFromDocument(documentSnapshot, "DisBir");
                                                                    DisBis = getListFromDocument(documentSnapshot, "DisBis");
                                                                    DisAqua = getListFromDocument(documentSnapshot, "DisAqua");
                                                                    DisIns = getListFromDocument(documentSnapshot, "DisIns");
                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                        boolean check = true;
                                                                        boolean like = false;
                                                                        boolean tagSearch=false;
                                                                        double random = 0;
                                                                        Map<String, Object> data = document.getData();
                                                                        String documentId = document.getId();
                                                                        Log.d(TAG, "Data from Firestore: " + data.toString());


                                                                        ContestItemPost post = document.toObject(ContestItemPost.class);

                                                                        List<Boolean> tagMom = (List<Boolean>) data.get("tagMom");
                                                                        List<Boolean> tagBir = (List<Boolean>) data.get("tagBir");
                                                                        List<Boolean> tagRip = (List<Boolean>) data.get("tagRip");
                                                                        List<Boolean> tagBis = (List<Boolean>) data.get("tagBis");
                                                                        List<Boolean> tagAqua = (List<Boolean>) data.get("tagAqua");
                                                                        List<Boolean> tagIns = (List<Boolean>) data.get("tagIns");
                                                                        Number likeCountDouble = ((Number) data.get("likeCount"));
                                                                        //                                    エラー直すために勝手に追加
                                                                        List<Boolean> likeMomList = getListFromDocument(documentSnapshot, "likeMom");
                                                                        List<Boolean> likeBirList = getListFromDocument(documentSnapshot, "likeBir");
                                                                        List<Boolean> likeRipList = getListFromDocument(documentSnapshot, "likeRip");
                                                                        List<Boolean> likeBisList = getListFromDocument(documentSnapshot, "likeBis");
                                                                        List<Boolean> likeAquaList = getListFromDocument(documentSnapshot, "likeAqua");
                                                                        List<Boolean> likeInsList = getListFromDocument(documentSnapshot, "likeIns");
//                                    エラー直すために勝手に追加
                                                                        for (int i = 0; i < tagMom.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagMom.size() > i && tagMom.get(i)) {
                                                                                if (!likeMomList.isEmpty() && likeMomList.size() > i && likeMomList.get(i) ) {
                                                                                    like = true;
                                                                                }
                                                                                //エラー直すために勝手に追加
                                                                                if (!DisMom.isEmpty() && DisMom.size() > i && DisMom.get(i)) {
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        for (int i = 0; i < tagBir.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagBir.size() > i && tagBir.get(i)) {
                                                                                if (!likeBirList.isEmpty() && likeBirList.size() > i && likeBirList.get(i)) {
                                                                                    like = true;
                                                                                }
                                                                                //エラー直すために勝手に追加
                                                                                if (!DisBir.isEmpty() && DisBir.size() > i && DisBir.get(i)) {
                                                                                    check = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        for (int i = 0; i < tagRip.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagRip.size() > i && tagRip.get(i)) {
                                                                                if (!likeRipList.isEmpty() && likeRipList.size() > i && likeRipList.get(i)) {
                                                                                    like = true;
                                                                                }
                                                                                //エラー直すために勝手に追加
                                                                                if (!DisRip.isEmpty() && DisRip.size() > i && DisRip.get(i)) {
                                                                                    check = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        for (int i = 0; i < tagBis.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagBis.size() > i && tagBis.get(i)) {
                                                                                if (!likeBisList.isEmpty() && likeBisList.size() > i && likeBisList.get(i)) {
                                                                                    like = true;
                                                                                }
                                                                                if (!DisBis.isEmpty() && DisBis.size() > i && DisBis.get(i)) {
                                                                                    check = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        for (int i = 0; i < tagAqua.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagAqua.size() > i && tagAqua.get(i)) {
                                                                                if (!likeAquaList.isEmpty() && likeAquaList.size() > i && likeAquaList.get(i)) {
                                                                                    like = true;
                                                                                }
                                                                                //エラー直すために勝手に追加
                                                                                if (!DisAqua.isEmpty() && DisAqua.size() > i && DisAqua.get(i)) {
                                                                                    check = false;

                                                                                    break;
                                                                                }
                                                                            }
                                                                        }
                                                                        for (int i = 0; i < tagIns.size(); i++) {
                                                                            //エラー直すために勝手に追加
                                                                            if (tagIns.size() > i && tagIns.get(i)) {
                                                                                if (!likeInsList.isEmpty() && likeInsList.size() > i && likeInsList.get(i)) {
                                                                                    like = true;
                                                                                }
                                                                                //エラー直すために勝手に追加
                                                                                if (!DisIns.isEmpty() && DisIns.size() > i && DisIns.get(i)) {
//エラー直すために勝手に追加
                                                                                    check = false;
                                                                                    break;
                                                                                }
                                                                            }
                                                                        }


                                                                        if (check) {

                                                                            if(viewModel.getCheck()) {

                                                                                for (int i = 0; i < tagMom.size(); i++) {
                                                                                    if (tagMom.get(i)) {
                                                                                        if (viewModel.getArraylikeMom().get(i)) {
                                                                                            tagSearch=true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for (int i = 0; i < tagBir.size(); i++) {
                                                                                    if (tagBir.get(i)) {
                                                                                        if (viewModel.getArraylikeBir().get(i)) {
                                                                                            tagSearch = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for (int i = 0; i < tagRip.size(); i++) {
                                                                                    if (tagRip.get(i)) {
                                                                                        if (viewModel.getArraylikeRip().get(i)) {
                                                                                            tagSearch = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for (int i = 0; i < tagBis.size(); i++) {
                                                                                    if (tagBis.get(i)) {
                                                                                        if (viewModel.getArraylikeBis().get(i)) {
                                                                                            tagSearch = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for (int i = 0; i < tagAqua.size(); i++) {
                                                                                    if (tagAqua.get(i)) {
                                                                                        if (viewModel.getArraylikeAqua().get(i)) {
                                                                                            tagSearch = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                for (int i = 0; i < tagIns.size(); i++) {
                                                                                    if (tagIns.get(i)) {
                                                                                        if (viewModel.getArraylikeIns().get(i)) {
                                                                                            tagSearch = true;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                if(tagSearch){
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
                                                                                }else{

                                                                                }
                                                                            }else {
                                                                                if (!like) {
                                                                                    random = Math.random();
                                                                                }
                                                                                if (like || random < 1) {


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
                                                                        }
                                                                    }
                                                                    postAdapter.setPosts(posts);
                                                                } else {

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
                });
    }
    private List<Boolean> getListFromDocument(DocumentSnapshot documentSnapshot, String fieldName) {
        List<Boolean> list = (List<Boolean>) documentSnapshot.get(fieldName);
        return list != null ? list : new ArrayList<>();
    }

}