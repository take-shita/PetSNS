package com.example.petsns.ui.contest;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsns.R;
import com.example.petsns.ui.profile.OtherPostAdapter;
import com.example.petsns.ui.profile.Profile_TestPost;
import com.example.petsns.ui.snstop.TestPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ContestPostAdapter  extends RecyclerView.Adapter<ContestPostAdapter.PostViewHolder>{
    private List<ContestItemPost> posts;
    private Context context;
    private List<QueryDocumentSnapshot> data;
    String userId;
    Boolean likeCheck=false;
    private FirebaseUser user;
    private CollectionReference collectionRef;
    private DocumentReference documentRef;
    private String fieldName = "iinePostId";

    public ContestPostAdapter(Context context) {
        this.data =new ArrayList<>();
        this.context = context;
    }

    public void setPosts(List<ContestItemPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    private String formattimestamp(Timestamp timestamp) {
        // ここで適切なフォーマットに変換する処理を実装
        // 例: SimpleDateFormatを使用して文字列に変換する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp.getSeconds() * 1000));
    }

    @NonNull
    @Override
    public ContestPostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contest_item_post, parent, false);

        return new ContestPostAdapter.PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestPostAdapter.PostViewHolder holder, int position) {
        if (posts != null && position != RecyclerView.NO_POSITION && position < posts.size()) {
            setLikeButtonState(holder, posts.get(position));
        }

        int adapterPosition = holder.getAdapterPosition();

        ContestItemPost post = posts.get(adapterPosition);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String documentId=post.getDocumentId();

        // 投稿時間を取得
        Timestamp timestamp = post.gettimestamp();

        // 取得した投稿時間を適切なフォーマットに変換
        String formattedTime = formattimestamp(timestamp);

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
//                                holder.likeCount.setText(documentSnapshot.get("likeCount").toString());
                            }
                            // 取得した値を利用する処理をここに追加
                        } else {
                            // ドキュメントが存在しない場合の処理
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // エラーが発生した場合の処理
                    }
                });



        holder.report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされたときの処理
                // 新しい画面に遷移する  後で書き換える
                Context context = v.getContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_profile_other_reportcheck);
                ImageButton hai = dialog.findViewById(R.id.haibtn);
                ImageButton iie = dialog.findViewById(R.id.iiebtn);
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 372; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                hai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

        holder.hartbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(isChecked){
                    if(!likeCheck){
                        //いいねを押したときの処理
                        likeCheck=true;
                        List<TestPost> posts = new ArrayList<>();
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
                                                                            DocumentReference docRef=db.collection("contestPosts").document(documentId);

                                                                            Map<String,Object> updates=new HashMap<>();
                                                                            if(post.getLikeCount()!=0){
                                                                                int likeCountPlus= post.getLikeCount()+1;
                                                                                post.setLikeCount(likeCountPlus);
                                                                            }else {
                                                                                int likeCountPlus=1;
                                                                                post.setLikeCount(likeCountPlus);
                                                                            }
                                                                            updates.put("likeCount",post.getLikeCount());
                                                                            docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {

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
                } else {
                    if(likeCheck){
                        likeCheck=false;
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
                                                                    currentList.remove(post.getDocumentId());
                                                                    documentRef.update(fieldName, currentList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            // 成功時の処理
                                                                            holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_normal_image);
                                                                            DocumentReference docRef=db.collection("contestPosts").document(documentId);

                                                                            Map<String,Object> updates=new HashMap<>();
                                                                            if( post.getLikeCount()!=0){
                                                                                int likeCountPlus= post.getLikeCount()-1;
                                                                                post.setLikeCount(likeCountPlus);
                                                                            }else { }
                                                                            updates.put("likeCount",post.getLikeCount());
                                                                            docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {
                                                                                    if(post.getLikeCount()!=0) {
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
            }
        });

//




// コレクションとドキュメントのパスを指定
        String collectionPath = "users";
        String documentPath = post.getid();
        DocumentReference docRef = db.collection(collectionPath).document(documentPath);


        //        相手プロフィール画面への遷移
//        holder.otherprofilebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // ボタンがクリックされたときの処理
//                // 新しい画面に遷移する
//                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_profile_other);
//            }
//        });
// ドキュメントを取得
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // フィールド名を指定して文字列を取得
                        String name = document.getString("name");
                        holder.textUsername.setText(name);
                        // fieldValue を使って何かを行う
                    } else {
                        // ドキュメントが存在しない場合の処理
                    }
                } else {
                    // エラーが発生した場合の処理
                }
            }
        });


//        holder.textPost.setText(post.getSentence());
//        post.tagConversion();

        holder.tagText.setText(post.tagConversion());

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(post.getImageUrl());
            try {

                final File localFile = File.createTempFile("images", "jpg");
                storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    // ローカルファイルから画像を読み込んで ImageView にセット
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.imagePost.setImageBitmap(bitmap);

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

        db.collection("users") // コレクション名
                .document(post.getid()) // ドキュメント名
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // ドキュメントが存在する場合、フィールドの値を取得
                            post.setIcon(documentSnapshot.getString("icon"));
                            if (post.getIcon() != null && !post.getIcon().isEmpty()) {

                                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(post.getIcon());
                                try {

                                    final File localFile = File.createTempFile("images", "jpg");
                                    storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                                        // ローカルファイルから画像を読み込んで ImageView にセット
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        holder.otherprofilebtn.setImageBitmap(bitmap);

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
                            // 取得した値を利用する処理をここに追加
                        } else {
                            // ドキュメントが存在しない場合の処理
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // エラーが発生した場合の処理
                    }
                });







    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textPost;
        ImageView imagePost;
        ImageButton otherprofilebtn;
        ToggleButton hartbtn;
        ToggleButton hartbt;
        TextView timestamp;
        TextView tagText;
        TextView likeCount;
        TextView posttime;
        ImageButton report_btn;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            hartbtn=itemView.findViewById(R.id.hartbtn);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
            otherprofilebtn = itemView.findViewById(R.id.otherprofilebtn);
            tagText=itemView.findViewById(R.id.tagText);
            likeCount=itemView.findViewById(R.id.iinecount);
            posttime = itemView.findViewById(R.id.posttime);

            report_btn = itemView.findViewById(R.id.report_btn);

        }
    }

    private void setLikeButtonState(ContestPostAdapter.PostViewHolder holder, ContestItemPost post) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
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

                                        DocumentReference userRef = db.collection("users").document(userId);
                                        userRef.get().addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                Log.d("?","1");
                                                List<String> iinePostIds = (List<String>) documentSnapshot.get("iinePostId");
                                                if (iinePostIds != null && iinePostIds.contains(post.getDocumentId())) {
                                                    // いいねした投稿のIDがユーザーのiinePostIdフィールドに含まれている場合、いいねボタンを押した状態に設定
                                                   likeCheck=true;
                                                    holder.hartbtn.setChecked(true);
                                                    holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_pressed_image);
                                                    Log.d("2","2");
                                                } else {
                                                    // 含まれていない場合は通常の状態に設定
                                                    holder.hartbtn.setChecked(false);
                                                    Log.d("3","3");
                                                }
                                            }
                                        }).addOnFailureListener(e -> {
                                            // エラーが発生した場合の処理
                                        });
                                    }
                                }
                            }
                        });
            });


        }
    }
}

