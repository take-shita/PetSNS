package com.example.petsns;

import androidx.lifecycle.ViewModel;

public class PostInfoViewModel extends ViewModel {
    private String sentence="sample";


    public String getSentence(){
        return this.sentence;
    }
}
