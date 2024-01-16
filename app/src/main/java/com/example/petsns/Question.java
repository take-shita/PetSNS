package com.example.petsns;

import java.sql.Timestamp;

public class Question {
    private String ques_content;
    private Timestamp ques_date;
    private String ques_title;
    private String user_id;

    // ゲッター・セッターなど必要な処理を実装
    public String getQues_content(){
        return ques_content;
    }
    public String getQues_title(){ return ques_title; }
    public Timestamp getQues_date(){ return ques_date; }
    public String getUser_id(){ return user_id; }
}
