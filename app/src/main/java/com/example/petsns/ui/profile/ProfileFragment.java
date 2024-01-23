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
        // PostViewHolder を初期化
        holder = new PostViewHolder(rootView);

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


                    });
            firestore = FirebaseFirestore.getInstance();
            firestore.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // ユーザーのドキュメントが存在する場合、名前を取得して表示
                                // ユーザーのドキュメントが存在する場合、アイコンの URL を取得して表示
                                String iconUrl = documentSnapshot.getString("icon");
//                            アイコンの表示
                                if (iconUrl != null && !iconUrl.isEmpty()) {
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(iconUrl);
                                    try {
                                        final File localFile = File.createTempFile("images", "jpg");
                                        storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                            // ローカルファイルから画像を読み込んで ImageView にセット
                                            if (holder.profileicon != null) {
                                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                                holder.profileicon.setImageBitmap(bitmap);
                                            } else {
                                                // holder.imagePostがnullの場合の処理（ログなど）
                                            }
                                        }).addOnFailureListener(exception -> {
                                            // 失敗時の処理
                                        });
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // 画像がない場合の処理（任意で実装）
//            holder.imagePost.setImageResource(R.drawable.placeholder_image);
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
                            } else {
                                // ユーザーのドキュメントが存在しない場合の処理
                                // 例えば、デフォルトの名前とIDを設定するなど
                                // profile_textUsernameのTextViewを取得
                                TextView profileTextUsernameTextView = rootView.findViewById(R.id.profile_textUsername);
                                // profile_nameのTextViewを取得
                                TextView profileNameTextView = rootView.findViewById(R.id.profile_name);

                                // デフォルトのIDと名前をセット
                                profileTextUsernameTextView.setText("デフォルトID");
                                profileNameTextView.setText("デフォルトユーザー");
                            }
                        }
                    });
        } else {
            // ...
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


