package com.example.dell.coursetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.presenter.CoursePresenter;
import com.example.dell.coursetable.presenter.CoursePresenterImpl;
import com.example.dell.coursetable.view.CourseTableViewImpl;

public class MainActivity extends AppCompatActivity {



    private Button loginButton;
    private EditText idText;
    private EditText passwordText;

    private EditText dayText;
    private EditText timeText;
    private Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton=(Button) findViewById(R.id.button_1);
        idText = (EditText) findViewById(R.id.text_id);
        passwordText = (EditText) findViewById(R.id.text_password);
        dayText=(EditText)findViewById(R.id.text_day);
        timeText=(EditText) findViewById(R.id.text_time);
        getButton=(Button) findViewById(R.id.button_2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }


}
