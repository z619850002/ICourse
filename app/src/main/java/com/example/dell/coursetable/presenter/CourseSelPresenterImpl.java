package com.example.dell.coursetable.presenter;

import java.util.List;

/**
 * Created by tyk on 2018/3/8.
 */

public interface CourseSelPresenterImpl {
    public void initList();
    public void show(List<String> strings);
    public void change(List<String> strings);
    public void select(double examCoefficient , double teacherCoefficient , double practicibilityCoefficient , double loadCoefficient ,double enjoymentCoefficient , boolean morningClass , boolean nightClass , boolean concentratedClass);
    public void showData();
}
