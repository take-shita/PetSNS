package com.example.petsns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.example.petsns.TestPost;

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

        // 画像をセット
        // ここで投稿写真のバイナリデータをImageViewに設定する処理を実装する
        // 例: holder.imagePost.setImageBitmap(decodeByteArray(post.get投稿写真()));

        // 他に必要な処理があればここで実装
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
