package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.model.CourseSelectionModel;
import com.example.dell.coursetable.view.CourseSelViewImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by tyk on 2018/3/8.
 */

public class CourseSelPresenter implements CourseSelPresenterImpl {

    CourseSelViewImpl cView;
    static CourseSelectionModel cModel;

    public CourseSelPresenter(CourseSelViewImpl view){
        cView = view;
        if(cModel==null)
            cModel = new CourseSelectionModel();
    }
    @Override
    public void initList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cModel.initCourseSelection();
                    List<String> strings=new ArrayList<>();
                    for (List<CourseInformation>  ele: cModel.getCourseInformationList())
                    {
                        strings.add(ele.get(0).getName());
                    }
                    show(strings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void show(List<String> strings) {
        cView.showData(strings);
    }

    @Override
    public void change(List<String> strings) {
        List<List<CourseInformation>> list = new ArrayList<>();
        for(List<CourseInformation> ele:cModel.getCourseInformationList()){
            if(strings.contains(ele.get(0).getName()))
                list.add(ele);
        }
        cModel.setCourseInformationList(list);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cModel.beginSelect();
                    cView.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void select(final double examCoefficient , final double teacherCoefficient , final double practicibilityCoefficient , final double loadCoefficient , final double enjoymentCoefficient , final boolean morningClass , final boolean nightClass , final boolean concentratedClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cModel.sortCourseSelection(examCoefficient,teacherCoefficient,practicibilityCoefficient,loadCoefficient,enjoymentCoefficient,morningClass,nightClass,concentratedClass);
                cView.show();
            }
        }).start();
    }

    @Override
    public void showData() {
        cView.showCourse(cModel.getCourseSelectionList().get(0).getCourseLists_d(), cModel.getCourseSelectionList().get(0).getCourseLists_s());
    }
}
