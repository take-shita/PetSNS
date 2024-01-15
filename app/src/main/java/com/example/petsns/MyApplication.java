package com.example.petsns;

import android.app.Application;
import androidx.lifecycle.ViewModelProvider;

import com.example.petsns.ui.setting.TagViewModel;

public class MyApplication extends Application {
    private SigupViewModel sigupViewModel;
    private  TagViewModel tagviewmodel;

    private TagPostViewModel tagPostViewModel;
    @Override
    public void onCreate() {
        super.onCreate();
        sigupViewModel =new ViewModelProvider.AndroidViewModelFactory(this).create(SigupViewModel.class);
        tagviewmodel =new ViewModelProvider.AndroidViewModelFactory(this).create(TagViewModel.class);
        tagPostViewModel =new ViewModelProvider.AndroidViewModelFactory(this).create(TagPostViewModel.class);
    }

    public SigupViewModel getSharedViewModel() {

        return sigupViewModel;
    }

    public TagViewModel getSharedTagViewModel(){
        return  tagviewmodel;
    }

    public TagPostViewModel getSharedTagPostViewModel(){
        return tagPostViewModel;
    }

}
