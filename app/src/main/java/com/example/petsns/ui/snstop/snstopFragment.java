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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

        // Firestore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<TestPost> posts = new ArrayList<>();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Map<String, Object> data = document.getData();
                            Log.d(TAG, "Data from Firestore: " + data.toString());

                            TestPost post = new TestPost();
                            post.setId((String) data.get("id"));
                            post.setSentence((String) data.get("sentence"));
                            post.setImageUrl((String) data.get("imageUrl"));
                            List<Boolean> tagMom = (List<Boolean>) data.get("tagMom");
                            post.setTagMom(tagMom);
                            List<Boolean> tagBir = (List<Boolean>) data.get("tagBir");
                            post.setTagMom(tagBir);
                            List<Boolean> tagRip = (List<Boolean>) data.get("tagRip");
                            post.setTagMom(tagRip);
                            List<Boolean> tagBis = (List<Boolean>) data.get("tagBis");
                            post.setTagMom(tagBis);
                            List<Boolean> tagAqua = (List<Boolean>) data.get("tagAqua");
                            post.setTagMom(tagAqua);
                            List<Boolean> tagIns = (List<Boolean>) data.get("tagIns");
                            post.setTagMom(tagIns);
//                            TestPost post = document.toObject(TestPost.class);
                            posts.add(post);
                        }
                        postAdapter.setPosts(posts);
                    }


                });



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
}