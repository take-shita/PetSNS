package com.example.petsns.ui.login_signup;

import androidx.lifecycle.ViewModel;

public class SigupViewModel extends ViewModel {
    private String userId=null;
    private String userName=null;
    private String email=null;
    private String password=null;

    // ゲッターとセッター
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
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
