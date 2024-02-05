package com.example.petsns;

import com.example.petsns.Profile_TestPost;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.petsns.TestPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.firebase.Timestamp;







public class Profile_TestPostAdapter extends RecyclerView.Adapter<Profile_TestPostAdapter.PostViewHolder> {

    private List<Profile_TestPost> posts;
    private Context context;
    private FirebaseFirestore db;
    String userId;
    private FirebaseUser user;
    private CollectionReference collectionRef;
    private DocumentReference documentRef;
    private String fieldName = "iinePostId";
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();


    private void deleteFirestoreData(String documentId) {
        // 削除するドキュメントの参照を取得
        db1.collection("posts").document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // 削除成功時の処理
                    // 例: ユーザーに通知など
                })
                .addOnFailureListener(e -> {
                    // 削除失敗時の処理
                    // 例: エラーメッセージを表示
                });
    }


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

        String documentId=post.getDocumentId();

        // 投稿者 ID と投稿文をセット
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // FirebaseAuthからユーザーを取得
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // 投稿時間を取得
        Timestamp timestamp = post.gettimestamp();

        // 取得した投稿時間を適切なフォーマットに変換
        String formattedTime = formattimestamp(timestamp);





        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされたときの処理
                // 新しい画面に遷移する  後で書き換える
                Context context = v.getContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_profile_deletecheck);
                ImageButton hai = dialog.findViewById(R.id.haibtn);
                ImageButton iie = dialog.findViewById(R.id.iiebtn);
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 372; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                hai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFirestoreData(documentId);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                iie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        // posttime TextView にセット
        holder.posttime.setText(formattedTime);

        db.collection("posts") // コレクション名
                .document(documentId) // ドキュメント名
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // ドキュメントが存在する場合、フィールドの値を取得
                            if(!documentSnapshot.get("likeCount").toString().equals("0")){
                                holder.likeCount.setText(documentSnapshot.get("likeCount").toString());
                            }
                            // 取得した値を利用する処理をここに追加
                        } else {
                            // ドキュメントが存在しない場合の処理
                        }
                    }
                });

        if (user != null) {
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

                                                holder.textPost.setText(post.getSentence());

                                                // コレクションとドキュメントのパスを指定
                                                String collectionPath = "users";
                                                String documentPath = userId;
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

                                                holder.tagText.setText(post.tagConversion());


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
                                                }

                                                holder.hartbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(isChecked){
                                                            //いいねを押したときの処理
                                                            List<TestPost> posts = new ArrayList<>();
                                                            user = FirebaseAuth.getInstance().getCurrentUser();
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

                                                                                        collectionRef = db.collection("users");
                                                                                        documentRef = collectionRef.document(userId);
                                                                                        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    DocumentSnapshot document = task.getResult();
                                                                                                    if (document.exists()) {
                                                                                                        List<String> currentList = (List<String>) document.get(fieldName);
                                                                                                        if (currentList == null) {
                                                                                                            // リストがまだ存在しない場合、新しいリストを作成
                                                                                                            currentList = new ArrayList<>();
                                                                                                        }
                                                                                                        currentList.add(post.getDocumentId());
                                                                                                        documentRef.update(fieldName, currentList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void unused) {
                                                                                                                // 成功時の処理
                                                                                                                holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_pressed_image);
                                                                                                                DocumentReference docRef = db.collection("posts").document(documentId);

                                                                                                                Map<String, Object> updates = new HashMap<>();
                                                                                                                if (post.getLikeCount() != 0) {
                                                                                                                    int likeCountPlus = post.getLikeCount() + 1;
                                                                                                                    post.setLikeCount(likeCountPlus);
                                                                                                                } else {
                                                                                                                    int likeCountPlus = 1;
                                                                                                                    post.setLikeCount(likeCountPlus);
                                                                                                                }
                                                                                                                updates.put("likeCount", post.getLikeCount());
                                                                                                                docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                    @Override
                                                                                                                    public void onSuccess(Void unused) {
                                                                                                                        if (post.getLikeCount() != 1) {
                                                                                                                            holder.likeCount.setText(String.valueOf(post.getLikeCount()));
                                                                                                                        } else {
                                                                                                                            holder.likeCount.setText("");
                                                                                                                        }
                                                                                                                    }
                                                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                                                    @Override
                                                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                                                            @Override
                                                                                                            public void onFailure(@NonNull Exception e) {
                                                                                                                // 失敗時の処理
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                                                            }
                                                                        });
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                    });



        }
    }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            TextView textUsername;
            TextView textPost;
            ImageView imagePost;
            ImageView profileicon;
            ToggleButton hartbtn;
            TextView posttime;
            TextView tagText;
            TextView likeCount;
            ImageButton delete_btn;

            public PostViewHolder(@NonNull View itemView) {
                super(itemView);
                hartbtn = itemView.findViewById(R.id.hartbtn);
                textUsername = itemView.findViewById(R.id.textUsername);
                textPost = itemView.findViewById(R.id.textPost);
                imagePost = itemView.findViewById(R.id.imagePost);
                profileicon = itemView.findViewById(R.id.profileicon);
                posttime = itemView.findViewById(R.id.posttime);
                tagText=itemView.findViewById(R.id.tagText);
                likeCount=itemView.findViewById(R.id.iinecount);
                delete_btn=itemView.findViewById(R.id.delete_btn);

            }
        }

        // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
    }
