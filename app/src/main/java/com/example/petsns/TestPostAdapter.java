package com.example.petsns;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.petsns.TestPost;
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

        // 投稿者IDと投稿文をセット
        holder.textUsername.setText(post.getid());
        holder.textPost.setText(post.getSentence());

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {

            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(post.getImageUrl());
            try {

                final File localFile = File.createTempFile("images", "jpg");
                storageReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                    // ローカルファイルから画像を読み込んでImageViewにセット
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
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textPost;
        ImageView imagePost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.textUsername);
            textPost = itemView.findViewById(R.id.textPost);
            imagePost = itemView.findViewById(R.id.imagePost);
        }
    }

    // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
}
