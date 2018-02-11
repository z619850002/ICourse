package com.example.dell.coursetable.model;

import com.example.dell.coursetable.gson.User;
import com.example.dell.coursetable.webutil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * @author Kyrie
 * @version 1.0
 * @description  用户信息model
 * @date 2018/2/10
 */

public class UserInfoModel implements  UserInfoModelImpl {


    private User user;

    @Override
    public void initDataById(String id) throws IOException, ExecutionException, InterruptedException {
        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/users/id?userId="+id);
        Gson gson=new Gson();
        User privateUser = gson.fromJson(response , User.class);
        user = privateUser;
    }

    @Override
    public void initDataByName(String userName) throws IOException, ExecutionException , InterruptedException {
        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/users/name?userName="+userName);
        Gson gson=new Gson();
        List<User> privateUser = gson.fromJson(response , new TypeToken<List<User>>(){}.getType());
        if (privateUser!=null) {
            user = privateUser.get(0);
        }
        else
        {
            user=null;
        }
    }

    @Override
    public void initDataByStudentNumber(String studentNumber) throws IOException, ExecutionException, InterruptedException {
        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/users/studentnumber?studentNumber="+studentNumber);
        Gson gson=new Gson();
        User privateUser = gson.fromJson(response , User.class);
        user = privateUser;
    }

    @Override
    public boolean createUser(String studentNumber , String userName  ) throws IOException, ExecutionException, InterruptedException {

        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        requestBodyBuilder.add("studentNumber" , studentNumber);
        requestBodyBuilder.add("userName" , userName);
        RequestBody formBody=requestBodyBuilder.build();
        String response= HttpUtil.sendPostRequest(HttpUtil.host+"/users" , formBody);
        if (response.equals("true"))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(String studentNumber, String userName, String email, Integer integral, String area, String nickName) throws IOException, ExecutionException, InterruptedException {

        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        UserInfoModel m=new UserInfoModel();
        m.initDataByStudentNumber(studentNumber);
        if (m.getUser()!=null) {
            requestBodyBuilder.add("userId", m.getUser().getUserId().toString());
            requestBodyBuilder.add("userName", userName);
            requestBodyBuilder.add("studentNumber", studentNumber);
            requestBodyBuilder.add("email", email);
            requestBodyBuilder.add("integral", integral.toString());
            requestBodyBuilder.add("area", area);
            requestBodyBuilder.add("nickName", nickName);
            RequestBody formBody = requestBodyBuilder.build();
            String response = HttpUtil.sendPostRequest(HttpUtil.host + "/users/id", formBody);
            return true;
        }
        return false;
    }

    public UserInfoModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
