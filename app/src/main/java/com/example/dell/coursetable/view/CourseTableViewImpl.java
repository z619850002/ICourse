package com.example.dell.coursetable.view;

import com.example.dell.coursetable.coursedata.CourseList;


/**
 * @author Kyrie
 * @version 1.0
 * @description 课程表相关视图
 * @date 2018/1/28
 */
public interface CourseTableViewImpl {
    public void showData(CourseList[][] courseLists_d , CourseList[][] courseLists_s);
    public void showError(String message);
}
