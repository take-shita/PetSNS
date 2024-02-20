package com.example.petsns;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.petsns.ui.login_signup.SigupViewModel;
import com.example.petsns.ui.route.RouteViewModel;
import com.example.petsns.ui.setting.TagViewModel;

public class MyApplication extends Application {
    private SigupViewModel sigupViewModel;
    private  TagViewModel tagviewmodel;
    private RouteViewModel routeViewModel;
    private TagPostViewModel tagPostViewModel;
    private TagSearchViewModel tagSearchViewModel;
    private PostInfoViewModel postInfoViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        sigupViewModel =new ViewModelProvider.AndroidViewModelFactory(this).create(SigupViewModel.class);
        tagviewmodel =new ViewModelProvider.AndroidViewModelFactory(this).create(TagViewModel.class);
        tagPostViewModel =new ViewModelProvider.AndroidViewModelFactory(this).create(TagPostViewModel.class);
        routeViewModel=new ViewModelProvider.AndroidViewModelFactory(this).create(RouteViewModel.class);
        tagSearchViewModel=new ViewModelProvider.AndroidViewModelFactory(this).create(TagSearchViewModel.class);
        postInfoViewModel=new ViewModelProvider.AndroidViewModelFactory(this).create(PostInfoViewModel.class);
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

    public RouteViewModel getSharedRouteViewModel(){
        return routeViewModel;
    }

    public TagSearchViewModel getSharedTagSearchViewModel(){return tagSearchViewModel;}

    public PostInfoViewModel getSharedPostInfoViewModel(){return postInfoViewModel;}

}
