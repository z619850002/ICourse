package com.example.dell.coursetable.model;


import com.example.dell.coursetable.gson.Course;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Kyrie
 * @version 1.0
 * @description 接口
 * @date 2018/2/10
 */

public interface CourseInfoModelImpl {
    public void getCoursesByName(String name)throws IOException, ExecutionException , InterruptedException ;
    public void getCoursesByTeacher(String teacherName)throws IOException, ExecutionException , InterruptedException ;
    public void getAllCourses()throws IOException, ExecutionException , InterruptedException ;
    public void getCourseByNameAndTeacher(String name , String teacherName) throws IOException , ExecutionException , InterruptedException;
    public boolean createNewCourse(String courseName , String teacherName) throws IOException, ExecutionException , InterruptedException ;
    public void getCoursesByNamesAndTeacahers(String courseNames ,String teacherNames) throws IOException , ExecutionException , InterruptedException;
}
