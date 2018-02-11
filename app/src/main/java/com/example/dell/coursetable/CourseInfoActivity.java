package com.example.dell.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class CourseInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView backBtn;
    private TextView evaluateBtn;
    private TextView courseName;
    private TextView courseRoom;
    private TextView courseStep;
    private TextView courseTeacher;
    private TextView courseWeek;
    private RadarView courseRadar;
    private double[] scores = new double[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        initView();
        bindClickListener();
    }
    private void initView(){
        backBtn = (TextView) findViewById(R.id.info_back);
        evaluateBtn = (TextView)findViewById(R.id.evaluate);
        courseName = (TextView)findViewById(R.id.course_name);
        courseRoom = (TextView)findViewById(R.id.room_name);
        courseStep = (TextView)findViewById(R.id.step_num);
        courseTeacher = (TextView)findViewById(R.id.teacher_name);
        courseWeek = (TextView)findViewById(R.id.week_num);
        courseRadar = (RadarView)findViewById(R.id.course_radar);

        Bundle bundle = this.getIntent().getExtras();
        int start = Integer.parseInt(bundle.getString("start"));
        int step = Integer.parseInt(bundle.getString("step"));
        String week = bundle.getString("week");
        String begin = week.substring(week.indexOf('[')+1,week.indexOf('-'));
        String end = week.substring(week.indexOf('-')+1,week.indexOf(']'));

        courseName.setText(bundle.getString("name"));
        courseRoom.setText(bundle.getString("room"));
        courseStep.setText(start+"-"+(start+step-1));
        courseTeacher.setText(bundle.getString("teacher"));
        courseWeek.setText(begin+"-"+end+"周");

        scores[0]=4.5;
        scores[1]=4.2;
        scores[2]=4.88;
        scores[3]=4.211;
        scores[4]=4.566;
        courseRadar.setData(scores);//设置数据
    }

    private void bindClickListener(){
        backBtn.setOnClickListener(this);
        evaluateBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_back:
                finish();
                break;
            case R.id.evaluate:
                showEvaluate();
        }
    }

    private void showEvaluate(){
        Bundle bundle = new Bundle();
        bundle.putString("courseName",courseName.getText().toString());
        bundle.putString("courseTeacher",courseTeacher.getText().toString());
        Intent intent = new Intent(CourseInfoActivity.this, EvaluateActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
