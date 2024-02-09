//package com.example.petsns;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListView;
//
//import com.example.petsns.MyApplication;
//import com.example.petsns.R;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class profile_postFragment extends Fragment {
//
//    private ProfilePostViewModel mViewModel;
//
//    public static profile_postFragment newInstance() {
//        return new profile_postFragment();
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_profile_post, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ProfilePostViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//}