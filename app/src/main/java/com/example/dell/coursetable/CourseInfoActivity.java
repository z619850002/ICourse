package com.example.dell.coursetable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.coursetable.gson.Course;
import com.example.dell.coursetable.gson.Teacher;
import com.example.dell.coursetable.presenter.CommentPresenterImpl;
import com.example.dell.coursetable.presenter.CourseInfoPresenter;
import com.example.dell.coursetable.view.CourseInfoViewImpl;


public class CourseInfoActivity extends AppCompatActivity implements View.OnClickListener,CourseInfoViewImpl{

    private TextView backBtn;
    private TextView evaluateBtn;
    private TextView courseName;
    private TextView courseRoom;
    private TextView courseStep;
    private TextView courseTeacher;
    private TextView courseWeek;
    private RadarView courseRadar;
    private RelativeLayout commentInfo;
    private CommentPresenterImpl cPresenter;
    private ProgressBar progressBar;
    private double[] scores = new double[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);
        cPresenter = new CourseInfoPresenter(CourseInfoActivity.this);
        initView();
        bindClickListener();
    }
    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        cPresenter.update(courseName.getText().toString(), courseTeacher.getText().toString());
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
        commentInfo = (RelativeLayout)findViewById(R.id.comment);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = this.getIntent().getExtras();
        int start = Integer.parseInt(bundle.getString("start"));
        int step = Integer.parseInt(bundle.getString("step"));
        String week = bundle.getString("week");
        String begin = week.substring(week.indexOf('[')+1,week.indexOf('-'));
        String end = week.substring(week.indexOf('-')+1,week.indexOf(']'));

        cPresenter.update(bundle.getString("name"), bundle.getString("teacher"));
        courseName.setText(bundle.getString("name"));
        courseRoom.setText(bundle.getString("room"));
        courseStep.setText(start+"-"+(start+step-1));
        courseTeacher.setText(bundle.getString("teacher"));
        courseWeek.setText(begin+"-"+end+"周");


    }

    private void bindClickListener(){
        backBtn.setOnClickListener(this);
        evaluateBtn.setOnClickListener(this);
        commentInfo.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_back:
                finish();
                break;
            case R.id.evaluate:
                showEvaluate();
                break;
            case R.id.comment:
                showCommentInfo();
                break;
                default:
                    break;
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

    private void showCommentInfo(){
        Bundle bundle = new Bundle();
        bundle.putString("name", courseName.getText().toString());
        bundle.putString("teacher",courseTeacher.getText().toString());
        Intent intent = new Intent(CourseInfoActivity.this, CommentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showScore(final Course course, final Teacher teacher) {

        final Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scores[0] = (double)course.getExamDifficulty();
                scores[1] = (double)course.getCourseLoad();
                scores[2] = (double)course.getPracticability();
                scores[3] = (double)course.getEnjoyment();
                scores[4] = (double)teacher.getScore();
                courseRadar.setData(scores);
                courseRadar.invalidate();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showError() {

    }
}
