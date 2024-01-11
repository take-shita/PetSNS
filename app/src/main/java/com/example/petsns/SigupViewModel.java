package com.example.petsns;

import androidx.lifecycle.ViewModel;

public class SigupViewModel extends ViewModel {
    private String userName="sample";
    private String email="sample";
    private String password="sample";

    // ゲッターとセッター
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        if(email.equals("")){
            return "a?";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
