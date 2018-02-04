package com.example.dell.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class CourseInfoActivity extends AppCompatActivity {

    private TextView backBtn;
    private TextView evaluateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        backBtn = (TextView) findViewById(R.id.info_back);
        evaluateBtn = (TextView)findViewById(R.id.evaluate);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        evaluateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseInfoActivity.this, EvaluateActivity.class);
                startActivity(intent);
            }
        });
    }
}
