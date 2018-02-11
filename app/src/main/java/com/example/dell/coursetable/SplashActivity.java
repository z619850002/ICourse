package com.example.dell.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dell.coursetable.model.UserModel;
import com.example.dell.coursetable.presenter.CoursePresenterImpl;
import com.example.dell.coursetable.presenter.SplashPresenter;
import com.example.dell.coursetable.userdata.DbUtils;
import com.example.dell.coursetable.view.SplashViewImpl;

public class SplashActivity extends AppCompatActivity implements SplashViewImpl{

    private static final int GO_HOME = 0;
    private static final int GO_LOGIN = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case GO_HOME:
                    CoursePresenterImpl coursePresenter = new SplashPresenter(SplashActivity.this);
                    coursePresenter.update(UserModel.getInstance().getUserInfo(SplashActivity.this).getUserName(), UserModel.getInstance().getUserInfo(SplashActivity.this).getPassword());
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }
        }
    };

    public void goLogin(){
        Intent intent2 = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent2);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DbUtils.createDb(this, "myCourse");
        if(UserModel.getInstance().hasUserInfo(this)){
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN,2000);
        }
    }

    @Override
    public void showDate() {
       Intent intent = new Intent(SplashActivity.this, TimeTableActivity.class);
       startActivity(intent);
       finish();
    }
    @Override
    public void showError(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashActivity.this , "失败" , Toast.LENGTH_LONG).show();
                goLogin();
            }
        });
    }
}
