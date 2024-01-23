package com.example.petsns.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;






public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private Profile_TestPostAdapter postAdapter;
    private PostViewHolder holder;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private List<Profile_TestPost> posts;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        //        主要な要素
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        postAdapter = new Profile_TestPostAdapter(requireContext());
        recyclerView.setAdapter(postAdapter);



        TextView profileUsernameTextView = rootView.findViewById(R.id.profile_textUsername);
        // Firebase Authenticationからユーザー情報を取得
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // ユーザーがログインしている場合、そのユーザーのIDを取得
            String userId = currentUser.getUid();

            // Firestore からデータを取得して表示
            firestore = FirebaseFirestore.getInstance();
            firestore.collection("posts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }

                            List<Profile_TestPost> posts = new ArrayList<>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Profile_TestPost post = document.toObject(Profile_TestPost.class);  // クラスの型もProfile_TestPostに変更
                                posts.add(post);


                            }
                            postAdapter.setPosts(posts);
                        }

        // Firestore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Profile_TestPost> posts = new ArrayList<>();



                    });
            firestore = FirebaseFirestore.getInstance();
            firestore.getInstance().collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // ユーザーのドキュメントが存在する場合、名前を取得して表示
                                // ユーザーのドキュメントが存在する場合、アイコンの URL を取得して表示
                                String iconUrl = documentSnapshot.getString("icon");
                                // ローカルファイルへのダウンロード処理
                                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(iconUrl);
                                try {
                                    final File localFile = File.createTempFile("images", "jpg");
                                    storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                        // PostViewHolder を作成して設定
                                        holder = new PostViewHolder(rootView);
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        holder.profileicon.setImageBitmap(bitmap);
                                    }).addOnFailureListener(exception -> {
                                        // 失敗時の処理
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // 画像がない場合の処理（任意で実装）
                                // holder.imagePost.setImageResource(R.drawable.placeholder_image);
                            }
                            String username = documentSnapshot.getString("name");
                            String userID = documentSnapshot.getString("id");
                            // profile_textUsernameのTextViewを取得
                            TextView profileTextUsernameTextView = rootView.findViewById(R.id.profile_textUsername);
                            // profile_nameのTextViewを取得
                            TextView profileNameTextView = rootView.findViewById(R.id.profile_name);


                            // 取得したIDと名前をそれぞれのTextViewにセット
                            profileTextUsernameTextView.setText(userID);
                            profileNameTextView.setText(username);


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
                    });
        }



        // ... (省略されたコード)

        return rootView;
    }


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
        TextView profile_name;
        TextView textPost;
        ImageView imagePost;

        ImageView profileicon;
        ToggleButton hartbtn;
        TextView posttime;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_name = itemView.findViewById(R.id.profile_name);
            hartbtn = itemView.findViewById(R.id.hartbtn);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
            profileicon = itemView.findViewById(R.id.profileicon);
            posttime = itemView.findViewById(R.id.posttime);
        }
    }
}



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


