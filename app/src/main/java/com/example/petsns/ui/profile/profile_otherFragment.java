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

import com.example.petsns.OtherPostAdapter;
import com.example.petsns.R;
import com.example.petsns.Profile_TestPost;
import com.example.petsns.Profile_TestPostAdapter;
import com.example.petsns.TestPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
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
import androidx.lifecycle.ViewModel;
import android.util.Log;



public class profile_otherFragment extends Fragment {

    private ProfileOtherViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private String value;
    private DocumentReference documentRef;
    private String fieldName = "follow";
    private CollectionReference collectionRef;
    private ToggleButton followbtn;
    private String userId;

    private TextView other_userid;
    private TextView other_username;
    private ImageView other_icon;
    private ProfileFragment.PostViewHolder holder; // PostViewHolderのインスタンスをメンバ変数として宣言

    private View rootView;
    private PostViewHolder postViewHolder;  // PostViewHolderをメンバ変数として宣言
    private static final String TAG = "ProfileOtherFragment";
    private OtherPostAdapter postAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





        public static profile_otherFragment newInstance() {
            return new profile_otherFragment();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_profile_other, container, false);
            //         RecyclerViewをインスタンス化
            Bundle args = getArguments();

            if (args != null) {
                value = args.getString("userId");
                // データを使用して何かを行う
                // 例: TextViewにセットするなど
            }

            recyclerView = view.findViewById(R.id.recyclerView);
            // LinearLayoutManagerを設定
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            // OtherPostAdapterをインスタンス化
            postAdapter = new OtherPostAdapter(requireContext());
            // RecyclerViewにAdapterをセット
            recyclerView.setAdapter(postAdapter);
            // Firestoreからデータを取得


            fetchDataFromFirestore();
            //        主要な要素
            // ユーザーIDとユーザー名のTextViewを初期化
            other_userid = view.findViewById(R.id.other_userid);
            other_username = view.findViewById(R.id.other_username);

            // ユーザーIDを取得

// PostViewHolder クラスをインスタンス化し、itemView を使用して findViewById メソッドを呼び出す
            postViewHolder = new PostViewHolder(view);

            // 初回表示時にボタンの状態に合わせて背景を設定
            ToggleButton followbtn = view.findViewById(R.id.followbtn);

            db = FirebaseFirestore.getInstance();
            user = FirebaseAuth.getInstance().getCurrentUser();
            userId = user.getUid();
            collectionRef = db.collection("users");
            documentRef = collectionRef.document(userId);

            documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> currentList = (List<String>) document.get(fieldName);

                            if (currentList != null) {
                                if(currentList.contains(value)){
                                    followbtn.setChecked(true);
                                }
                            }
                        }
                    }
                }
            });


            if (followbtn.isChecked()) {
                followbtn.setBackgroundResource(R.drawable.forotyuutouka);
            } else {
                followbtn.setBackgroundResource(R.drawable.forotouka);
            }



            return view;
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
                            DocumentReference docRef = db.collection("users").document(value);


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
                                if(((String) data.get("id")).equals(value)){
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
                                }else {

                                }

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
                                                postViewHolder.other_icon.setImageBitmap(bitmap);
                                            }).addOnFailureListener(exception -> {
                                                // 失敗時の処理
                                            });
                                        } catch (IOException e) {
                                            // IOExceptionが発生した場合の処理
                                            e.printStackTrace();
                                        }

                                        String username = documentSnapshot.getString("name");
                                        String userID = documentSnapshot.getString("id");

                                        // フィールドとして定義されている変数に代入
                                        if (other_userid != null && other_username != null) {
                                            other_userid.setText(userID);
                                            other_username.setText(username);
                                        } else {
                                            Log.e(TAG, "other_userid or other_username is null!");
                                        }
                                    } else {
                                        // ユーザーのドキュメントが存在しない場合の処理
                                    }
                                }
                            });
                        }


                    });
        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(ProfileOtherViewModel.class);
            // TODO: Use the ViewModel
        }

        public static class ProfileOtherViewModel extends ViewModel {
            // TODO: Implement the ViewModel
        }
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // PostViewHolder クラスをインスタンス化し、itemView を使用して findViewById メソッドを呼び出す
            postViewHolder = new PostViewHolder(view);

            ImageButton toukou = view.findViewById(R.id.toukoubtn);
            toukou.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_profile_other_to_navigation_snspost);
                }
            });

            ToggleButton followbtn = view.findViewById(R.id.followbtn);



//            DocumentReference documentRef = collectionRef.document(userId);
            followbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        followbtn.setBackgroundResource(R.drawable.forotyuutouka);

                        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        List<String> currentList = (List<String>) document.get(fieldName);

                                        if (currentList == null) {
                                            currentList = new ArrayList<>();
                                        }
                                        if(!currentList.contains(value)){
                                            currentList.add(value);
                                            documentRef.update(fieldName, currentList)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // 更新が成功した場合の処理
                                                            Log.d(TAG, "Document updated successfully!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // 更新が失敗した場合の処理
                                                            Log.e(TAG, "Error updating document", e);
                                                        }
                                                    });

                                        }


                                        // 更新されたデータを設定

                                    }
                                }
                            }
                        });
                    } else {
                        followbtn.setBackgroundResource(R.drawable.forotouka);

                        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        List<String> currentList = (List<String>) document.get(fieldName);

                                        if (currentList == null) {
                                            currentList = new ArrayList<>();
                                        }

                                        currentList.remove(value);

                                        // 更新されたデータを設定
                                        documentRef.update(fieldName, currentList)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // 更新が成功した場合の処理
                                                        Log.d(TAG, "Document updated successfully!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // 更新が失敗した場合の処理
                                                        Log.e(TAG, "Error updating document", e);
                                                    }
                                                });
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            TextView other_userid;
            TextView other_username;
            ImageView other_icon;
            ImageView profileicon;
            ToggleButton hartbtn;
            TextView posttime;
            TextView tagText;
            TextView likeCount;
            ImageButton delete_btn;
            public PostViewHolder(@NonNull View itemView) {
                super(itemView);
                hartbtn = itemView.findViewById(R.id.hartbtn);
                other_userid = itemView.findViewById(R.id.other_userid);
                other_username = itemView.findViewById(R.id.other_username);
                other_icon = itemView.findViewById(R.id.other_icon);
                profileicon = itemView.findViewById(R.id.profileicon);
                posttime = itemView.findViewById(R.id.posttime);
                tagText=itemView.findViewById(R.id.tagText);
                likeCount=itemView.findViewById(R.id.iinecount);
                delete_btn=itemView.findViewById(R.id.delete_btn);

            }
        }
    }