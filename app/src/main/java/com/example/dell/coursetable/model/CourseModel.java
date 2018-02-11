package com.example.dell.coursetable.model;

import android.util.Log;

import com.example.dell.coursetable.coursedata.CourseInformation;
import com.example.dell.coursetable.coursedata.CourseList;
import com.example.dell.coursetable.userdata.CourseData;
import com.example.dell.coursetable.userdata.DbUtils;
import com.example.dell.coursetable.webutil.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;


/**
 * @author Kyrie
 * @version 1.0
 * @description  课程相关model
 * @date 2018/2/3
 */
public class CourseModel implements CourseModelImpl {



    Document doc=new Document("");

    static CourseList[][] courseState_s=new CourseList[7][12];  //单周
    static CourseList[][] courseState_d=new CourseList[7][12];  //双周

    public Document storeDocument(String rawData)
    {
        doc= Jsoup.parse(rawData);
        return doc;
    }

    public CourseList getCourse(int day , int time , boolean isSingle)
    {
        if (isSingle) {
            return courseState_s[day-1][time];
        }
        return courseState_d[day-1][time];
    }


    public boolean initCourse(String rawData)
    {
        storeDocument(rawData);
        for (int i=0;i<=6;i++)
        {
            for (int j=0;j<=11;j++)
            {
                courseState_s[i][j]=new CourseList();
                courseState_d[i][j]=new CourseList();
            }
        }
        Element body = doc.body();
        Element tbody=body.getElementsByTag("div").first().getElementsByTag("table").first().getElementsByTag("tbody").first();

        Elements tr=tbody.getElementsByTag("tr");
        for (Element ele:tr)
        {
            Elements td=ele.getElementsByTag("td");
            String name=td.get(2).text();
            String type=td.get(3).text();
            String testType=td.get(4).text();
            String teacher=td.get(7).text();
            String time=td.get(8).toString();
            String area=td.get(9).text();
            if (!saveCourse(time,name , type , testType , teacher , area))
            {
                Log.e(TAG, "initClass: 课程故障" );
                return  false;
            }
        }
        return true;
    }


    private boolean saveCourse(String rawTime , String name , String type , String testType , String teacher , String area) //第二个参数表示求的是不是单周
    {
        String data=rawTime;
        data = data.substring(data.indexOf("<td>")+"<td>".length() , data.length());
        boolean isEnd=false;
        while (!isEnd)
        {
            isEnd=true;
            int beginIndex=data.indexOf("星期");
            int endIndex=data.indexOf("<");
            String[] info=data.substring(beginIndex , endIndex).split(" ");
            int day=7;
            switch (info[0].charAt(info[0].length()-1))
            {
                case '一': day=0;break;
                case '二': day=1;break;
                case '三': day=2;break;
                case '四': day=3;break;
                case '五': day=4;break;
                case '六': day=5;break;
                case '七': day=6;break;
            }
            if (day==7)
            {
                return false;
            }

            String timeInfo=info[1];
            String beginTime=timeInfo.substring(0,timeInfo.indexOf('-'));
            String endTime=timeInfo.substring(timeInfo.indexOf('-')+1,timeInfo.length());
            for (Integer i= Integer.parseInt(beginTime)-1 ; i<=Integer.parseInt(endTime)-1;i++) {
                CourseInformation courseInformation = new CourseInformation();
                courseInformation.setArea(area);
                courseInformation.setTime(info[0] + ' ' + info[1] + ' ' + info[2]);
                courseInformation.setWeek(info[2]);
                courseInformation.setName(name);
                courseInformation.setTeacher(teacher);
                courseInformation.setTestType(testType);
                courseInformation.setType(type);
                if (info[2].charAt(0) == '单') {
                    courseState_s[day][i].getCourseInfomations().add(courseInformation);
                }
                else if (info[2].charAt(0)=='双'){
                    courseState_d[day][i].getCourseInfomations().add(courseInformation);
                }
                else
                {
                    courseState_s[day][i].getCourseInfomations().add(courseInformation);
                    courseState_d[day][i].getCourseInfomations().add(courseInformation);
                }
            }

            if (data.charAt(endIndex+1)=='b')
            {
                isEnd=false;
                data=data.substring(endIndex+4,data.length());
            }
        }
        return true;
    }


    public Document getDoc() {
        return doc;
    }

    public CourseList[][] getClassState_s() {
        return courseState_s;
    }

    public CourseList[][] getClassState_d() {
        return courseState_d;
    }








    private String getSAMLRequest(String html)
    {
        String s1="";
        for (int i=0;i<=html.length()-12;i++)
        {
            s1=html.substring(i,i+11);
            if (s1.equals("SAMLRequest"))
            {
                html=html.substring(i);
                break;
            }
        }
        char[] c_array=html.toCharArray();
        int pos=0;
        for (int i=0;i<=html.length()-1;i++)
        {
            if (c_array[i]=='>')
            {
                pos=i;
                break;
            }
        }
        html=html.substring(0,pos-1);
        return html;
    }

    private String getSAMLResponse(String rawData)
    {
        int beginIndex=rawData.indexOf("name=\"SAMLResponse\" value=\"")+27;
        char[] arr=rawData.toCharArray();
        int endIndex=rawData.length();
        for (int i=beginIndex ; i<rawData.length();i++)
        {
            if (arr[i]=='>') {
                endIndex = i - 2;
                break;
            }
        }
        String response=rawData.substring(beginIndex,endIndex);
        return response;
    }

