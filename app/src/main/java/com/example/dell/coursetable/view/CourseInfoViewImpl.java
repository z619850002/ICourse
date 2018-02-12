package com.example.dell.coursetable.view;

import com.example.dell.coursetable.gson.Course;
import com.example.dell.coursetable.gson.Teacher;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public interface CourseInfoViewImpl {
    public void showScore(Course course, Teacher teacher);
    public void showError();
}
