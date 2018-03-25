package com.example.dell.coursetable.model;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.coursedata.CourseSelection;
import com.example.dell.coursetable.gson.Course;
import com.example.dell.coursetable.webutil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

/**
 * Created by dell on 2018/2/23.
 */

public class CourseSelectionModel {

    final private double MORNING_COEFFICIENT=0.4;
    final private double NIGHT_COEFFICIENT=0.4;
    final private double CONCENTRATED_COEFFICIENT=0.1;


    private List<CourseSelection> courseSelectionList;

    private List<List<Course>> courseList;
    private List<List<CourseInformation>>  courseInformationList;



    public CourseSelectionModel() {
        courseInformationList=new ArrayList<>();
        courseList=new ArrayList<>();
        courseSelectionList=new ArrayList<>();
    }

    public Course getScoreInfo(CourseInformation courseInformation)
    {
        String courseName=courseInformation.getName();
        String teacherName=courseInformation.getTeacher();
        for (List<Course> ele:courseList)
        {
            if (!ele.isEmpty())
            {
                if (ele.get(0).getCourseName().equals(courseName))
                {
                    for (Course ele2:ele)
                    {
                        if (ele2.getTeacherInfo().getTeacherName().equals(teacherName))
                        {
                            return ele2;
                        }
                    }
                }
            }
        }
        return null;
    }


    class FuckTheCourse
    {
        String id;
        String no;
        String name;
        String credits;
        String weekHour;
        String startWeek;
        String teachers;
        String endWeek;
        String teachDepart;
        List<ArrangeInfo> arrangeInfo;
    }
    class ArrangeInfo
    {
        String weekDay;
        String weekState;
        String startUnit;
        String endUnit;
        String rooms;

    }


    class CanSelectCourse {
        private String id;
        private int sc;
        private int lc;
        public boolean canSelect;

        public CanSelectCourse(String id, String sc, String lc){
            this.id = id;
            this.sc = Integer.parseInt(sc);
            this.lc = Integer.parseInt(lc);
            if(this.sc<this.lc){
                canSelect = true;
            } else {
                canSelect = false;
            }
        }

        public void setId(String id){
            this.id = id;
        }

        public void setSc(int sc){
            this.sc = sc;
        }

        public void setLc(int lc){
            this.lc = lc;
        }

        public String getId(){
            return id;
        }

        public int getSc(){
            return sc;
        }

