package com.example.dell.coursetable.userdata;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by 田雍恺 on 2018/2/10.
 */

@Table("course")
public class CourseData {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("studentId")
    private String studentId;
    @Column("info")
    private String info;

    public void setStudentId(String id){
        this.studentId = id;
    }
    public String getStudentId(){
        return studentId;
    }
    public int getId(){
        return id;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return info;
    }
}
