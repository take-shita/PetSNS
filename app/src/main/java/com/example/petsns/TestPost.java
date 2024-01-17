package com.example.petsns;

public class TestPost {
    private String id;
    private String sentence;
    private String imageUrl;

    private String icon;

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

}
