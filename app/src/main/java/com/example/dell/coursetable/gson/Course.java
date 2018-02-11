package com.example.dell.coursetable.gson;

import java.util.Set;

/**
 * Created by dell on 2018/2/10.
 */

public class Course {
    private Integer courseId;

    private String courseName;


    private Integer examDifficulty;

    private Integer courseLoad;

    private Integer practicability;

    private Integer enjoyment;

    private Teacher teacherInfo;

    private Set<Remark> remarkSet;

    public Course() {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getExamDifficulty() {
        return examDifficulty;
    }

    public void setExamDifficulty(Integer examDifficulty) {
        this.examDifficulty = examDifficulty;
    }

    public Integer getCourseLoad() {
        return courseLoad;
    }

    public void setCourseLoad(Integer courseLoad) {
        this.courseLoad = courseLoad;
    }

    public Integer getPracticability() {
        return practicability;
    }

    public void setPracticability(Integer practicability) {
        this.practicability = practicability;
    }

    public Integer getEnjoyment() {
        return enjoyment;
    }

    public void setEnjoyment(Integer enjoyment) {
        this.enjoyment = enjoyment;
    }

    public Teacher getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(Teacher teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public Set<Remark> getRemarkSet() {
        return remarkSet;
    }

    public void setRemarkSet(Set<Remark> remarkSet) {
        this.remarkSet = remarkSet;
    }
}
