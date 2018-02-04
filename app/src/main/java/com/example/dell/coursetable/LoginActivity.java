package com.example.dell.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.coursetable.presenter.CoursePresenterImpl;
import com.example.dell.coursetable.presenter.LoginPresenter;
import com.example.dell.coursetable.view.LoginViewImpl;


public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private CoursePresenterImpl cPresenter;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void initView(){
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        cPresenter=new LoginPresenter(LoginActivity.this);
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                cPresenter.update(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

    }


    @Override
    public void showData() {
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        Intent i2 = new Intent(LoginActivity.this,TimeTableActivity.class);
        startActivity(i2);
    }

    @Override
    public void showError(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this , "失败,请核对信息" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
