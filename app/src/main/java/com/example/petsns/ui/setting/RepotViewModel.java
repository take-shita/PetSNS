package com.example.petsns.ui.setting;
import android.widget.Toast;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RepotViewModel extends ViewModel {

    // Firestoreへのデータ送信メソッド
    public void sendReportToFirestore(String subject, String message, Context context) {
        // Firestoreへのデータ送信処理
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // データの作成
        Map<String, Object> data = new HashMap<>();
        data.put("subject", subject);
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



//
//import androidx.lifecycle.ViewModelProvider;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.Navigation;
//import androidx.lifecycle.ViewModel;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.example.petsns.R;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//public class RepotViewModel extends ViewModel {
//
//    private RepotViewModel mViewModel;
//
//    public static repotFragment newInstance() {
//        return new repotFragment();
//    }
//
//    // Firestoreへのデータ送信メソッド
//    public void sendReportToFirestore(String subject, String message) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("subject", subject);
//        data.put("message", message);
//
//        db.collection("report").add(data)
//                .addOnSuccessListener(documentReference -> {
//                    // 成功時の処理
//                })
//                .addOnFailureListener(e -> {
//                    // 失敗時の処理
//                });
//    }
//}

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_repot, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(RepotViewModel.class);
//        // TODO: Use the ViewModel
//    }


//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        Button btncan = view.findViewById(R.id.btncan);
//
//        btncan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Firebase Firestoreに報告を送信
//                mViewModel.sendReportToFirestore("報告の件名", "報告のメッセージ");
//
//                // 報告が送信されたら設定画面に戻る
//                Navigation.findNavController(v).navigate(R.id.action_navigation_repot_to_navigation_setting);
//            }
//        });
//    }
//}
