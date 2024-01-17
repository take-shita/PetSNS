package com.example.petsns.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petsns.R;

public class profile_other_reportcheckFragment extends Fragment {

    private ProfileOtherReportcheckViewModel mViewModel;

    public static profile_other_reportcheckFragment newInstance() {
        return new profile_other_reportcheckFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_other_reportcheck, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileOtherReportcheckViewModel.class);
        // TODO: Use the ViewModel
    }

}