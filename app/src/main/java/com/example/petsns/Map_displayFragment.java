//package com.example.petsns;
//import androidx.lifecycle.ViewModelProvider;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//
//public class Map_displayFragment extends Fragment {
//
//    private MapDisplayViewModel mViewModel;
//
//    public static Map_displayFragment newInstance() {
//        return new Map_displayFragment();
//    }
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_main, container, false);
//        if (savedInstanceState == null) {
//            // Fragmentのトランザクションを開始して、MapFragmentを追加
//            FragmentManager fragmentManager = getChildFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragmentContainer, new MapFragment());
//            fragmentTransaction.commit();
//        }
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MapDisplayViewModel.class);
//        // TODO: Use the ViewModel
//    }
//
//}