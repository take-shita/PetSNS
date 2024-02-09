package com.example.petsns;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.ui.profile.ProfileFragment;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class profile_postFragment extends Fragment {

    private ProfilePostViewModel mViewModel;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;
    private String userId;
    private Profile_TestPostAdapter postAdapter;
    private PostViewHolder holder; // PostViewHolderのインスタンスをメンバ変数として宣言
    private List<Profile_TestPost> postList = new ArrayList<>();
    private RecyclerView recyclerView; // recyclerView をフィールド変数として定義





    public static profile_postFragment newInstance() {
        return new profile_postFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_post, container, false);
        //        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new Profile_TestPostAdapter(requireContext(), postList);
        recyclerView.setAdapter(postAdapter);
        holder = new PostViewHolder(rootView);  // rootview を渡す必要はありません
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfilePostViewModel.class);
        // TODO: Use the ViewModel
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textPost;
        ImageView imagePost;
        ImageButton otherprofilebtn;
        ToggleButton hartbtn;
        TextView timestamp;
        TextView tagText;
        TextView likeCount;
        TextView posttime;
        ImageButton report_btn;
        ImageView profileicon;
        // メンバ変数としてrootViewを保持
        View rootView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rootView = itemView;  // rootViewを設定
            hartbtn = itemView.findViewById(R.id.hartbtn);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
            otherprofilebtn = itemView.findViewById(R.id.otherprofilebtn);
            tagText = itemView.findViewById(R.id.tagText);
            likeCount = itemView.findViewById(R.id.iinecount);
            posttime = itemView.findViewById(R.id.posttime);
            report_btn = itemView.findViewById(R.id.report_btn);
            profileicon = itemView.findViewById(R.id.profileicon);
        }
    }
}