package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.CourseModel;
import com.example.dell.coursetable.view.SplashViewImpl;

/**
 * Created by 田雍恺 on 2018/2/10.
 */

public class SplashPresenter implements CoursePresenterImpl{

    SplashViewImpl sView;
    CourseModel cModel;

    public SplashPresenter(SplashViewImpl sView){
        this.sView = sView;
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
        sView.showDate();
    }

    public void showError(String s){
        sView.showError(s);
    }
}
