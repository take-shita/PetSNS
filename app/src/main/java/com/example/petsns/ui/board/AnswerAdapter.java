package com.example.petsns.ui.board;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsns.R;

import java.util.List;

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
        holder.report_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 新しい画面に遷移する  後で書き換える
                Context context = v.getContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_answer_report);
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
    }
    @Override
    public int getItemCount() {
        return ans != null ? ans.size() : 0;
    }
    public class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answer_content;
        Button report_btn;
        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            answer_content = itemView.findViewById(R.id.answer_content);
            report_btn=  itemView.findViewById(R.id.report_btn);
        }
    }
}