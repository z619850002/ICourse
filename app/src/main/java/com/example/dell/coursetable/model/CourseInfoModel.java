package com.example.dell.coursetable.model;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.gson.Course;
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
 * @author Kyrie
 * @version 1.0
 * @description  用于存储查询的课程信息的model
 * @date 2018/2/10
 */
public class CourseInfoModel implements  CourseInfoModelImpl {

    private Course courseInformation;
    private List<Course> courseList;



    //由于所有课程数量太庞大，因此最好不用
    @Override
    public void getAllCourses() {

    }


    @Override
    public void getCourseByNameAndTeacher(String name, String teacherName) throws IOException, ExecutionException , InterruptedException {
        String response=HttpUtil.sendGetRequest(HttpUtil.host+"/courses/name&teacher/"+name+"&"+teacherName);
        Gson gson=new Gson();
        Course course=gson.fromJson(response , Course.class);
        courseInformation=course;
    }



    @Override
    public void getCoursesByName(String name)throws IOException, ExecutionException , InterruptedException  {
        String response=HttpUtil.sendGetRequest(HttpUtil.host+"/courses/name/"+name);
        Gson gson=new Gson();
        List<Course> privateCourseList=gson.fromJson(response , new TypeToken<List<Course>>(){}.getType());
        courseList=privateCourseList;
    }


    @Override
    public void getCoursesByTeacher(String teacherName) throws IOException, ExecutionException , InterruptedException {
        String response=HttpUtil.sendGetRequest(HttpUtil.host+"/courses/teacher/"+teacherName);
        Gson gson=new Gson();
        List<Course> privateCourseList=gson.fromJson(response , new TypeToken<List<Course>>(){}.getType());
        courseList=privateCourseList;
    }

    @Override
    public boolean createNewCourse(String courseName ,String teacherName) throws IOException, ExecutionException, InterruptedException {

        /*@RequestParam("courseName") String courseName,
                            @RequestParam("examDifficulty") Integer examDifficulty,
                            @RequestParam("courseLoad") Integer courseLoad,
                            @RequestParam("practicability") Integer practicability,
                            @RequestParam("enjoyment") Integer enjoyment,
                            @RequestParam("teacherInfo")Teacher teacherId)*/

        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        requestBodyBuilder.add("courseName" , courseName);
        requestBodyBuilder.add("examDifficulty" , "5");
        requestBodyBuilder.add("courseLoad" , "5");
        requestBodyBuilder.add("practicability" , "5");
        requestBodyBuilder.add("enjoyment" , "5");
        requestBodyBuilder.add("teacherInfo",teacherName);
        RequestBody formBody=requestBodyBuilder.build();
        String response = HttpUtil.sendPostRequest(HttpUtil.host+"/courses" , formBody);
        if (response.equals("null"))
        {
            return false;
        }
        return true;
    }

    public CourseInfoModel() {
        this.courseList = new ArrayList<Course>();
    }

    public Course getCourseInformation() {
        return courseInformation;
    }

    public void setCourseInformation(Course courseInformation) {
        this.courseInformation = courseInformation;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
