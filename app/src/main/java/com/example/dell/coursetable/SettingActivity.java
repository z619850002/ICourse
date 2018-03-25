package com.example.dell.coursetable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.coursetable.adapter.SettingAdapter;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.presenter.CourseSelPresenter;
import com.example.dell.coursetable.presenter.CourseSelPresenterImpl;
import com.example.dell.coursetable.view.CourseSelViewImpl;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener,CourseSelViewImpl {

    private CourseSelPresenterImpl courseSelPresenter;

    private BubbleSeekBar difficultBar;
    private BubbleSeekBar loadBar;
    private BubbleSeekBar practicabilityBar;
    private BubbleSeekBar enjoyBar;
    private BubbleSeekBar teacherBar;
    private TextView settingBack;
    private TextView confirm;

    private List<String> list;
    private SettingAdapter adapter;
    private RecyclerView type;
    private LinearLayout linearLayout;

    private CheckBox mCheckBox;
    private CheckBox nCheckBox;
    private CheckBox cCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initType();
        initView();
        courseSelPresenter = new CourseSelPresenter(SettingActivity.this);
    }

    private void initView(){
        difficultBar = (BubbleSeekBar)findViewById(R.id.difficult);
        loadBar = (BubbleSeekBar)findViewById(R.id.load);
        practicabilityBar = (BubbleSeekBar)findViewById(R.id.practicability);
        enjoyBar = (BubbleSeekBar)findViewById(R.id.enjoy);
        teacherBar = (BubbleSeekBar)findViewById(R.id.teacher);
        settingBack = (TextView)findViewById(R.id.setting_back);
        confirm = (TextView)findViewById(R.id.setting_confirm);
        type = (RecyclerView)findViewById(R.id.type);
        linearLayout = (LinearLayout)findViewById(R.id.user_defined);

        mCheckBox = (CheckBox)findViewById(R.id.morning_choose);
        nCheckBox = (CheckBox)findViewById(R.id.night_choose);
        cCheckBox = (CheckBox)findViewById(R.id.concentrate_choose);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        type.setLayoutManager(manager);
        adapter = new SettingAdapter(list, this);
        adapter.setRecyclerViewOnItemClickListener(new SettingAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                adapter.setSelectItem(position);
               for(int i = 0; i<list.size();i++){
                    if(adapter.getMap().get(i)&&i!=position){
                        adapter.setSelectItem(i);
                    }
                }
                if(adapter.getMap().get(5)){
                   linearLayout.setVisibility(View.VISIBLE);
                } else {
                    linearLayout.setVisibility(View.INVISIBLE);
                }
            }
        });
        type.setAdapter(adapter);

        settingBack.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    private void initType(){
        list = new ArrayList<>();
        list.add("均衡型");
        list.add("绩点型");
        list.add("轻松型");
        list.add("实用型");
        list.add("趣味型");
        list.add("自定义");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_confirm:
                double difficult=0.0;
                double load=0.0;
                double practicability=0.0;
                double enjoy=0.0;
                double teacher=0.0;
                Map<Integer,Boolean> map = adapter.getMap();
                for(int i=0; i<map.size();i++){
                    if(map.get(i).booleanValue()){
                        switch(i){
                            case 0:
                                difficult = 1.0;
                                load = 1.0;
                                practicability = 1.0;
                                enjoy = 1.0;
                                teacher = 1.0;
                                break;
                            case 1:
                                difficult = 0.5;
                                load = 0.5;
                                practicability = 0.5;
                                enjoy = 1.0;
                                teacher = 2.5;
                                break;
                            case 2:
                                difficult = 0.8;
                                load = 0.3;
                                practicability = 0.3;
                                enjoy = 2.4;
                                teacher = 1.2;
                                break;
                            case 3:
                                difficult = 0.5;
                                load = 1.8;
                                practicability = 2.0;
                                enjoy = 0.3;
                                teacher = 0.4;
                                break;
                            case 4:
                                difficult = 2;
                                load = 1;
                                practicability = 0.5;
                                enjoy = 0.6;
                                teacher = 0.9;
                                break;
                            case 5:
                                difficult = (double) difficultBar.getProgress()/20;
                                load = (double) loadBar.getProgress()/20;
                                practicability = (double) practicabilityBar.getProgress()/20;
                                enjoy = (double)enjoyBar.getProgress()/20;
                                teacher = (double)teacherBar.getProgress()/20;
                                break;
                        }
                    }
                }
                if(difficult+load+practicability+enjoy+teacher!=5.0){
                    Toast.makeText(this,"请重新设置", Toast.LENGTH_SHORT).show();
                } else {
                    courseSelPresenter.select(difficult,teacher,practicability,load,enjoy,mCheckBox.isChecked(),nCheckBox.isChecked(), cCheckBox.isChecked());
                }
                break;
        }
    }

    @Override
    public void showData(List<String> strings) {

    }

    @Override
    public void showCourse(CourseList[][] courseLists_d, CourseList[][] courseLists_s) {

    }

    @Override
    public void show() {
        Intent intent = new Intent(SettingActivity.this, ResultActivity.class);
        startActivity(intent);
        finish();
    }
}
