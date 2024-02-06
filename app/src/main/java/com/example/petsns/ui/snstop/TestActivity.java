package com.example.petsns.ui.snstop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsns.R;
import com.example.petsns.ui.snstop.TestPost;
import com.example.petsns.ui.snstop.TestPostAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Fragment {

    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private TestPostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_timeline_test, container, false);

        // RecyclerViewの設定
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);

        // Firestoreからデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<TestPost> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            TestPost post = document.toObject(TestPost.class);
                            posts.add(post);
                        }
                        postAdapter.setPosts(posts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // エラー時の処理
//                        Log.w(TAG, "Error getting documents.", e);
                    }
                });

        return rootView;
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timeline_test);
//
//        firestore = FirebaseFirestore.getInstance();
//        recyclerView = findViewById(R.id.recyclerView);
//
//        // RecyclerViewの設定
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        postAdapter = new TestPostAdapter(this);
//        recyclerView.setAdapter(postAdapter);
//
//        // Firestoreからデータを取得して表示
//        firestore.collection("posts")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                        List<TestPost> posts = new ArrayList<>();
//                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                            TestPost post = document.toObject(TestPost.class);
//                            posts.add(post);
//                        }
//                        postAdapter.setPosts(posts);
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
////                        Log.w(TAG, "Error getting documents.", e);
//                    }
//                });
//    }
}

