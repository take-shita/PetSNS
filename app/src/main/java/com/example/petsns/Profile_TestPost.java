package com.example.petsns;
import com.google.firebase.Timestamp;
public class Profile_TestPost {
    private String id;
    private String name;
    private String sentence;
    private String imageUrl;
    private String icon;
    private Timestamp timestamp;

    public Profile_TestPost() {
    }

    public Profile_TestPost(String id, String name, String sentence, String imageUrl, String icon, Timestamp timestamp){
        this.id = id;
        this.name = name;
        this.sentence = sentence;
        this.imageUrl = imageUrl;
        this.icon = icon;
        this.timestamp = timestamp;
    }

    // ゲッター・セッターなど必要な処理を実装
    public String getid(){
        return id;
    }
    public String getname() { return name;}
    public String getSentence(){
        return sentence;
    }
    public String getImageUrl(){ return imageUrl;}
    public String geticon(){ return icon;}
    public void setIcon(String icon){this.icon=icon;}
    public Timestamp gettimestamp(){ return  timestamp;}
}