        public int getLc(){
            return lc;
        }

    }
    private List<CanSelectCourse> divide(String s) {
        List<CanSelectCourse> cancourseList = new ArrayList<>();
        String str;
        String id;
        List<String> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '\'' && s.charAt(i + 1) != ':') {
                j = i;
            } else if (s.charAt(i) == '}') {
                str = s.substring(j, i + 1);
                id = str.substring(1, 16);
                int m = 0;
                int n = 0;
                list.clear();
                for (m = 19; m < str.length(); m++) {
                    if (str.charAt(m) == 's' || str.charAt(m) == 'l') {
                        n = m + 3;
                    } else if (str.charAt(m) == ',') {
                        list.add(str.substring(n, m));
                    }
                    if (list.size() == 2)
                        break;
                }

                cancourseList.add(new CanSelectCourse(id, list.get(0), list.get(1)));
            }
        }
        return cancourseList;
    }

    public List<String> getAllCourseInfo()
    {
        List<String> res=new ArrayList<>();
        Set<String>  resSet=new HashSet<>();
        try {
            String response1 = HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/doorOfStdElectCourse.action?_=1520318973220");
            int beginIndex=response1.indexOf("/eams/tJStdElectCourse!defaultPage.action?electionProfile.id=");
            int endIndex =beginIndex;
            for (int i=beginIndex+"/eams/tJStdElectCourse!defaultPage.action?electionProfile.id=".length();i<response1.length();i++)
            {
                if (response1.charAt(i)=='\"')
                {
                    endIndex=i;
                    break;
                }
            }
            String nextUrl = "http://4m3.tongji.edu.cn"+response1.substring(beginIndex,endIndex);
            String response2=HttpUtil.sendGetRequest(nextUrl);
            String response3=HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/tJStdElectCourse!planCourses.action");
            String response4=HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/tJStdElectCourse!data.action?profileId=4207");
            String response5=HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/tJStdElectCourse!queryStdCount.action?profileId=4207");
            List<CanSelectCourse> canSelectCourses=divide(response5);
            response4=response4.substring(response4.indexOf('['),response4.length()-1);
            List<FuckTheCourse> fuckTheCourseList=new Gson().fromJson(response4,new TypeToken<List<FuckTheCourse>>(){}.getType());
            String[] resList=response3.split("name");
            for (int i=0;i<=resList.length-1;i++)
            {
                if (resList[i].charAt(0)==':')
                {
                    int begin=resList[i].indexOf('\'');
                    int end=resList[i].indexOf(',')-1;
                    String name=resList[i].substring(begin+1,end);
                    if (!resSet.contains(name))
                    {
                        resSet.add(name);
                    }
                }
            }



            for (FuckTheCourse ele:fuckTheCourseList) {
                boolean can=false;
                for (CanSelectCourse elec:canSelectCourses)
                {
                    if (elec.id.equals(ele.id))
                    {
                        can=elec.canSelect;
                    }
                }
                if (resSet.contains(ele.name)&&can) {
                    String s = ele.no + "    " + ele.name + "    " + ele.weekHour + "    " + ele.credits + "    " + "考察" + "    " + ele.startWeek + "-" + ele.endWeek + "    " + ele.teachers + "    " + "教授" + "    " + 100 + "    " + 100 + "    ";
                    for (ArrangeInfo ele2 : ele.arrangeInfo) {
                        switch (ele2.weekDay) {
                            case "1":
                                s = s + "一:";
                                break;
                            case "2":
                                s = s + "二:";
                                break;
                            case "3":
                                s = s + "三:";
                                break;
                            case "4":
                                s = s + "四:";
                                break;
                            case "5":
                                s = s + "五:";
                                break;
                            case "6":
                                s = s + "六:";
                                break;
                            case "7":
                                s = s + "日:";
                                break;
                        }
                        int startw = 1;
                        int endw = 1;
                        boolean first = false;
                        boolean unique = false;
                        for (int i = 0; i <= ele2.weekState.length() - 1; i++) {
                            if (ele2.weekState.charAt(i) == '1') {
                                if (!first) {
                                    startw = i;
                                    first=true;
                                }
                                endw = i;
                            }
                            if (first && ele2.weekState.charAt(i) == '0') {
                                unique = true;
                            }
                        }
                        s = s + ele2.startUnit + "-" + ele2.endUnit;
                        if (unique) {
                            if (startw % 2 == 0) {
                                s = s + "双";
                            } else {
                                s = s + "单";
                            }
                        }
                        s += "(" + ele2.rooms + ");";
                    }
                    s = s + "    " + ele.teachDepart;
                    res.add(s);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }





    public void initCourseSelection() throws Exception
    {
        courseInformationList.clear();
        courseList.clear();
        courseSelectionList.clear();


        List<String> courseInfo=getAllCourseInfo();
        for (String s:courseInfo) {
            String[] arr = s.split("    ");
            CourseInformation cim = new CourseInformation();
            cim.setName(arr[1]);
            cim.setTeacher(arr[6]);
            cim.setTestType(arr[4]);
            cim.setWeek(arr[5]);
            cim.setTime(arr[10]);
            boolean exist = false;
            for (List<CourseInformation> ele : courseInformationList) {
                if (!ele.isEmpty()) {
                    if (ele.get(0).getName().equals(arr[1])) {
                        exist = true;
                    }
                }
            }
            if (!exist) {
                List<CourseInformation> arrayList = new ArrayList<>();
                arrayList.add(cim);
                getCourseInformationList().add(arrayList);
            } else {
                for (List<CourseInformation> ele : courseInformationList) {
                    if (!ele.isEmpty()) {
                        if (ele.get(0).getName().equals(arr[1])) {
                            ele.add(cim);
                        }
                    }
                }
            }
        }

    }

    public void beginSelect() throws IOException , InterruptedException   , ExecutionException
    {

        /*for (int i=0;i<=15;i++)
        {
            courseInformationList.remove(courseInformationList.size()-1);
        }*/
        getCourses();
        CourseSelection courseSelection=new CourseSelection();
        Stack<Integer> stack_int=new Stack<>();
        selectCourse(0,courseInformationList.size(),courseSelection);
    }

    private void selectCourse(int level , int maxLevel , CourseSelection courseSelection)
    {
        if (level>=maxLevel)
        {
            CourseSelection courseSelection1=new CourseSelection();
            courseSelection1=courseSelection.clone();
            courseSelectionList.add(courseSelection1);
        }
        else
        {
            for (CourseInformation ele:courseInformationList.get(level))
            {
                if (courseSelection.selectCourse(ele)) {
                    selectCourse(level + 1, maxLevel, courseSelection);
                }
                courseSelection.removeCourse(ele);
            }
        }
    }


    public void getCourses() throws IOException , InterruptedException    , ExecutionException
    {
        boolean first=true;
        String courseNames="";
        String teacherNames="";

        for (List<CourseInformation> ele:courseInformationList)
        {
            for (CourseInformation ele2:ele)
            {
                if (first) {
                    courseNames+=(ele2.getName());
                    teacherNames+=(ele2.getTeacher());
                    first=false;
                }
                else
                {
                    courseNames+=("0"+ele2.getName());
                    teacherNames+=("0"+ele2.getTeacher());
                }
            }
        }
        CourseInfoModel courseInfoModel=new CourseInfoModel();
        courseInfoModel.getCoursesByNamesAndTeacahers(courseNames , teacherNames);
        List<Course> courseList2=courseInfoModel.getCourseList();
        int num=0;
        for (int i=0;i<=courseInformationList.size()-1;i++)
        {
            List<Course> privateCourseList=new ArrayList<>();
            for (int j=0;j<=courseInformationList.get(i).size()-1;j++)
            {
                privateCourseList.add(courseList2.get(num));
                num++;
            }
            courseList.add(privateCourseList);
        }
    }


    public void sortCourseSelection(double examCoefficient , double teacherCoefficient , double practicibilityCoefficient , double loadCoefficient ,double enjoymentCoefficient , boolean morningClass , boolean nightClass , boolean concentratedClass)
    {
        for (CourseSelection ele:courseSelectionList)
        {
            double sc=0;
            //calculate the basic score of every kind of selection
            for (CourseInformation ele2:ele.getCourseList().getCourseInfomations())
            {
                Course course=getScoreInfo(ele2);
                sc+=course.getExamDifficulty()*examCoefficient+course.getTeacherInfo().getScore()*teacherCoefficient+course.getPracticability()*practicibilityCoefficient+course.getCourseLoad()*loadCoefficient+course.getEnjoyment()*enjoymentCoefficient;
            }
            double morningsc=0;
            if (morningClass)
            {
                for (int i=0;i<=4;i++)
                {
                    for (int j=0;j<=1;j++)
                    {
                        if (ele.getCourseLists_d()[i][j].getCourseInfomations().isEmpty())
                        {
                            morningsc+=1;
                        }
                        else
                        {
                            morningsc-=1;
                        }
                        if (ele.getCourseLists_s()[i][j].getCourseInfomations().isEmpty())
                        {
                            morningsc+=1;
                        }
                        else
                        {
                            morningsc-=1;
                        }
                    }
                }
                morningsc*=MORNING_COEFFICIENT;
            }
            double nightsc=0;
            if (nightClass)
            {
                for (int i=0;i<=4;i++)
                {
                    for (int j=8;j<=11;j++)
                    {
                        if (ele.getCourseLists_d()[i][j].getCourseInfomations().isEmpty())
                        {
                            nightsc+=1;
                        }
                        else
                        {
                            nightsc-=1;
                        }
                        if (ele.getCourseLists_s()[i][j].getCourseInfomations().isEmpty())
                        {
                            nightsc+=1;
                        }
                        else
                        {
                            nightsc-=1;
                        }
                    }
                }
                nightsc*=NIGHT_COEFFICIENT;
            }
            double concentratesc=0;

            if (concentratedClass)
            {
                int line=0;
                for (int i=0;i<=4;i++)
                {
                    for (int j=0;j<=11;j++)
                    {
                        if (ele.getCourseLists_s()[i][j].getCourseInfomations().isEmpty())
                        {
                            line=0;
                            concentratesc-=3;
                        }
                        else
                        {
                            line+=0.5;
                            concentratesc+=line;
                        }
                    }
                }
                concentratesc*=CONCENTRATED_COEFFICIENT;
            }
            double totalsc=sc+morningsc+nightsc+concentratesc;
            ele.setScore(totalsc);
        }

        for (int i=0;i<=courseSelectionList.size()-2;i++)
        {
            for (int j=1;j<=courseSelectionList.size()-1;j++)
            {
                if (courseSelectionList.get(i).getScore()<courseSelectionList.get(j).getScore())
                {
                    CourseSelection c=courseSelectionList.get(i);
                    courseSelectionList.set(i,courseSelectionList.get(j));
                    courseSelectionList.set(j,c);
                }
            }
        }
    }


    public List<CourseSelection> getCourseSelectionList() {
        return courseSelectionList;
    }

    public void setCourseSelectionList(List<CourseSelection> courseSelectionList) {
        this.courseSelectionList = courseSelectionList;
    }

    public List<List<Course>> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<List<Course>> courseList) {
        this.courseList = courseList;
    }

    public List<List<CourseInformation>> getCourseInformationList() {
        return courseInformationList;
    }

    public void setCourseInformationList(List<List<CourseInformation>> courseInformationList) {
        this.courseInformationList = courseInformationList;
    }
}
