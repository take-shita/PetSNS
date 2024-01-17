package com.example.petsns;

import java.sql.Timestamp;

public class Question {
    private String ques_content;
    private Timestamp timestamp;
    private String ques_title;
    private String user_id;
    private String documentID;

    // ゲッター・セッターなど必要な処理を実装
    public String getQues_content(){
        return ques_content;
    }
    public String getQues_title(){ return ques_title; }
    public Timestamp timestamp(){ return timestamp; }
    public String getUser_id(){ return user_id; }
    public String getDocumentID(){ return documentID; }
}
