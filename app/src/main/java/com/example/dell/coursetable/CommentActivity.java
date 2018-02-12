package com.example.dell.coursetable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dell.coursetable.adapter.CommentAdapter;
import com.example.dell.coursetable.gson.Remark;
import com.example.dell.coursetable.presenter.CommentPresenter;
import com.example.dell.coursetable.presenter.CommentPresenterImpl;
import com.example.dell.coursetable.view.CommentViewImpl;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements CommentViewImpl,View.OnClickListener{

    private List<Remark> remarkList = new ArrayList<>();
    private CommentPresenterImpl commentPresenter;
    private TextView commentBack;
    private CommentAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentPresenter = new CommentPresenter(CommentActivity.this);
        Bundle bundle = getIntent().getExtras();
        initView();
        bindOnClickListener();
        commentPresenter.update(bundle.getString("name"), bundle.getString("teacher"));
    }

    private void initView(){
        commentBack = (TextView)findViewById(R.id.comment_back);
        recyclerView = (RecyclerView) findViewById(R.id.course_comment);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    private void bindOnClickListener(){
        commentBack.setOnClickListener(this);
    }
    @Override
    public void showData(final List<Remark> List) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                remarkList = List;
                adapter = new CommentAdapter(remarkList);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_back:
                finish();
                break;
                default:
                    break;
        }
    }
}
