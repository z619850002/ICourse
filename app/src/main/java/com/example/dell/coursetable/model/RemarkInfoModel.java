package com.example.dell.coursetable.model;

import com.example.dell.coursetable.gson.Course;
import com.example.dell.coursetable.gson.Remark;
import com.example.dell.coursetable.gson.User;
import com.example.dell.coursetable.webutil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by dell on 2018/2/11.
 */

public class RemarkInfoModel implements RemarkInfoModelImpl {

    private List<Remark>  remarkList;


    @Override
    public void getCommentListByCourse(String name, String teacher) throws IOException, ExecutionException, InterruptedException {

        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/comments/courses/nameandteacher/"+name+"&"+teacher);
        Gson gson=new Gson();
        List<Remark> privateRemarkList=gson.fromJson(response , new TypeToken<List<Remark>>(){}.getType());
        remarkList=privateRemarkList;
    }

    @Override
    public void getCommentListByUser(String id) throws IOException, ExecutionException, InterruptedException {
        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/comments/user/id/"+id);
        Gson gson=new Gson();
        List<Remark> privateRemarkList=gson.fromJson(response , new TypeToken<List<Remark>>(){}.getType());
        remarkList=privateRemarkList;
    }

    @Override
    public boolean addRemark(String content , String examDifficulty ,String courseLoad ,String practicability ,String enjoyment ,String teacherScore ,String studentNumber ,String teacherInfo ,String courseName) throws IOException, ExecutionException, InterruptedException {
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        requestBodyBuilder.add("Content" , content);
        requestBodyBuilder.add("examDifficulty" , examDifficulty);
        requestBodyBuilder.add("courseLoad" , courseLoad);
        requestBodyBuilder.add("practicability" , practicability);
        requestBodyBuilder.add("enjoyment" , enjoyment);
        requestBodyBuilder.add("teacherScore",teacherScore);
        UserInfoModel user=new UserInfoModel();
        user.initDataByStudentNumber(studentNumber);
        if (user.getUser()==null)
        {
            return false;
        }
        requestBodyBuilder.add("userInfo",user.getUser().getUserId().toString());
        requestBodyBuilder.add("teacherInfo",teacherInfo);
        requestBodyBuilder.add("courseName",courseName);
        RequestBody formBody=requestBodyBuilder.build();
        String response = HttpUtil.sendPostRequest(HttpUtil.host+"/comments" , formBody);
        if (!response.equals("null"))
        {
            return true;
        }
        return false;
    }

    public RemarkInfoModel() {
        remarkList=new ArrayList<Remark>();
    }

    public List<Remark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<Remark> remarkList) {
        this.remarkList = remarkList;
    }
}
