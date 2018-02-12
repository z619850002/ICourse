package com.example.dell.coursetable.presenter;

import com.example.dell.coursetable.model.CourseModel;
import com.example.dell.coursetable.model.UserInfoModel;
import com.example.dell.coursetable.view.LoginViewImpl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dell on 2018/2/3.
 */

public class LoginPresenter implements CoursePresenterImpl {


    LoginViewImpl lView;
    CourseModel cModel;
    UserInfoModel uModel;


    public LoginPresenter(LoginViewImpl lView) {
        this.lView = lView;
        this.cModel = new CourseModel();
        this.uModel = new UserInfoModel();
    }

    @Override
    public void update(final String id, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (cModel.initData(id ,password))
                {
                    try {
                        uModel.createUser(id,password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
