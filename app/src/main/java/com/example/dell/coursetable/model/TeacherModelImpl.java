package com.example.dell.coursetable.model;

import com.example.dell.coursetable.gson.Teacher;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dell on 2018/2/10.
 */

public interface TeacherModelImpl {
    public void getTeacherByName(String name)throws IOException, ExecutionException, InterruptedException ;
    public boolean createTeacher(String teacherName)throws IOException, ExecutionException , InterruptedException ;
}
