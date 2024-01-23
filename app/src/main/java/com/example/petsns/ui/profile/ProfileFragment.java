package com.example.petsns.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.petsns.R;
import com.example.petsns.Profile_TestPost;
import com.example.petsns.Profile_TestPostAdapter;
import com.example.petsns.TestPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private Profile_TestPostAdapter postAdapter;
    private FirebaseFirestore db;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new Profile_TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);

        fetchDataFromFirestore();

        return rootView;
    }

        public void fetchDataFromFirestore() {
            // Firestore からデータを取得して表示
            firestore = FirebaseFirestore.getInstance();
            firestore.collection("posts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Profile_TestPost> posts = new ArrayList<>();


                            db = FirebaseFirestore.getInstance();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();
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
                            postAdapter.setPosts(posts);
                        }


                    });
        }



//        ここまで

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton toukou = view.findViewById(R.id.toukoubtn);
        toukou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_snspost);
            }
        });
//        ImageButton sakujo = view.findViewById(R.id.sakujobtn);//投稿削除確認ポップアップ画面
//        sakujo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = requireContext();
//                Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.fragment_profile_deletecheck);
//                ImageButton hai = dialog.findViewById(R.id.haibtn);
//                ImageButton iie = dialog.findViewById(R.id.iiebtn);
//                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
//                params.width = 811; // 幅を変更
//                params.height = 372; // 高さを変更
//                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//                hai.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//                iie.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
    }
}
