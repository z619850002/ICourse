package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.CourseModel;
import com.example.dell.coursetable.view.CourseTableViewImpl;


/**
 * @author Kyrie
 * @version 1.0
 * @description 课程相关表示器
 * @date 2018/1/28
 */
public class CoursePresenter implements CoursePresenterImpl {

    CourseTableViewImpl cView;
    CourseModel cModel;

    public CoursePresenter(CourseTableViewImpl cView) {
        this.cView = cView;
        this.cModel = new CourseModel();
    }

    @Override
    public void update(final String id, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (cModel.initData(id ,password))
                {
                    show();
                }
                else
                {
                    showError("失败");
                }
            }
        }).start();
    }

    @Override
    public void show() {
        cView.showData(cModel.getClassState_d() , cModel.getClassState_s());
    }
    public void showError(String s)
    {
        cView.showError(s);
    }
}
