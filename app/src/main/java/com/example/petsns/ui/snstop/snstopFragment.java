package com.example.petsns.ui.snstop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.R;
import com.example.petsns.TestPost;
import com.example.petsns.TestPostAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class snstopFragment extends Fragment {

    private SnstopViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private TestPostAdapter postAdapter;
    public static snstopFragment newInstance() {
        return new snstopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_snstop, container, false);

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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SnstopViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }
}