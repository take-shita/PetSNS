package com.example.petsns.ui.board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.petsns.Answer;
import com.example.petsns.AnswerAdapter;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardChatFragment extends Fragment {

    private BoardViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private com.example.petsns.AnswerAdapter AnswerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String documentID;
    private TextView txttitle;
    private  TextView txtcon;
    private FirebaseFirestore db;
    String userId;
    public BoardChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardChatFragment newInstance(String param1, String param2) {
        BoardChatFragment fragment = new BoardChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_board_chat, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        AnswerAdapter = new AnswerAdapter(requireContext());
        recyclerView.setAdapter(AnswerAdapter);

        db = FirebaseFirestore.getInstance();

        // Firestoreからデータを取得して表示
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("answer")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<Answer> ans = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Answer answ = document.toObject(Answer.class);
                            if(answ.getQues_id().equals(documentID)){
                                ans.add(answ);
                            }

                        }
                        AnswerAdapter.setAnswer(ans);
                    }
                });

        return rootView;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button question = view.findViewById(R.id.back_btn);
        txttitle=view.findViewById(R.id.textView27);
        txtcon=view.findViewById(R.id.ques_content_textview);
        Bundle args = getArguments();
        if (args != null) {
            documentID = args.getString("key");
            if(documentID!=null) {
                FirebaseStorage storage = FirebaseStorage.getInstance();

                firestore.collection("question")  // コレクション名
                        .document(documentID)      // ドキュメントID
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // ドキュメントが存在する場合
                                        // 指定したフィールドの値を取得
                                        String  fieldValue1 = document.getString("ques_title");
                                        String  fieldValue2 = document.getString("ques_content");
                                        txttitle.setText(fieldValue1);
                                        txtcon.setText(fieldValue2);

                                        // ここで fieldValue を使って必要な処理を行います
                                    } else {
                                        // ドキュメントが存在しない場合
                                    }
                                } else {
                                    // クエリの実行中にエラーが発生した場合
                                }
                            }
                        });
            }else {
                txttitle.setText("!!!!!!!!!!");
            }


            EditText ans_con = view.findViewById(R.id.editTextText3);

            Button answer = view.findViewById(R.id.send_btn);
            answer.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
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
                                                CollectionReference postCollection = db.collection("answer");

                                                Map<String, Object> data = new HashMap<>();
                                                data.put("user_id", userId);
                                                data.put("timestamp", FieldValue.serverTimestamp());
                                                data.put("ques_id",documentID);
                                                data.put("answer_content", ans_con.getText().toString());
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
            // データを使用して何かを行う
        }




        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_Board_Chat_to_navigation_board);
            }
        });
    }
}