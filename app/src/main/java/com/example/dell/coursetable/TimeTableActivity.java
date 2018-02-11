package com.example.dell.coursetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.presenter.CoursePresenter;
import com.example.dell.coursetable.presenter.CoursePresenterImpl;
import com.example.dell.coursetable.view.CourseTableViewImpl;
import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.TimetableView;

import java.util.ArrayList;
import java.util.List;

public class TimeTableActivity extends AppCompatActivity implements OnSubjectItemClickListener , CourseTableViewImpl{


    private CoursePresenterImpl cPresenter;

    private TimetableView mTimeTableView;
    private List<SubjectBean> subjectBeans = new ArrayList<>();
    private List<String> weekList = new ArrayList<>();
    private CourseList[][] courseLists_d;
    private CourseList[][] courseLists_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        mTimeTableView = (TimetableView)findViewById(R.id.timetableview);

        initWeekList();
        cPresenter=new CoursePresenter(TimeTableActivity.this);
        cPresenter.show();
        mTimeTableView.setDataSource(subjectBeans)
                .setCurWeek("2018-1-1 00:00:00")
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
                    Integer beginWeek=Integer.parseInt(week.substring(week.indexOf('[')+1,week.indexOf('-')));
                    Integer endWeek=Integer.parseInt(week.substring(week.indexOf('-')+1,week.indexOf(']')));
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

                    SubjectBean s = new SubjectBean(courseInformation.getName(), courseInformation.getArea(), courseInformation.getTeacher(), myWeekList, beginTime+1,endTime-beginTime+1 , i+1, 1, "");
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
                    Integer beginWeek=Integer.parseInt(week.substring(week.indexOf('[')+1,week.indexOf('-')));
                    Integer endWeek=Integer.parseInt(week.substring(week.indexOf('-')+1,week.indexOf(']')));
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

                    SubjectBean s = new SubjectBean(courseInformation.getName(), courseInformation.getArea(), courseInformation.getTeacher(), myWeekList, beginTime+1,endTime-beginTime+1 , i+1, 1, "");
                    subjectBeans.add(s);
                }
            }
        }
    }

    private void initWeekList(){
        for(int i=0;i<17;i++){
            weekList.add("第"+(i+1)+"周");
        }
    }

    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {
        Bundle bundle = new Bundle();
        for(int i =0; i < subjectList.size(); i++){
            if(subjectList.get(i).getWeekList().contains(mTimeTableView.getCurWeek())){
                bundle.putString("name", subjectList.get(i).getName());
                bundle.putString("room",subjectList.get(i).getRoom());
                bundle.putString("start",subjectList.get(i).getStart()+"");
                bundle.putString("step",subjectList.get(i).getStep()+"");
                bundle.putString("teacher",subjectList.get(i).getTeacher());
                List<CourseInformation>list = courseLists_d[subjectList.get(i).getDay()-1][subjectList.get(i).getStart()-1].getCourseInfomations();
                int j = 0;
                while(!list.get(j).getName().equals(subjectList.get(i).getName())){
                    j++;
                }
                if(j>=list.size()){
                    j=0;
                    list = courseLists_s[subjectList.get(i).getDay()-1][subjectList.get(i).getStart()-1].getCourseInfomations();
                    while(!list.get(j).getName().equals(subjectList.get(i).getName())){
                        j++;
                    }
                }
                bundle.putString("week",list.get(j).getWeek());
            }
        }
        if(bundle.isEmpty()){
            bundle.putString("name", subjectList.get(0).getName());
            bundle.putString("room",subjectList.get(0).getRoom());
            bundle.putString("start",subjectList.get(0).getStart()+"");
            bundle.putString("step",subjectList.get(0).getStep()+"");
            bundle.putString("teacher",subjectList.get(0).getTeacher());
            List<CourseInformation>list = courseLists_d[subjectList.get(0).getDay()-1][subjectList.get(0).getStart()-1].getCourseInfomations();
            int j = 0;
            while(!list.get(j).getName().equals(subjectList.get(0).getName())){
                j++;
            }
            if(j>=list.size()){
                j=0;
                list = courseLists_s[subjectList.get(0).getDay()-1][subjectList.get(0).getStart()-1].getCourseInfomations();
                while(!list.get(j).getName().equals(subjectList.get(0).getName())){
                    j++;
                }
            }
            bundle.putString("week",list.get(j).getWeek());
        }
        Intent intent = new Intent(TimeTableActivity.this, CourseInfoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void showData(CourseList[][] courseLists_d  , CourseList[][] courseLists_s) {
        initCourse(courseLists_d , courseLists_s);
        this.courseLists_d = courseLists_d;
        this.courseLists_s = courseLists_s;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(TimeTableActivity.this , message , Toast.LENGTH_LONG).show();
    }
}
