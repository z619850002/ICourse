package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.LoginActivity;
import com.example.dell.coursetable.model.CourseModel;
import com.example.dell.coursetable.view.CourseTableViewImpl;
import com.example.dell.coursetable.view.LoginViewImpl;

/**
 * Created by dell on 2018/2/3.
 */

public class LoginPresenter implements CoursePresenterImpl {


    LoginViewImpl lView;
    CourseModel cModel;


    public LoginPresenter(LoginViewImpl lView) {
        this.lView = lView;
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
        lView.showData();
    }


    public void showError(String s)
    {
        lView.showError(s);
    }
}
