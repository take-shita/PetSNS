package com.example.petsns.ui.board;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.MainActivity;
import com.example.petsns.Question;
import com.example.petsns.QuestionAdapter;
import com.example.petsns.R;
import com.example.petsns.TestPost;
import com.example.petsns.TestPostAdapter;
import com.example.petsns.ui.snstop.SnstopViewModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class BoardFragment extends Fragment {

    private BoardViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private QuestionAdapter QuestionAdapter;
    private FirebaseFirestore db;
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

        EditText ques_title= getView().findViewById(R.id.ques_title);
        EditText ques_con=getView().findViewById(R.id.ques_content);


        Button Question = view.findViewById(R.id.Q_btn);

        db = FirebaseFirestore.getInstance();
        Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_question);

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

                        String userId = user.getUid();

                        CollectionReference postCollection = db.collection("question");

                        Map<String, Object> data = new HashMap<>();
                        data.put("user_id",userId);
                        data.put("timestamp", FieldValue.serverTimestamp());
                        data.put("ques_content",ques_con.getText().toString());
                        data.put("ques_title",ques_title.getText().toString());

                        postCollection.document(UUID.randomUUID().toString()).set(data)
                                .addOnSuccessListener(documentReference -> {
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                });
                dialog.show();
            }
        });
    }
}