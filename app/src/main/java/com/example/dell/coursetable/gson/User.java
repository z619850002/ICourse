package com.example.dell.coursetable.gson;

import java.util.Set;

/**
 * Created by dell on 2018/2/10.
 */

public class User {

    private Integer userId;

    //用户名
    private String userName;

    // private String password;
    private String studentNumber;

    //昵称
    private String nickName;

    //邮箱
    private String email;

    //积分
    private Integer integral;

    //地区
    private String area;

    private Set<Remark> remarkSet;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Set<Remark> getRemarkSet() {
        return remarkSet;
    }

    public void setRemarkSet(Set<Remark> remarkSet) {
        this.remarkSet = remarkSet;
    }
}
