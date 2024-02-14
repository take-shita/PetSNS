package com.example.petsns.ui.profile;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    private List<Profile_TestPost> postList = new ArrayList<>();
    private ProfileViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
//    private Profile_TestPostAdapter postAdapter;
    private FirebaseFirestore db;
    private View rootView;
    private String userId;
    private ImageView profileIcon;


    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profileIcon = rootView.findViewById(R.id.profileicon);
//        postAdapter = new Profile_TestPostAdapter(requireContext(), postList);
        return rootView;
    }
    public void fetchDataFromFirestore() {
        // FireStore からデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts").orderBy("timestamp", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Profile_TestPost> posts = new ArrayList<>();

                db = FirebaseFirestore.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userUid = user.getUid();


                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                    CollectionReference collectionRefId = db.collection("userId");
                    collectionRefId.whereEqualTo("uid", userUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(Task<QuerySnapshot> task1) {

                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                            // ドキュメントが見つかった場合、IDを取得
                                            userId = document1.getId();

                                            DocumentReference docRef = db.collection("users").document(userId);


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
                                                                profileIcon.setImageBitmap(bitmap);
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
                                    }
                                }
                            });
                });
                try {
                    future1.get(); // 非同期処理が終わるまでブロック

                } catch (InterruptedException | ExecutionException e) {
                    // 例外処理
                }            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView profile_textUsername = view.findViewById(R.id.profile_textUsername);
        TextView profile_name = view.findViewById(R.id.profile_name);
        ImageButton toukou = view.findViewById(R.id.toukoubtn);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        fetchDataFromFirestore();

        toukou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_navigation_snspost);
            }
        });


        // タブが選択されたときのリスナー
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // タブが選択されたときに対応するFragmentを表示
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new profile_postFragment());
                        break;
                    case 1:
                        replaceFragment(new profile_iinepostFragment());
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 他のタブが選択されたときの処理
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 同じタブが再度選択されたときの処理
            }
        });
        // 最初のタブを表示
        replaceFragment(new profile_postFragment());
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }



}