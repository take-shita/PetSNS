package com.example.petsns;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.petsns.TestPost;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {

    private List<Answer> ans;
    private Context context;

    public AnswerAdapter(Context context) {
        this.context = context;
    }

    public void setAnswer(List<Answer> ans) {
        this.ans = ans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        Answer answ = ans.get(position);

        // 回答内容
        holder.answer_content.setText(answ.getAnswer_content());

//        通報ボタン
        holder.Report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされたときの処理
                // 新しい画面に遷移する  後で書き換える
                Navigation.findNavController(v).navigate(R.id.action_navigation_board_to_navigation_Board_Chat);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ans != null ? ans.size() : 0;
    }

    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answer_content;
        Button Report_btn;
        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            answer_content = itemView.findViewById(R.id.answer_content);
            Report_btn=  itemView.findViewById(R.id.Report_btn);
        }
    }

    // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
}