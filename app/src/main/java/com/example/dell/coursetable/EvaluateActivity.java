package com.example.dell.coursetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener{

    private RatingBar diffRatingBar;
    private RatingBar loadRatingBar;
    private RatingBar pracRatingBar;
    private RatingBar interestRatingBar;
    private RatingBar teacherRating;
    private TextView back;
    private TextView courseName;
    private TextView courseTeacher;
    private double[] scores = new double[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initView();
        bindClickListener();
    }

    private void initView(){
        diffRatingBar = (RatingBar) findViewById(R.id.difficult_star);
        loadRatingBar = (RatingBar) findViewById(R.id.load_star);
        pracRatingBar = (RatingBar) findViewById(R.id.practicability_star);
        interestRatingBar = (RatingBar) findViewById(R.id.interest_star);
        teacherRating = (RatingBar) findViewById(R.id.teacher_star);
        back = (TextView) findViewById(R.id.evaluate_back);
        courseName = (TextView)findViewById(R.id.evaluate_course_name);
        courseTeacher = (TextView)findViewById(R.id.evaluate_teacher);

        diffRatingBar.setClickable(true);
        loadRatingBar.setClickable(true);
        pracRatingBar.setClickable(true);
        interestRatingBar.setClickable(true);
        teacherRating.setClickable(true);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        courseName.setText(bundle.getString("courseName"));
        courseTeacher.setText(bundle.getString("courseTeacher"));
    }

    private void bindClickListener(){
        diffRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[0] = ratingCount;
            }
        });
        loadRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[1] = ratingCount;
            }
        });
        pracRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[2] = ratingCount;
            }
        });
        interestRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[3]=ratingCount;
            }
        });
        teacherRating.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[4]=ratingCount;
            }
        });
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.evaluate_back:
                finish();
                break;
                default:
                    break;
        }
    }
}