    private String getRelayState(String rawData)
    {

        int beginIndex=rawData.indexOf("name=\"RelayState\" value=\"")+25;
        char[] arr=rawData.toCharArray();
        int endIndex=rawData.length();
        for (int i=beginIndex ; i<rawData.length();i++)
        {
            if (arr[i]=='>') {
                endIndex = i - 2;
                break;
            }
        }
        String response=rawData.substring(beginIndex,endIndex);
        return response;
    }

    private String getLoginUrl(String rawData)
    {
        int beginIndex=rawData.indexOf("action=")+8;
        char[] arr=rawData.toCharArray();
        int endIndex=rawData.length();
        for (int i=beginIndex ; i<rawData.length();i++)
        {
            if (arr[i]=='>') {
                endIndex = i - 1;
                break;
            }
        }
        String response=rawData.substring(beginIndex,endIndex);
        return response;
    }


    //登陆并获取课表

    public String login(final String id , final String password) throws IOException, ExecutionException, InterruptedException
    {
        FutureTask<String> futureTask=new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String response1 = HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/login.action");
                List<Cookie> originCookie = HttpUtil.formerCookie;
                String response2 = HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/samlCheck");
                String nextUrl=getSAMLRequest(response2);
                String response3=HttpUtil.sendGetRequest("https://ids.tongji.edu.cn:8443/nidp/saml2/sso?"+nextUrl );
                FormBody.Builder requestBodyBuilder = new FormBody.Builder();
                requestBodyBuilder.add("option" , "credential");
                requestBodyBuilder.add("Ecom_User_ID" , id);
                requestBodyBuilder.add("Ecom_Password", password);
                requestBodyBuilder.add("submit","登录");
                RequestBody formBody=requestBodyBuilder.build();

                nextUrl="https://ids.tongji.edu.cn:8443"+getLoginUrl(response3);

                String response4 = HttpUtil.sendPostRequest(nextUrl,new FormBody.Builder().build());
                String response5 = HttpUtil.sendPostRequest("https://ids.tongji.edu.cn:8443/nidp/saml2/sso?sid=0&sid=0", formBody);
                String response6 = HttpUtil.sendGetRequest("https://ids.tongji.edu.cn:8443/nidp/saml2/sso?sid=0" );
                String SAMLResponse=getSAMLResponse(response6);
                String relayState=getRelayState(response6);


                FormBody.Builder requestBodyBuilder2 = new FormBody.Builder();
                requestBodyBuilder2.add("SAMLResponse",SAMLResponse);

                requestBodyBuilder2.add("RelayState",relayState);
                RequestBody formBody2=requestBodyBuilder2.build();


                String response7 = HttpUtil.sendPostRequest("http://4m3.tongji.edu.cn/eams/saml/SAMLAssertionConsumer",formBody2 );
                //String response8 = HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/samlCheck",false,null);
                //String response9 = HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/home.action" , false  , null);
                //String finalResponse=response9;
                String s=findCourse();
                return s;
            }
        });
        futureTask.run();
        List<CourseData> list = DbUtils.getQueryByWhere(CourseData.class,"studentId", new String[]{id});
        if(list.isEmpty()||list.size()==0){
            CourseData data = new CourseData();
            data.setStudentId(id);
            data.setInfo(futureTask.get());
            DbUtils.insert(data);
        }
        return futureTask.get();
    }


    //获取课表
    public String findCourse() throws Exception
    {
        String response1=HttpUtil.sendGetRequest("http://4m3.tongji.edu.cn/eams/courseTableForStd.action" );
        String ids=getIds(response1);
        String semesterId=getSemesterId(response1);
        FormBody.Builder builder=new FormBody.Builder();
        builder.add("ids",ids);
        builder.add("ignoreHead","1");
        builder.add("semester.id",semesterId);
        builder.add("setting.kind","std");
        builder.add("startWeek","1");
        RequestBody body=builder.build();
        String response2=HttpUtil.sendPostRequest("http://4m3.tongji.edu.cn/eams/courseTableForStd!courseTable.action",body );
        return response2;
    }

    public String getIds(String rawData)
    {

        int beginIndex=rawData.indexOf("bg.form.addInput(form,\"ids\",\"")+"bg.form.addInput(form,\"ids\",\"".length();
        char[] arr=rawData.toCharArray();
        int endIndex=rawData.length();
        for (int i=beginIndex ; i<rawData.length();i++)
        {
            if (arr[i]=='"') {
                endIndex = i;
                break;
            }
        }
        String response=rawData.substring(beginIndex,endIndex);
        return response;
    }
    public String getSemesterId(String rawData)
    {

        int beginIndex=rawData.indexOf("semesterCalendar({empty:\"false\",onChange:\"\",value:\"")+"semesterCalendar({empty:\"false\",onChange:\"\",value:\"".length();
        char[] arr=rawData.toCharArray();
        int endIndex=rawData.length();
        for (int i=beginIndex ; i<rawData.length();i++)
        {
            if (arr[i]=='"') {
                endIndex = i;
                break;
            }
        }
        String response=rawData.substring(beginIndex,endIndex);
        return response;
    }


    @Override
    public boolean initData(String id, String password) {

        try {
            String rawData;
            List<CourseData> list = DbUtils.getQueryByWhere(CourseData.class, "studentId", new String[]{id});
            if(list.isEmpty()||list.size()==0){
                rawData = login(id, password);
            } else {
                rawData = list.get(0).getInfo();
            }
            return initCourse(rawData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "initAllClass: ",e );
        }
        return false;
    }
}
