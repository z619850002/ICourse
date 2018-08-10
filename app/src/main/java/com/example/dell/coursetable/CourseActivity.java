package com.example.dell.coursetable;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dell.coursetable.adapter.CourseListAdapter;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.presenter.CourseSelPresenter;
import com.example.dell.coursetable.presenter.CourseSelPresenterImpl;
import com.example.dell.coursetable.view.CourseSelViewImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity implements View.OnClickListener,CourseSelViewImpl {

    private List<String> list = new ArrayList<>();
    private Map<Integer,Boolean> map = new HashMap<>();
    private CourseListAdapter adapter;
    private TextView selectAll;
    private TextView unSelectAll;
    private TextView back;
    private TextView confirm;
    private CourseSelPresenterImpl courseSelPresenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        initView();

        courseSelPresenter = new CourseSelPresenter(CourseActivity.this);
        courseSelPresenter.initList();
    }

    private void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.course_list);
        confirm = (TextView)findViewById(R.id.course_list_confirm);
        selectAll = (TextView)findViewById(R.id.all);
        unSelectAll = (TextView)findViewById(R.id.no_all);
        back = (TextView)findViewById(R.id.select_back);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.select_refresh);



        refreshLayout.setEnabled(false);
        refreshLayout.setRefreshing(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new CourseListAdapter(list,map,CourseActivity.this);
        adapter.setRecyclerViewOnItemClickListener(new CourseListAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                //设置选中的项
                adapter.setSelectItem(position);
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                adapter.setShowBox();
                //设置选中的项
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        recyclerView.setAdapter(adapter);


        selectAll.setOnClickListener(this);
        unSelectAll.setOnClickListener(this);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.all:
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, true);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.no_all:
                Map<Integer, Boolean> m = adapter.getMap();
                for (int i = 0; i < m.size(); i++) {
                    m.put(i, false);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.course_list_confirm:
                Map<Integer, Boolean> map1 = adapter.getMap();
                List<String> strings = new ArrayList<>();
                for(int i =0; i<map1.size(); i++){
                    if(map1.get(i).booleanValue()){
                        strings.add(list.get(i));
                    }
                }
                courseSelPresenter.change(strings);
                break;
            case R.id.select_back:
                finish();
                break;
        }
    }

    @Override
    public void showData(final List<String> strings) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.addAll(strings);
                for(int i=0;i<list.size();i++){
                    map.put(i,false);
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showCourse(CourseList[][] courseLists_d, CourseList[][] courseLists_s) {

    }

    @Override
    public void show() {
        Intent intent = new Intent(CourseActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
}
