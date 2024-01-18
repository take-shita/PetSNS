package com.example.petsns;

import java.sql.Timestamp;

public class TestPost {
    private String id;
    private String sentence;
    private String imageUrl;
    private String icon;
    private Timestamp timestamp;

    // ゲッター・セッターなど必要な処理を実装
    public String getid(){
        return id;
    }
    public String getSentence(){
        return sentence;
    }
    public String getImageUrl(){ return imageUrl;}
    public String getIcon(){ return icon;}
    public void setIcon(String icon){this.icon=icon;}
    public Timestamp timestamp(){ return  timestamp;}
}
