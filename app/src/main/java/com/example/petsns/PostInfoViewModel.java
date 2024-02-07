package com.example.petsns;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class PostInfoViewModel extends ViewModel {
    private String sentence=null;
    private Uri image=null;

    public void setSentence(String sentence){
        this.sentence=sentence;
    }
    public String getSentence(){
        return this.sentence;
    }

    public void setImage(Uri image){
        this.image=image;
    }
    public Uri getImage(){
        return image;
    }

    public void postCancel(){
        sentence=null;
        image=null;
    }


}
