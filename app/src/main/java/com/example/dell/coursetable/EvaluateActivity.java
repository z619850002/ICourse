package com.example.dell.coursetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EvaluateActivity extends AppCompatActivity {

    private RatingBar diffRatingBar;
    private RatingBar loadRatingBar;
    private RatingBar pracRatingBar;
    private RatingBar interestRatingBar;
    private RatingBar teacherRating;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        diffRatingBar = (RatingBar) findViewById(R.id.difficult_star);
        loadRatingBar = (RatingBar) findViewById(R.id.load_star);
        pracRatingBar = (RatingBar) findViewById(R.id.practicability_star);
        interestRatingBar = (RatingBar) findViewById(R.id.interest_star);
        teacherRating = (RatingBar) findViewById(R.id.teacher_star);
        back = (TextView) findViewById(R.id.evaluate_back);
        diffRatingBar.setClickable(true);
        loadRatingBar.setClickable(true);
        pracRatingBar.setClickable(true);
        interestRatingBar.setClickable(true);
        teacherRating.setClickable(true);
        diffRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
            }
        });
        loadRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
            }
        });
        pracRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
            }
        });
        interestRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
            }
        });
        teacherRating.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
