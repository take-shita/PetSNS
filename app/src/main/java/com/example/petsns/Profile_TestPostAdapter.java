package com.example.petsns;

import com.example.petsns.Profile_TestPost;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.petsns.TestPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.google.firebase.Timestamp;







public class Profile_TestPostAdapter extends RecyclerView.Adapter<Profile_TestPostAdapter.PostViewHolder> {

    private List<Profile_TestPost> posts;
    private Context context;

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public Profile_TestPostAdapter(Context context) {
        this.context = context;
    }

    public void setPosts(List<com.example.petsns.Profile_TestPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_item_post, parent, false);
        return new PostViewHolder(view);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < posts.size()) {
            posts.remove(position);
            notifyItemRemoved(position);
        }
    }

    private String formattimestamp(Timestamp timestamp) {
        // ここで適切なフォーマットに変換する処理を実装
        // 例: SimpleDateFormatを使用して文字列に変換する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp.getSeconds() * 1000));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Profile_TestPost post = posts.get(position);

        // 投稿者 ID と投稿文をセット
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // FirebaseAuthからユーザーを取得
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // 投稿時間を取得
        Timestamp timestamp = post.gettimestamp();

        // 取得した投稿時間を適切なフォーマットに変換
        String formattedTime = formattimestamp(timestamp);

        // posttime TextView にセット
        holder.posttime.setText(formattedTime);


        if (user != null) {
            // ユーザーがログインしている場合
            // UIDを取得
            String uid = user.getUid();

            // 投稿者がログインユーザーでない場合、非表示にする
            if (!uid.equals(post.getid())) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                return; // 非表示の場合はここでメソッドを終了
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            holder.textPost.setText(post.getSentence());

            // コレクションとドキュメントのパスを指定
            String collectionPath = "users";
            String documentPath = uid;
            DocumentReference docRef = db.collection(collectionPath).document(documentPath);

            // ドキュメントを取得
            // ドキュメントを取得
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // フィールド名を指定して文字列を取得
                            String name = document.getString("name");
                            String iconUrl = document.getString("icon");
                            // ログインユーザーの情報を表示
                            holder.textUsername.setText(name);

//                        アイコンの表示
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
                            // fieldValue を使って何かを行う
                        } else {
                            // ドキュメントが存在しない場合の処理
                        }
                    } else {
                        // エラーが発生した場合の処理
                    }
                }
            });


            holder.textPost.setText(post.getSentence());


            if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {

                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(post.getImageUrl());
                try {

                    final File localFile = File.createTempFile("images", "jpg");
                    storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                        // ローカルファイルから画像を読み込んで ImageView にセット
                        if (holder.imagePost != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.imagePost.setImageBitmap(bitmap);
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

                holder.hartbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_pressed_image);
                        } else {
                            holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_normal_image);
                        }
                    }
                });
            }
        }
    }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            TextView textUsername;
            TextView textPost;
            ImageView imagePost;
            ImageView profileicon;
            ToggleButton hartbtn;
            TextView posttime;

            public PostViewHolder(@NonNull View itemView) {
                super(itemView);
                hartbtn = itemView.findViewById(R.id.hartbtn);
                textUsername = itemView.findViewById(R.id.textUsername);
                textPost = itemView.findViewById(R.id.textPost);
                imagePost = itemView.findViewById(R.id.imagePost);
                profileicon = itemView.findViewById(R.id.profileicon);
                posttime = itemView.findViewById(R.id.posttime);

            }
        }

        // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
    }
