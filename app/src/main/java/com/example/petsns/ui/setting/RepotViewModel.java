package com.example.petsns.ui.setting;
import android.widget.Toast;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RepotViewModel extends ViewModel {

    // Firestoreへのデータ送信メソッド
    public void sendReportToFirestore(String message, Context context) {
        // Firestoreへのデータ送信処理
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // データの作成
        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        // データの送信
        db.collection("reports").add(data)
                .addOnSuccessListener(documentReference -> {
                    // 成功時の処理
                    // 例: 送信成功のメッセージを表示
                    Toast.makeText(context, "報告が送信されました", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // 失敗時の処理
                    // 例: エラーメッセージを表示
                    Toast.makeText(context, "報告の送信に失敗しました", Toast.LENGTH_SHORT).show();
                });
    }
}