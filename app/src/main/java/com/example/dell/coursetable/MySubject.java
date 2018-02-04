package com.example.dell.coursetable;

import java.util.List;

/**
 * Created by 田雍恺 on 2018/2/2.
 */

public class MySubject {
    private String name;

    private String time;

    private String room;

    private String teacher;

    private List<Integer> weekList;

    private int start;

    private int step;

    private int day;

    private String term;

    private int colorRandom = 0;

    public MySubject(){

    }

    public MySubject(String term, String name, String room, String teacher, List<Integer> weekList, int start, int step, int day, int colorRandom, String time){
        this.term = term;
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.weekList = weekList;
        this.start = start;
        this.step = step;
        this.day = day;
        this.colorRandom = colorRandom;
        this.time = time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setRoom(String room){
        this.room = room;
    }

    public String getRoom(){
        return room;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }

    public String getTeacher(){
        return teacher;
    }

    public void setWeekList(List<Integer> weekList){
        this.weekList = weekList;
    }

    public List<Integer> getWeekList(){
        return weekList;
    }

    public void setStart(int start){
        this.start = start;
    }

    public int getStart(){
        return start;
    }

    public void setStep(int step){
        this.step = step;
    }

    public int getStep(){
        return step;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getDay(){
        return  day;
    }

    public void setTerm(String term){
        this.term = term;
    }

    public String getTerm(){
        return term;
    }

    public void setColorRandom(int colorRandom){
        this.colorRandom = colorRandom;
    }

    public int getColorRandom(){
        return colorRandom;
    }
}
