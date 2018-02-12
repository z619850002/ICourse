package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.CourseInfoModel;
import com.example.dell.coursetable.model.RemarkInfoModel;
import com.example.dell.coursetable.model.TeacherModel;
import com.example.dell.coursetable.view.CourseInfoViewImpl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by 田雍恺 on 2018/2/12.
 */

public class CourseInfoPresenter implements CommentPresenterImpl {
    CourseInfoViewImpl cView;
    RemarkInfoModel rModel;
    CourseInfoModel cModel;
    TeacherModel tModel;

    public CourseInfoPresenter(CourseInfoViewImpl cView){
        this.cView = cView;
        rModel = new RemarkInfoModel();
        cModel = new CourseInfoModel();
        tModel = new TeacherModel();
    }

    @Override
    public void show() {
        cView.showScore(cModel.getCourseInformation(), tModel.getTeacher());
    }

    @Override
    public void update(final String name, final String teacher){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cModel.getCourseByNameAndTeacher(name, teacher);
                    if(cModel.getCourseInformation()==null){
                        cModel.createNewCourse(name,teacher);
                        cModel.getCourseByNameAndTeacher(name,teacher);
                    }
                    tModel.getTeacherByName(teacher);
                    if(tModel.getTeacher()==null){
                        tModel.createTeacher(teacher);
                        tModel.getTeacherByName(teacher);
                    }
                    show();
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
}
