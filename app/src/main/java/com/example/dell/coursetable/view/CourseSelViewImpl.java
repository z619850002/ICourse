package com.example.dell.coursetable.view;

import com.example.dell.coursetable.coursedata.CourseList;

import java.util.List;

/**
 * Created by tyk on 2018/3/8.
 */

public interface CourseSelViewImpl {
    public void showData(List<String> strings);
    public void showCourse(CourseList[][] courseLists_d  , CourseList[][] courseLists_s);
    public void show();
}
