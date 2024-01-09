package com.example.petsns.ui.route;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.petsns.R;


public class route3Fragment extends Fragment {

    private TextView timerTextView;
    private Button startButton;
    private EditText timerText;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 初期値は 1 分（60,000 ミリ秒）

    public route3Fragment() {
        // Required empty public constructor
    }

    private Route3ViewModel mViewModel;

    public static route3Fragment newInstance() {
        return new route3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route3, container, false);

        timerText = view.findViewById(R.id.timer);
        startButton = view.findViewById(R.id.start1);
//        timerTextView = view.findViewById(R.id.timer1); // Access timer1 directly from the current layout

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        return view;
    }

    private void startStop() {
        if (countDownTimer == null) {
            // タイマーが動作していない場合、タイマーを開始
            String timeString = timerText.getText().toString();
            long timeInMillis = Long.parseLong(timeString) * 1000;
            countDownTimer = new CountDownTimer(timeInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCountdownText();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    // タイマーが終了したときの処理
                    countDownTimer = null;
                }
            }.start();

            startButton.setText("Stop");

        } else {
            // タイマーが動作している場合、タイマーを停止
            countDownTimer.cancel();
            countDownTimer = null;
            startButton.setText("Start");
        }
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.time1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_route3_to_navigation_route2);
            }
        });

        Button btn1 = view.findViewById(R.id.route1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_route3_to_navigation_route4);
            }
        });

        Button bt = view.findViewById(R.id.start1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_route3_to_navigation_route7);
            }
        });
    }
}



