package com.example.petsns;

import android.app.Application;
import androidx.lifecycle.ViewModelProvider;

public class MyApplication extends Application {
    private SigupViewModel sigupViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        sigupViewModel =new ViewModelProvider.AndroidViewModelFactory(this).create(SigupViewModel.class);
    }

    public SigupViewModel getSharedViewModel() {

        return sigupViewModel;
    }
}
