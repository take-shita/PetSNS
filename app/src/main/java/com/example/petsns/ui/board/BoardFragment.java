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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.MainActivity;
import com.example.petsns.R;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BoardFragment extends Fragment {

    private BoardViewModel mViewModel;

    public static BoardFragment newInstance() {
        return new BoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BoardViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Button Question = view.findViewById(R.id.Q_btn);

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

                dialog.show();
            }
        });

        Button prof_bt = view.findViewById(R.id.Answer_btn);
        prof_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_board_to_navigation_Board_Chat);
            }
        });

//        super.onViewCreated(view, savedInstanceState);
//        Button bt_judge = view.findViewById(R.id.button12);
//        bt_judge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Navigation.findNavController(v).navigate(R.id.action_navigation_board_to_navigation_sample);
//
//            }
//
//        });
    }
}