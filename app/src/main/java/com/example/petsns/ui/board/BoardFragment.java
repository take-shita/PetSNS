package com.example.petsns.ui.board;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.Question;
import com.example.petsns.QuestionAdapter;
import com.example.petsns.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BoardFragment extends Fragment {

    private BoardViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private QuestionAdapter QuestionAdapter;
    private FirebaseFirestore db;
    String userId;
    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_board, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        QuestionAdapter = new QuestionAdapter(requireContext());
        recyclerView.setAdapter(QuestionAdapter);

        // Firestoreからデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("question")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<Question> ques = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Question quess = document.toObject(Question.class);
                            quess.sampleDocumentId(document.getId());

                            ques.add(quess);
                        }
                        QuestionAdapter.setQuestion(ques);
                    }
                });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("question");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // ドキュメントIDを取得
                        String documentId = document.getId();
                        // ここでdocumentIdを使用できます
                        Log.d("Firestore", "Document ID: " + documentId);
                    }
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BoardViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {




        Button Question = view.findViewById(R.id.Q_btn);

        db = FirebaseFirestore.getInstance();
        Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_question);

                EditText ques_tit = dialog.findViewById(R.id.ques_title);
                EditText ques_con = dialog.findViewById(R.id.ques_content);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 800; // 幅を変更
                params.height = 1000; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.back_btn);

                btnClose.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });

                Button question = dialog.findViewById(R.id.question_btn);
                question.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                                                            CollectionReference postCollection = db.collection("question");

                                                            Map<String, Object> data = new HashMap<>();
                                                            data.put("user_id",userId);
                                                            data.put("timestamp", FieldValue.serverTimestamp());
                                                            data.put("ques_content",ques_con.getText().toString());
                                                            data.put("ques_title",ques_tit.getText().toString());

                                                            postCollection.document(UUID.randomUUID().toString()).set(data)
                                                                    .addOnSuccessListener(documentReference -> {
                                                                    })
                                                                    .addOnFailureListener(e -> {
                                                                    });
                                                        }
                                                    }
                                                }
                                            });
                                });
                        try {
                            future1.get(); // 非同期処理が終わるまでブロック
                        } catch (InterruptedException | ExecutionException e) {
                            // 例外処理
                        }

                    }
                });
                dialog.show();
            }
        });
    }
}