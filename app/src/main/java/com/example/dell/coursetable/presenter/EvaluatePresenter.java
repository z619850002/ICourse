package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.RemarkInfoModel;
import com.example.dell.coursetable.model.TeacherModel;
import com.example.dell.coursetable.view.EvaluateViewImpl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public class EvaluatePresenter implements EvaluatePresenterImpl{

    EvaluateViewImpl eView;
    RemarkInfoModel rModel;
    TeacherModel tModel;

    public EvaluatePresenter(EvaluateViewImpl eView){
        this.eView = eView;
        this.rModel = new RemarkInfoModel();
        this.tModel = new TeacherModel();

    }

    @Override
    public void update(final String content, final String examDifficulty, final String courseLoad, final String practicability, final String enjoyment, final String teacherScore, final String studentNumber, final String teacherInfo, final String courseName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    rModel.addRemark(content,examDifficulty, courseLoad, practicability, enjoyment, teacherScore, studentNumber, teacherInfo, courseName);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void show() {
        eView.commitEvaluate();
    }
}
