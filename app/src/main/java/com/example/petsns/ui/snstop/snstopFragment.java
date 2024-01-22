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

import com.example.petsns.R;
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


//        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);
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
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_tag_search);
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
                                LikeMom = (List<Boolean>) documentSnapshot.get("likeMom");
                                LikeRip = (List<Boolean>) documentSnapshot.get("likeRip");
                                LikeBir = (List<Boolean>) documentSnapshot.get("likeBir");
                                LikeBis = (List<Boolean>) documentSnapshot.get("likeBis");
                                LikeAqua = (List<Boolean>) documentSnapshot.get("likeAqua");
                                LikeIns = (List<Boolean>) documentSnapshot.get("likeIns");

                                DisMom = (List<Boolean>) documentSnapshot.get("DisMom");
                                DisRip = (List<Boolean>) documentSnapshot.get("DisRip");
                                DisBir = (List<Boolean>) documentSnapshot.get("DisBir");
                                DisBis = (List<Boolean>) documentSnapshot.get("DisBis");
                                DisAqua = (List<Boolean>) documentSnapshot.get("DisAqua");
                                DisIns = (List<Boolean>) documentSnapshot.get("DisIns");
                                // fieldValueを使用して何かを行う
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boolean check = true;
                                    boolean like = false;
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
                                    for (int i = 0; i < tagMom.size(); i++) {
                                        if (tagMom.get(i)) {
                                            if (LikeMom.get(i)) {
                                                like = true;
                                            }
                                            if (DisMom.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagBir.size(); i++) {
                                        if (tagBir.get(i)) {
                                            if (LikeBir.get(i)) {
                                                like = true;
                                            }
                                            if (DisBir.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagRip.size(); i++) {
                                        if (tagRip.get(i)) {
                                            if (LikeRip.get(i)) {
                                                like = true;
                                            }
                                            if (DisRip.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagBis.size(); i++) {
                                        if (tagBis.get(i)) {
                                            if (LikeBis.get(i)) {
                                                like = true;
                                            }
                                            if (DisBis.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagAqua.size(); i++) {
                                        if (tagAqua.get(i)) {
                                            if (LikeAqua.get(i)) {
                                                like = true;
                                            }
                                            if (DisAqua.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }
                                    for (int i = 0; i < tagIns.size(); i++) {
                                        if (tagIns.get(i)) {
                                            if (LikeIns.get(i)) {
                                                like = true;
                                            }
                                            if (DisIns.get(i)) {
                                                check = false;
                                                break;
                                            }
                                        }
                                    }


                                    if (check) {
                                        if (!like) {
                                            random = Math.random();
                                        }
                                        if (like || random < 0.5) {


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
                                postAdapter.setPosts(posts);
                            } else {

                            }
                        });
                    }
                });
    }
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
////                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }

}