package com.example.dell.coursetable.coursedata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/2/3.
 */

public class CourseList {

    List<CourseInformation> courseInformations= new ArrayList<>();

    public List<CourseInformation> getCourseInfomations() {
        return courseInformations;
    }

    public void setClassInfomations(List<CourseInformation> courseInformations) {
        this.courseInformations = courseInformations;
    }
}
