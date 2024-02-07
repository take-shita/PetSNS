package com.example.petsns.ui.route;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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

import com.example.petsns.MyApplication;
import com.example.petsns.R;

public class routetimeFragment extends Fragment {

    private TextView timerTextView;
    private Button startButton;
    private EditText timerText;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 初期値は 1 分（60,000 ミリ秒）
    private String beforeText;
    private String afterText;
    int rootMinute=3000;
    String hourString;
    int hourInt;

    String minuteString;
    int minuteInt;

    public routetimeFragment() {
        // Required empty public constructor
    }

    private RoutetimeViewModel mViewModel;
    private RouteViewModel viewModel;
    public static routetimeFragment newInstance() {
        return new routetimeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_routetime, container, false);
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        if (myApplication != null) {
            viewModel = myApplication.getSharedRouteViewModel();
        } else {
            // エラーハンドリング
        }
        timerText = view.findViewById(R.id.timer);
        startButton = view.findViewById(R.id.start1);

        timerText.setGravity(Gravity.END);

        timerText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String afterText=timerText.getText().toString();
                if(afterText.length()==3){
                    timerText.setText(afterText.charAt(0)+":"+afterText.charAt(1)+afterText.charAt(2));
                    timerText.setSelection(timerText.length());
                }
                    if (afterText.length() == 5) {
                        timerText.setText(" "+String.valueOf(afterText.charAt(0)) + String.valueOf(afterText.charAt(2)) + ":" + String.valueOf(afterText.charAt(3)) + String.valueOf(afterText.charAt(4)));
                        timerText.setSelection(timerText.length());
                    }

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timerTextString=timerText.getText().toString();
                if(timerTextString.length()==5){
                    hourString=String.valueOf(timerTextString.charAt(0))+String.valueOf(timerTextString.charAt(1));
                    minuteString=String.valueOf(timerTextString.charAt(3))+String.valueOf(timerTextString.charAt(4));

                    hourInt=Integer.parseInt(hourString);
                    minuteInt=Integer.parseInt(minuteString);

                    minuteConversion(hourInt,minuteInt);
                    viewModel.setDistance(rootMinute);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_routetime_to_navigation_route_view);
                }else if(timerTextString.length()==4){
                    hourString=String.valueOf(timerTextString.charAt(0));
                    minuteString=String.valueOf(timerTextString.charAt(2))+String.valueOf(timerTextString.charAt(3));

                    hourInt=Integer.parseInt(hourString);
                    minuteInt=Integer.parseInt(minuteString);
                    minuteConversion(hourInt,minuteInt);
                    viewModel.setDistance(rootMinute);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_routetime_to_navigation_route_view);
                }else if(timerTextString.length()==2){
                    minuteString= timerTextString;

                    hourInt=0;
                    minuteInt=Integer.parseInt(minuteString);

                    minuteConversion(hourInt,minuteInt);
                    viewModel.setDistance(rootMinute);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_routetime_to_navigation_route_view);
                }else if(timerTextString.length()==1){
                    minuteString= timerTextString;

                    hourInt=0;
                    minuteInt=Integer.parseInt(minuteString);
                    minuteConversion(hourInt,minuteInt);
                    viewModel.setDistance(rootMinute);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_routetime_to_navigation_route_view);
                }

            }
        });

        return view;
    }
    public void minuteConversion(int hour,int minute){
        this.rootMinute=(hour*60+minute)*60;
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.setButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_routepopup);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 400; // 幅を変更
                params.height = 300; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                Button a = dialog.findViewById(R.id.distance1);
                Button b = dialog.findViewById(R.id.time);

                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(view).navigate(R.id.action_navigation_routepopup_to_navigation_route);
                        dialog.dismiss();
                    }
                });
//                dialog.show();
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        Button btnFavorite = view.findViewById(R.id.btnFavorite);//投稿削除確認ポップアップ画面
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = requireContext();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.fragment_routepopup2);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = 811; // 幅を変更
                params.height = 900; // 高さを変更
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

//                Button c = dialog.findViewById(R.id.place);
//                Button d = dialog.findViewById(R.id.favorite);
//
//                c.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
////                dialog.show();
//                d.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Navigation.findNavController(view).navigate(R.id.action_navigation_routepopup2_to_navigation_routefavorite);
//                        dialog.dismiss();
//                    }
//                });
                dialog.show();
            }
        });
    }
}
