package com.example.dell.coursetable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.presenter.CourseSelPresenter;
import com.example.dell.coursetable.presenter.CourseSelPresenterImpl;
import com.example.dell.coursetable.view.CourseSelViewImpl;
import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.TimetableView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements CourseSelViewImpl,OnSubjectItemClickListener{

    private TextView back;
    private TimetableView mTimeTableView;
    private List<SubjectBean> subjectBeans = new ArrayList<>();
    private CourseSelPresenterImpl courseSelPresenter;
    private SwipeRefreshLayout refreshLayout;
    private TextView addCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        courseSelPresenter = new CourseSelPresenter(this);
        courseSelPresenter.showData();
        initView();
    }

    private void initView(){
        back = (TextView)findViewById(R.id.table_back);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.table_refresh);
        addCourse = (TextView)findViewById(R.id.add_course);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addCourse.setVisibility(View.INVISIBLE);

        refreshLayout.setEnabled(false);
        //refreshLayout.setRefreshing(true);

        mTimeTableView = (TimetableView)findViewById(R.id.timetableview);
        mTimeTableView.setDataSource(subjectBeans)
                .setCurWeek("2018-3-5 00:00:00")
                .setMax(true)
                .setShowDashLayer(true)
                .setOnSubjectItemClickListener(this)
                .showTimetableView();
        mTimeTableView.changeWeek(mTimeTableView.getCurWeek(),true);
    }

    private void initCourse(CourseList[][] courseLists_d , CourseList[][] courseLists_s){
        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                int temp = j;
                List<CourseInformation> courseList=courseLists_d[i][j].getCourseInfomations();
                for (int k=0;k<=courseList.size()-1;k++)
                {
                    j=temp;
                    CourseInformation courseInformation=courseList.get(k);
                    String week=courseInformation.getWeek();
                    Integer beginWeek=Integer.parseInt(week.substring(0,week.indexOf('-')));
                    Integer endWeek=Integer.parseInt(week.substring(week.indexOf('-')+1,week.length()));
                    Integer beginTime=j;
                    Integer endTime=j;
                    if (j<11) {
                        String s="";
                        /*if (courseLists_d[i][j + 1].getCourseInfomations().size()>=k+1) {
                            s = courseLists_d[i][j + 1].getCourseInfomations().get(k).getTime();
                        }*/
                        for (CourseInformation ele:courseLists_d[i][j + 1].getCourseInfomations())
                        {
                            if (ele.getName().equals(courseLists_d[i][j].getCourseInfomations().get(k).getName()))
                            {
                                s = ele.getTime();
                            }
                        }
                        while (s.equals(courseInformation.getTime()))
                        {
                            j++;
                            s = "";
                            if (j<11) {
                                /*if (courseLists_d[i][j + 1].getCourseInfomations().size() >= k + 1) {
                                    s = courseLists_d[i][j + 1].getCourseInfomations().get(k).getTime();
                                }*/
                                for (CourseInformation ele:courseLists_d[i][j + 1].getCourseInfomations())
                                {
                                    if (ele.getName().equals(courseLists_d[i][j].getCourseInfomations().get(k).getName()))
                                    {
                                        s = ele.getTime();
                                    }
                                }
                            }
                            else
                            {
                                j++;
                                endTime = j - 1;
                            }
                        }
                        if (j<12) {
                            endTime = j;
                        }
                    }
                    else {
                        j++;
                        endTime = j - 1;
                    }
                    List<Integer> myWeekList = new ArrayList<>();
                    for(int l=1; l < 17 ; l+=2) {
                        if (l>=beginWeek&&l<=endWeek)
                            myWeekList.add(l+1);
                    }

                    SubjectBean s = new SubjectBean(courseInformation.getName(), "", courseInformation.getTeacher(), myWeekList, beginTime+1,endTime-beginTime+1 , i+1, 1, "");
                    subjectBeans.add(s);
                }
            }
        }

        /*---------------------------------------上面是双周---------------------------------------------------------------------------------------------------*/

        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                int temp = j;
                List<CourseInformation> courseList=courseLists_s[i][j].getCourseInfomations();
                for (int k=0;k<=courseList.size()-1;k++)
                {
                    j=temp;
                    CourseInformation courseInformation=courseList.get(k);
                    String week=courseInformation.getWeek();
                    Integer beginWeek=Integer.parseInt(week.substring(0,week.indexOf('-')));
                    Integer endWeek=Integer.parseInt(week.substring(week.indexOf('-')+1,week.length()));
                    Integer beginTime=j;
                    Integer endTime=j;
                    if (j<11) {
                        String s="";
                        /*if (courseLists_s[i][j + 1].getCourseInfomations().size()>=k+1) {
                            s = courseLists_s[i][j + 1].getCourseInfomations().get(k).getTime();
                        }*/
                        for (CourseInformation ele:courseLists_s[i][j + 1].getCourseInfomations())
                        {
                            if (ele.getName().equals(courseLists_s[i][j].getCourseInfomations().get(k).getName()))
                            {
                                s = ele.getTime();
                            }
                        }
                        while (s.equals(courseInformation.getTime()))
                        {
                            j++;
                            s = "";
                            if (j<11) {
                                /*if (courseLists_s[i][j + 1].getCourseInfomations().size() >= k + 1) {
                                    s = courseLists_s[i][j + 1].getCourseInfomations().get(k).getTime();
                                }*/

                                for (CourseInformation ele:courseLists_s[i][j + 1].getCourseInfomations())
                                {
                                    if (ele.getName().equals(courseLists_s[i][j].getCourseInfomations().get(k).getName()))
                                    {
                                        s = ele.getTime();
                                    }
                                }
                            }
                            else
                            {
                                j++;
                                endTime = j - 1;
                            }
                        }
                        if (j<12) {
                            endTime = j;
                        }
                    }
                    else {
                        j++;
                        endTime = j - 1;
                    }
                    List<Integer> myWeekList = new ArrayList<>();
                    for(int l=1; l <= 17 ; l+=2) {
                        if (l>=beginWeek&&l<=endWeek)
                            myWeekList.add(l);
                    }

                    SubjectBean s = new SubjectBean(courseInformation.getName(), "", courseInformation.getTeacher(), myWeekList, beginTime+1,endTime-beginTime+1 , i+1, 1, "");
                    subjectBeans.add(s);
                }
            }
        }
    }
    @Override
    public void showData(List<String> strings) {

    }

    @Override
    public void showCourse(CourseList[][] courseLists_d, CourseList[][] courseLists_s) {
        initCourse(courseLists_d, courseLists_s);
    }

    @Override
    public void show() {

    }

    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {

    }
}
