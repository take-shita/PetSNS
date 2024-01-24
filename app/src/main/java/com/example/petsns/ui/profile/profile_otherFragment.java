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
import androidx.lifecycle.ViewModel;


public class profile_otherFragment extends Fragment {

    private ProfileOtherViewModel mViewModel;
    private TextView other_userid;
    private TextView other_username;
    private ImageView other_icon;

    public static profile_otherFragment newInstance() {
        return new profile_otherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_other, container, false);

        // レイアウト要素を取得
        other_userid = view.findViewById(R.id.other_userid);
        other_username = view.findViewById(R.id.other_username);
        other_icon = view.findViewById(R.id.other_icon);
        // ユーザーIDを取得
        String userId = null; // 初期値を設定しておく



        Bundle args = getArguments();
        if (args != null) {
            userId = args.getString("userId");
            // userId を使った処理を続ける
        } else {
            // getArguments() が null の場合のエラー処理やデフォルト値の設定など
        }

        // Firebase からユーザーデータを取得して表示
        loadUserData(userId);

        // 初回表示時にボタンの状態に合わせて背景を設定
        ToggleButton followbtn = view.findViewById(R.id.followbtn);
        if (followbtn.isChecked()) {
            followbtn.setBackgroundResource(R.drawable.forotyuutouka);
        } else {
            followbtn.setBackgroundResource(R.drawable.forotouka);
        }

        return view;
    }
    private void loadUserData(String userId) {
        if (userId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // ユーザーのドキュメント参照
            DocumentReference userRef = db.collection("users").document(userId);

            // ユーザーデータを取得
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // ドキュメントが存在する場合、ユーザー名とプロフィール画像を表示
                        String username = documentSnapshot.getString("name");
                        String profileImageUrl = documentSnapshot.getString("profileImageUrl");

                        other_username.setText(username);

                        // プロフィール画像の読み込みなどの処理を追加
                        // 例: Picasso や Glide を使用して画像を表示
                        // Picasso.get().load(profileImageUrl).into(profileImageView);
                    }
                }
            });
        } else {

        }
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
        PostViewHolder postViewHolder = new PostViewHolder(view);

        ImageButton toukou = view.findViewById(R.id.toukoubtn);
        toukou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_other_to_navigation_snspost);
            }
        });

        ToggleButton followbtn = view.findViewById(R.id.followbtn);

        followbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    followbtn.setBackgroundResource(R.drawable.forotyuutouka);
                } else {
                    followbtn.setBackgroundResource(R.drawable.forotouka);
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

