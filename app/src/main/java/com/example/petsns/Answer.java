package com.example.petsns;

import java.sql.Timestamp;

public class Answer {
    private String answer_content;
    private String ques_id;
    private Timestamp timestamp;
    private String user_id;

    // ゲッター・セッターなど必要な処理を実装
    public String getAnswer_content(){
        return answer_content;
    }
    public String getQues_id(){ return ques_id; }
    public Timestamp timestamp(){ return timestamp; }
    public String getUser_id(){ return user_id; }
}