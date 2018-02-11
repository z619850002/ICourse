package com.example.dell.coursetable.model;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dell on 2018/2/11.
 */

public interface RemarkInfoModelImpl {
    public void getCommentListByCourse(String name , String teacher) throws IOException , ExecutionException , InterruptedException;
    public void getCommentListByUser(String id)throws IOException , ExecutionException , InterruptedException;
    public boolean addRemark(String content , String examDifficulty ,String courseLoad ,String practicability ,String enjoyment ,String teacherScore ,String userInfo ,String teacherInfo ,String courseName)throws IOException , ExecutionException , InterruptedException;
}
