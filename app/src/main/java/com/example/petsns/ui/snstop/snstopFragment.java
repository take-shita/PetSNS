package com.example.petsns.ui.snstop;

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

import com.example.petsns.MyApplication;
import com.example.petsns.R;
import com.example.petsns.TagSearchViewModel;
import com.example.petsns.TestPost;
import com.example.petsns.TestPostAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class snstopFragment extends Fragment {

    private TagSearchViewModel viewModel;
    private SnstopViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private TestPostAdapter postAdapter;
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
    public static snstopFragment newInstance() {
        return new snstopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_snstop, container, false);

        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedTagSearchViewModel();
        } else {
            // エラーハンドリング
        }

//        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);

        // Firestore からデータを取得して表示
        fetchDataFromFirestore();


//        ここまで

        return rootView;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SnstopViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ボタンのクリックリスナー
        ImageButton prof_bt = view.findViewById(R.id.top_prof);
        prof_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_profile);
            }
        });

        ImageButton search_btn = view.findViewById(R.id.search_tag);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("key", false);
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_tag_search,bundle);
            }
        });


        ImageButton post = view.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_snspost);
            }
        });



        //        ここまで
    }
    private void fetchDataFromFirestore() {



        // Firestore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<TestPost> posts = new ArrayList<>();

                        db = FirebaseFirestore.getInstance();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
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
//                                エラー直すために勝手に追加
//                                エラー直すために勝手にコメントアウト
//                                LikeMom = (List<Boolean>) documentSnapshot.get("likeMom");
//                                LikeRip = (List<Boolean>) documentSnapshot.get("likeRip");
//                                LikeBir = (List<Boolean>) documentSnapshot.get("likeBir");
//                                LikeBis = (List<Boolean>) documentSnapshot.get("likeBis");
//                                LikeAqua = (List<Boolean>) documentSnapshot.get("likeAqua");
//                                LikeIns = (List<Boolean>) documentSnapshot.get("likeIns");
//
//                                DisMom = (List<Boolean>) documentSnapshot.get("DisMom");
//                                DisRip = (List<Boolean>) documentSnapshot.get("DisRip");
//                                DisBir = (List<Boolean>) documentSnapshot.get("DisBir");
//                                DisBis = (List<Boolean>) documentSnapshot.get("DisBis");
//                                DisAqua = (List<Boolean>) documentSnapshot.get("DisAqua");
//                                DisIns = (List<Boolean>) documentSnapshot.get("DisIns");
//                                エラー直すために勝手にコメントアウト
                                // fieldValueを使用して何かを行う
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boolean check = true;
                                    boolean like = false;
                                    boolean tagSearch=false;
                                    double random = 0;
                                    Map<String, Object> data = document.getData();
                                    String documentId = document.getId();
                                    Log.d(TAG, "Data from Firestore: " + data.toString());


                                    TestPost post = document.toObject(TestPost.class);

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
                                            if (!likeMomList.isEmpty() && likeMomList.size() > i && likeMomList.get(i) != null) {
//エラー直すために勝手に追加
//                                            エラー直すために勝手にコメントアウト
//                                                if (tagMom.get(i)) {
//                                            if (LikeMom.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisMom.isEmpty() && DisMom.size() > i && DisMom.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                                if (DisMom.get(i)) {
//                                                check = false;
//                                                エラー直すために勝手にコメントアウト
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagBir.size(); i++) {
                                        //エラー直すために勝手に追加
                                        if (tagBir.size() > i && tagBir.get(i)) {
                                            if (!likeBirList.isEmpty() && likeBirList.size() > i && likeBirList.get(i) != null) {
//エラー直すために勝手に追加
//                                        エラー直すために勝手にコメントアウト
//                                        if (tagBir.get(i)) {
//                                            if (LikeBir.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisBir.isEmpty() && DisBir.size() > i && DisBir.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                            if (DisBir.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagRip.size(); i++) {
                                        //エラー直すために勝手に追加
                                        if (tagRip.size() > i && tagRip.get(i)) {
                                            if (!likeRipList.isEmpty() && likeRipList.size() > i && likeRipList.get(i) != null) {
//エラー直すために勝手に追加
//                                        エラー直すために勝手にコメントアウト
//                                        if (tagRip.get(i)) {
//                                            if (LikeRip.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisRip.isEmpty() && DisRip.size() > i && DisRip.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                            if (DisRip.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagBis.size(); i++) {
                                        //エラー直すために勝手に追加
                                        if (tagBis.size() > i && tagBis.get(i)) {
                                            if (!likeBisList.isEmpty() && likeBisList.size() > i && likeBisList.get(i) != null) {
//エラー直すために勝手に追加
//                                        エラー直すために勝手にコメントアウト
//                                        if (tagBis.get(i)) {
//                                            if (LikeBis.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisBis.isEmpty() && DisBis.size() > i && DisBis.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                            if (DisBis.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagAqua.size(); i++) {
                                        //エラー直すために勝手に追加
                                        if (tagAqua.size() > i && tagAqua.get(i)) {
                                            if (!likeAquaList.isEmpty() && likeAquaList.size() > i && likeAquaList.get(i) != null) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                        if (tagAqua.get(i)) {
//                                            if (LikeAqua.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisAqua.isEmpty() && DisAqua.size() > i && DisAqua.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                            if (DisAqua.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                        check = false;

                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagIns.size(); i++) {
                                        //エラー直すために勝手に追加
                                        if (tagIns.size() > i && tagIns.get(i)) {
                                            if (!likeInsList.isEmpty() && likeInsList.size() > i && likeInsList.get(i) != null) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                        if (tagIns.get(i)) {
//                                            if (LikeIns.get(i)) {
//                                                エラー直すために勝手にコメントアウト
                                                like = true;
                                            }
                                            //エラー直すために勝手に追加
                                            if (!DisIns.isEmpty() && DisIns.size() > i && DisIns.get(i)) {
//エラー直すために勝手に追加
//                                                エラー直すために勝手にコメントアウト
//                                            if (DisIns.get(i)) {
//                                                エラー直すために勝手にコメントアウト
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
                });
    }
    //                        エラー直すために勝手に追加
    private List<Boolean> getListFromDocument(DocumentSnapshot documentSnapshot, String fieldName) {
        List<Boolean> list = (List<Boolean>) documentSnapshot.get(fieldName);
        return list != null ? list : new ArrayList<>();
    }
    //                        エラー直すために勝手に追加

//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
////                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }

}