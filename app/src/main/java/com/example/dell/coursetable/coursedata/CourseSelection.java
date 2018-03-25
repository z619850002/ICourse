package com.example.dell.coursetable.coursedata;

import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dell on 2018/2/23.
 */

public class CourseSelection {

    private CourseList[][] courseLists_s;
    private CourseList[][] courseLists_d;

    private CourseList courseList;//包含了所有选过的课程

    private double score;


    public CourseSelection clone()
    {
        CourseSelection cs=new CourseSelection();
        CourseList[][] courseListd=new CourseList[7][12];
        CourseList[][] courseLists=new CourseList[7][12];
        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                courseListd[i][j]=new CourseList();
                courseLists[i][j]=new CourseList();
            }
        }
        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                for (CourseInformation ele:courseLists_d[i][j].getCourseInfomations())
                {
                    courseListd[i][j].getCourseInfomations().add(ele);
                }
                for (CourseInformation ele:courseLists_s[i][j].getCourseInfomations())
                {
                    courseLists[i][j].getCourseInfomations().add(ele);
                }
            }
        }
        CourseList cl=new CourseList();
        for (CourseInformation ele:courseList.getCourseInfomations())
        {
            cl.courseInformations.add(ele);
        }
        cs.setCourseList(cl);
        cs.setCourseLists_d(courseListd);
        cs.setCourseLists_s(courseLists);
        cs.score=this.score;
        return cs;
    }


    //退掉已选的课程
    public void removeCourse(CourseInformation courseInformation)
    {
        try {
            courseList.getCourseInfomations().remove(courseInformation);
            for (int i = 0; i <= 6; i++) {
                for (int j = 0; j <= 11; j++) {
                    courseLists_d[i][j].getCourseInfomations().remove(courseInformation);
                    courseLists_s[i][j].getCourseInfomations().remove(courseInformation);
                }
            }
        }
        catch (Exception e)
        {
        }
    }


    //选课
    public boolean selectCourse(CourseInformation courseInformation)
    {
        String week=courseInformation.getWeek();
        Integer beginWeek=Integer.parseInt(week.substring(week.indexOf('[')+1,week.indexOf('-')));
        Integer endWeek=Integer.parseInt(week.substring(week.indexOf('-')+1,week.length()));

        String[] time=courseInformation.getTime().split(";");
        //检验是否能够插入
        for (int i=0;i<=time.length-1;i++)
        {
            String st=time[i].substring(0,time[i].indexOf('('));
            char c=st.charAt(st.length()-1);
            boolean isSingle=true;
            boolean isDouble=true;
            if (c=='单')
            {
                isDouble=false;
                st=st.substring(0,st.length()-1);
            }
            else if (c=='双')
            {
                isSingle=false;
                st=st.substring(0,st.length()-1);
            }

            if (isSingle)
            {
                int day=0;
                switch (st.charAt(0))
                {
                    case '一': day=0;break;
                    case '二': day=1;break;
                    case '三': day=2;break;
                    case '四': day=3;break;
                    case '五': day=4;break;
                    case '六': day=5;break;
                    case '七': day=6;break;
                }
                try {
                    int beginTime = Integer.parseInt(st.substring(st.indexOf(':') + 1, st.indexOf('-')));
                    int endTime = Integer.parseInt(st.substring(st.indexOf('-') + 1, st.length() ));
                    for (int j = beginTime; j <= endTime; j++) {
                        List<CourseInformation> cl=courseLists_s[day][j-1].getCourseInfomations();
                        for (CourseInformation ele:cl)
                        {
                            String weekTemp=ele.getWeek();
                            Integer beginWeekTemp=Integer.parseInt(weekTemp.substring(weekTemp.indexOf('[')+1,weekTemp.indexOf('-')));
                            Integer endWeekTemp=Integer.parseInt(weekTemp.substring(weekTemp.indexOf('-')+1,weekTemp.length()));
                            if ((endWeekTemp-beginWeek)*(beginWeekTemp-endWeek)>=0)
                            {
                                return false;
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    return false;
                }
            }


            if (isDouble)
            {
                int day=0;
                switch (st.charAt(0))
                {
                    case '一': day=0;break;
                    case '二': day=1;break;
                    case '三': day=2;break;
                    case '四': day=3;break;
                    case '五': day=4;break;
                    case '六': day=5;break;
                    case '七': day=6;break;
                }
                try {
                    int beginTime = Integer.parseInt(st.substring(st.indexOf(':') + 1, st.indexOf('-')));
                    int endTime = Integer.parseInt(st.substring(st.indexOf('-') + 1, st.length()));
                    for (int j = beginTime; j <= endTime; j++) {
                        List<CourseInformation> cl=courseLists_d[day][j-1].getCourseInfomations();
                        for (CourseInformation ele:cl)
                        {
                            String weekTemp=ele.getWeek();
                            Integer beginWeekTemp=Integer.parseInt(weekTemp.substring(weekTemp.indexOf('[')+1,weekTemp.indexOf('-')));
                            Integer endWeekTemp=Integer.parseInt(weekTemp.substring(weekTemp.indexOf('-')+1,weekTemp.indexOf(']')));
                            if ((endWeekTemp-beginWeek)*(beginWeekTemp-endWeek)>=0)
                            {
                                return false;
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }

        //插入课程
        courseList.getCourseInfomations().add(courseInformation);
        for (int i=0;i<=time.length-1;i++)
        {
            String st=time[i].substring(0,time[i].indexOf('('));
            char c=st.charAt(st.length()-1);
            boolean isSingle=true;
            boolean isDouble=true;
            if (c=='单')
            {
                isDouble=false;
                st=st.substring(0,st.length()-1);
            }
            else if (c=='双')
            {
                isSingle=false;
                st=st.substring(0,st.length()-1);
            }

            if (isSingle)
            {
                int day=0;
                switch (st.charAt(0))
                {
                    case '一': day=0;break;
                    case '二': day=1;break;
                    case '三': day=2;break;
                    case '四': day=3;break;
                    case '五': day=4;break;
                    case '六': day=5;break;
                    case '七': day=6;break;
                }
                try {
                    int beginTime = Integer.parseInt(st.substring(st.indexOf(':') + 1, st.indexOf('-')));
                    int endTime = Integer.parseInt(st.substring(st.indexOf('-') + 1, st.length()));
                    for (int j = beginTime; j <= endTime; j++) {
                        List<CourseInformation> cl=courseLists_s[day][j-1].getCourseInfomations();
                        cl.add(courseInformation);
                    }
                }
                catch (Exception e)
                {
                    return false;
                }
            }


            if (isDouble)
            {
                int day=0;
                switch (st.charAt(0))
                {
                    case '一': day=0;break;
                    case '二': day=1;break;
                    case '三': day=2;break;
                    case '四': day=3;break;
                    case '五': day=4;break;
                    case '六': day=5;break;
                    case '七': day=6;break;
                }
                try {
                    int beginTime = Integer.parseInt(st.substring(st.indexOf(':') + 1, st.indexOf('-')));
                    int endTime = Integer.parseInt(st.substring(st.indexOf('-') + 1, st.length()));
                    for (int j = beginTime; j <= endTime; j++) {
                        List<CourseInformation> cl=courseLists_d[day][j-1].getCourseInfomations();
                        cl.add(courseInformation);
                    }
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }

        return true;
    }


    public CourseSelection() {
        courseLists_s=new CourseList[7][12];
        courseLists_d=new CourseList[7][12];

        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                courseLists_d[i][j]=new CourseList();
                courseLists_s[i][j]=new CourseList();
            }
        }
        courseList=new CourseList();
    }

    public CourseList[][] getCourseLists_s() {
        return courseLists_s;
    }

    public void setCourseLists_s(CourseList[][] courseLists_s) {
        this.courseLists_s = courseLists_s;
    }

    public CourseList[][] getCourseLists_d() {
        return courseLists_d;
    }

    public void setCourseLists_d(CourseList[][] courseLists_d) {
        this.courseLists_d = courseLists_d;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

}
