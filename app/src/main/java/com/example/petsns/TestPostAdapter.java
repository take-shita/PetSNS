package com.example.petsns;

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

public class TestPostAdapter extends RecyclerView.Adapter<TestPostAdapter.PostViewHolder> {

    private List<TestPost> posts;
    private Context context;

    public TestPostAdapter(Context context) {
        this.context = context;
    }

    public void setPosts(List<TestPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        TestPost post = posts.get(position);

        // 投稿者 ID と投稿文をセット
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// コレクションとドキュメントのパスを指定
        String collectionPath = "users";
        String documentPath = post.getid();
        DocumentReference docRef = db.collection(collectionPath).document(documentPath);


        //        相手プロフィール画面への遷移
        holder.otherprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされたときの処理
                // 新しい画面に遷移する
                Navigation.findNavController(v).navigate(R.id.action_navigation_snstop_to_navigation_profile_other);
            }
        });
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


        holder.textPost.setText(post.getSentence());
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


        holder.hartbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_pressed_image);
                }else {
                    holder.hartbtn.setBackgroundResource(R.drawable.rounded_button_normal_image);
                }
            }
        });


//        holder.hartbt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    holder.hartbt.setBackgroundResource(R.drawable.rounded_button_pressed_image);
//                }else {
//                    holder.hartbt.setBackgroundResource(R.drawable.rounded_button_normal_image);
//                }
//            }
//        });

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
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            hartbt=itemView.findViewById(R.id.hartbt);
            hartbtn=itemView.findViewById(R.id.hartbtn);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
            otherprofilebtn = itemView.findViewById(R.id.otherprofilebtn);
            tagText=itemView.findViewById(R.id.tagText);
        }
    }

    // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
}
