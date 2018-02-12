package com.example.dell.coursetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.coursetable.model.UserModel;
import com.example.dell.coursetable.presenter.EvaluatePresenter;
import com.example.dell.coursetable.presenter.EvaluatePresenterImpl;
import com.example.dell.coursetable.view.EvaluateViewImpl;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener, EvaluateViewImpl{

    private RatingBar diffRatingBar;
    private RatingBar loadRatingBar;
    private RatingBar pracRatingBar;
    private RatingBar interestRatingBar;
    private RatingBar teacherRating;
    private TextView back;
    private TextView courseName;
    private TextView courseTeacher;
    private TextView commit;
    private EvaluatePresenterImpl presenter;
    private EditText addContent;
    private int[] scores = new int[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        presenter = new EvaluatePresenter(EvaluateActivity.this);
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
        commit = (TextView)findViewById(R.id.commit);
        addContent = (EditText)findViewById(R.id.add_content);


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
                scores[0] =(int) ratingCount;
            }
        });
        loadRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[1] = (int)ratingCount;
            }
        });
        pracRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[2] = (int)ratingCount;
            }
        });
        interestRatingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[3]=(int)ratingCount;
            }
        });
        teacherRating.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                scores[4]=(int)ratingCount;
            }
        });
        back.setOnClickListener(this);
        commit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.evaluate_back:
                finish();
                break;
            case R.id.commit:
                commit();
                default:
                    break;
        }
    }

    private void commit(){
        presenter.show();
    }

    @Override
    public void commitEvaluate() {
        presenter.update(addContent.getText().toString(), scores[0]+"", scores[1]+"",scores[2]+"",scores[3]+"",
                scores[4]+"", UserModel.getInstance().getUserInfo(this).getUserName(), courseTeacher.getText().toString(), courseName.getText().toString());
        finish();
    }
}
