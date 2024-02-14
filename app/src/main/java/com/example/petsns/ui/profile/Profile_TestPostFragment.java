package com.example.petsns.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsns.R;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class Profile_TestPostFragment extends Fragment {

    private RecyclerView recyclerView;
    private Profile_TestPostAdapter adapter;
    private List<Profile_TestPost> postList;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_post, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        postList = new ArrayList<>();
        adapter = new Profile_TestPostAdapter(getActivity(), postList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // データベースから投稿を読み込んでリストに追加する処理をここに追加する

        return view;
    }
}
