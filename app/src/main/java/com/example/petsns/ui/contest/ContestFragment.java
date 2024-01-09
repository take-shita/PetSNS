package com.example.petsns.ui.contest;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.Gravity;

import com.example.petsns.MainActivity;
import com.example.petsns.R;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

public class ContestFragment extends Fragment {

    private ContestViewModel mViewModel;
    private TextView popupText;
    Button btnView;
    Button btnPost;
    Button btnInfo;
    Button btnEntry;


    public static ContestFragment newInstance() {
        return new ContestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contest, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContestViewModel.class);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView txt= view.findViewById(R.id.textView25);



        btnView = view.findViewById(R.id.btnContestView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_view);

            }
        });


        btnPost = view.findViewById(R.id.btnContestPost);
        popupText = view.findViewById(R.id.popupText);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_navigation_contest_to_navigation_contest_post);
//                btnPost.setEnabled(false);
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.fragment_contest_post);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 700; // 幅を変更
                params.height = 1200; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnContestTopBack);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        btnInfo= view.findViewById(R.id.btnContestInfo);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.fragment_contest_info);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 700; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button btnClose = dialog.findViewById(R.id.btnContestTopBack);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        btnEntry=view.findViewById(R.id.btnContestEntry);
        btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEntry.setEnabled(false);
                btnPost.setEnabled(true);
                showPopup();
            }
        });



    }
    private void showPopup() {
        // ポップアップ表示
        popupText.setVisibility(View.VISIBLE);

        // 3秒後にポップアップを非表示にする
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                popupText.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // リクエストメソッドの設定（GETやPOSTなど）
                con.setRequestMethod("GET");

                // レスポンスコードの取得
                int responseCode = con.getResponseCode();

                // レスポンスデータの取得
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // レスポンスを処理するコードをここに記述
        }
    }

}