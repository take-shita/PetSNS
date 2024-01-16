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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> ques;
    private Context context;

    public QuestionAdapter(Context context) {
        this.context = context;
    }

    public void setQuestion(List<Question> ques) {
        this.ques = ques;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_questions, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question quess = ques.get(position);

        // 投稿者IDと投稿文をセット
        holder.ques_title.setText(quess.getQues_title());
        holder.answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされたときの処理
                // 新しい画面に遷移する
                Navigation.findNavController(v).navigate(R.id.action_navigation_board_to_navigation_Board_Chat);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ques != null ? ques.size() : 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView ques_title;
        Button answer_btn;
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ques_title = itemView.findViewById(R.id.ques_title);
            answer_btn=  itemView.findViewById(R.id.Answer_btn);
        }
    }

    // 画像データのバイナリをデコードするメソッドが必要な場合、以下のように実装できます
    /*
    private Bitmap decodeByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
    */
}
