package com.example.dell.coursetable.model;

import com.example.dell.coursetable.gson.Remark;
import com.example.dell.coursetable.gson.Teacher;
import com.example.dell.coursetable.webutil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by dell on 2018/2/10.
 */

public class TeacherModel implements  TeacherModelImpl {

    private Teacher teacher;

    @Override
    public void getTeacherByName(String name) throws IOException, ExecutionException, InterruptedException {
        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/teachers/name/"+name);
        Gson gson=new Gson();
        List<Teacher> privateTeacher = gson.fromJson(response , new TypeToken<List<Teacher>>(){}.getType());
        this.teacher=privateTeacher.get(0);
    }


    @Override
    public List<Teacher> getTeachersByNames(String names) throws IOException, ExecutionException, InterruptedException {

        String response= HttpUtil.sendGetRequest(HttpUtil.host+"/teachers/names/"+names);
        Gson gson=new Gson();
        List<Teacher> privateTeacher = gson.fromJson(response , new TypeToken<List<Teacher>>(){}.getType());
        return privateTeacher;
    }

    @Override
    public boolean createTeacher(String teacherName) throws IOException, ExecutionException , InterruptedException {

        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        requestBodyBuilder.add("teacherName", teacherName);
        requestBodyBuilder.add("score", "0");
        RequestBody formBody = requestBodyBuilder.build();
        String response = HttpUtil.sendPostRequest(HttpUtil.host + "/teachers", formBody);
        if (!response .equals("null")) {
            return true;
        }
        return false;
    }

    public TeacherModel() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
