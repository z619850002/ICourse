package com.example.dell.coursetable.userdata;

/**
 * Created by 田雍恺 on 2018/2/10.
 */

public class UserInfo {
    private String userName;
    private String password;
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
}