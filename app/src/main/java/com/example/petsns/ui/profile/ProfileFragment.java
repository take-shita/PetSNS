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
import android.widget.ImageView;
import android.widget.TextView;
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

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import java.io.File;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;








public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private Profile_TestPostAdapter postAdapter;
    private FirebaseFirestore db;
    private PostViewHolder holder; // PostViewHolderのインスタンスをメンバ変数として宣言

    private View rootView;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new Profile_TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);

        holder = new PostViewHolder(rootView, rootView);  // ここでrootViewを渡す

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

                        // データ取得後にユーザー情報を表示する処理を追加
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    // ユーザーのドキュメントが存在する場合の処理
                                    String iconUrl = documentSnapshot.getString("icon");
                                    // ローカルファイルへのダウンロード処理
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(iconUrl);
                                    try {
                                        final File localFile = File.createTempFile("images", "jpg");
                                        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                            // 成功時の処理
                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            holder.profileicon.setImageBitmap(bitmap);
                                        }).addOnFailureListener(exception -> {
                                            // 失敗時の処理
                                        });
                                    } catch (IOException e) {
                                        // IOExceptionが発生した場合の処理
                                        e.printStackTrace();
                                    }

                                    String username = documentSnapshot.getString("name");
                                    String userID = documentSnapshot.getString("id");
                                    // profile_textUsernameのTextViewを取得
                                    TextView profile_textUsername = rootView.findViewById(R.id.profile_textUsername);
                                    // profile_nameのTextViewを取得
                                    TextView profile_name = rootView.findViewById(R.id.profile_name);

                                    // 取得したIDと名前をそれぞれのTextViewにセット
                                    profile_textUsername.setText(userID);
                                    profile_name.setText(username);
                                } else {
                                    // ユーザーのドキュメントが存在しない場合の処理
                                }
                            }
                        });
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

        public PostViewHolder(@NonNull View itemView, View rootView) {
            super(itemView);

            this.rootView = rootView;  // rootViewを設定

            hartbtn=itemView.findViewById(R.id.hartbtn);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
            otherprofilebtn = itemView.findViewById(R.id.otherprofilebtn);
            tagText=itemView.findViewById(R.id.tagText);
            likeCount=itemView.findViewById(R.id.iinecount);
            posttime = itemView.findViewById(R.id.posttime);

            report_btn = itemView.findViewById(R.id.report_btn);
            profileicon = itemView.findViewById(R.id.profileicon);

        }
    }
}

//package com.example.petsns.ui.profile;
//
//import androidx.lifecycle.ViewModelProvider;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.Navigation;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.ToggleButton;
//
//import com.example.petsns.R;
//import com.example.petsns.Profile_TestPost;
//import com.example.petsns.Profile_TestPostAdapter;
//
//import com.example.petsns.TestPost;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import java.util.Map;
//
//
//
//
//
//
//
//
//public class ProfileFragment extends Fragment {
//
//    private ProfileViewModel mViewModel;
//    private FirebaseFirestore firestore;
//    private RecyclerView recyclerView;
//    private Profile_TestPostAdapter postAdapter;
//    private PostViewHolder holder;
//
//
//    public static ProfileFragment newInstance() {
//        return new ProfileFragment();
//    }
//
//    private List<Profile_TestPost> posts;
//    private Context context;
//    private FirebaseFirestore db;
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
//        //        主要な要素
//        recyclerView = rootView.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//
//        postAdapter = new Profile_TestPostAdapter(requireContext());
//        recyclerView.setAdapter(postAdapter);
//
//
//        fetchDataFromFirestore();
//
//        return rootView;
//    }
//
//        public void fetchDataFromFirestore() {
//
//            // Firestore からデータを取得して表示
//            firestore = FirebaseFirestore.getInstance();
//            firestore.collection("posts")
//                    .orderBy("timestamp", Query.Direction.DESCENDING)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            List<Profile_TestPost> posts = new ArrayList<>();
//
//
//                            db = FirebaseFirestore.getInstance();
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            String userId = user.getUid();
//                            DocumentReference docRef = db.collection("users").document(userId);
//
//
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Profile_TestPost post = document.toObject(Profile_TestPost.class);  // クラスの型もProfile_TestPostに変更
//
//                                Map<String, Object> data = document.getData();
//                                String documentId = document.getId();
//
//                                List<Boolean> tagMom = (List<Boolean>) data.get("tagMom");
//                                List<Boolean> tagBir = (List<Boolean>) data.get("tagBir");
//                                List<Boolean> tagRip = (List<Boolean>) data.get("tagRip");
//                                List<Boolean> tagBis = (List<Boolean>) data.get("tagBis");
//                                List<Boolean> tagAqua = (List<Boolean>) data.get("tagAqua");
//                                List<Boolean> tagIns = (List<Boolean>) data.get("tagIns");
//                                Number likeCountDouble = ((Number) data.get("likeCount"));
//
//                                post.setId((String) data.get("id"));
//                                post.setSentence((String) data.get("sentence"));
//                                post.setImageUrl((String) data.get("imageUrl"));
//                                post.setDocumentId(documentId);
//                                post.setLikeCount(likeCountDouble.intValue());
//                                post.setTagMom(tagMom);
//
//                                post.setTagBir(tagBir);
//
//                                post.setTagRip(tagRip);
//
//                                post.setTagBis(tagBis);
//
//                                post.setTagAqua(tagAqua);
//
//                                post.setTagIns(tagIns);
//
//
//                                posts.add(post);
//                            }
//                            postAdapter.setPosts(posts);
//                        }
//
//
//
//                    });
//        }
//
//
//
//        // ... (省略されたコード)
//
//
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ImageButton toukou = view.findViewById(R.id.toukoubtn);
//        toukou.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_snspost);
//            }
//        });
//
//    }
//
//
//    public class PostViewHolder extends RecyclerView.ViewHolder {
//        TextView textUsername;
//        TextView profile_name;
//        TextView textPost;
//        ImageView imagePost;
//
//        ImageView profileicon;
//        ToggleButton hartbtn;
//        TextView posttime;
//
//        public PostViewHolder(@NonNull View itemView) {
//            super(itemView);
//            profile_name = itemView.findViewById(R.id.profile_name);
//            hartbtn = itemView.findViewById(R.id.hartbtn);
//            textUsername = itemView.findViewById(R.id.textUsername);
//            textPost = itemView.findViewById(R.id.textPost);
//            imagePost = itemView.findViewById(R.id.imagePost);
//            profileicon = itemView.findViewById(R.id.profileicon);
//            posttime = itemView.findViewById(R.id.posttime);
//        }
//    }
//}