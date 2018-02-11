package com.example.dell.coursetable.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.dell.coursetable.userdata.UserInfo;

/**
 * Created by 田雍恺 on 2018/2/10.
 */

public class UserModel {
    private static UserModel instance;
    private UserModel(){

    }
    public static UserModel getInstance(){
        if(instance == null){
            instance = new UserModel();
        }
        return instance;
    }

    /**
     * 保存用户信息
     * @param context
     * @param username
     * @param password
     */
    public void saveUserInfo(Context context, String username, String password){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME",username);
        editor.putString("PASSWORD",password);
        editor.commit();
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public UserInfo getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME",""));
        userInfo.setPassword(sp.getString("PASSWORD",""));
        return userInfo;
    }

    public boolean hasUserInfo(Context context){
        UserInfo userInfo = getUserInfo(context);
        if(userInfo != null){
            if((!TextUtils.isEmpty(userInfo.getUserName()))&&(!TextUtils.isEmpty(userInfo.getPassword()))){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}
