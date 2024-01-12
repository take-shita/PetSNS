package com.example.petsns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // ここにデータを保持する変数やリストを定義する
    private String title;
    private String description;
    private String timestamp;

    public MyAdapter() {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription(){
        return title;
    }

    public String getTimestamp(){
        return title;
    }

    // ViewHolderクラス
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }

    // コンストラクタなど、必要なメソッドを実装

    // onCreateViewHolder: ViewHolderを作成
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycle_post, parent, false);
        return new MyViewHolder(v);
    }

    // onBindViewHolder: ViewHolderにデータをバインド
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // ここでデータをViewHolderにバインドする処理を実装
    }

    // getItemCount: データの総数を返す
    @Override
    public int getItemCount() {
        // データの総数を返す処理を実装
        return 0;
    }
}
