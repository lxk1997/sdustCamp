package com.clxk.h.sdustcamp.bean;

import cn.bmob.v3.BmobObject;

public class TimeTable extends BmobObject{
    private String className;
    private String classRoom;
    private String teacher;
    private String classStart;
    private String classEnd;
    private String classDay;
    private String classNum;
    private String term;

    public TimeTable(String className, String classRoom, String teacher, String classStart, String classEnd, String classDay, String classNum, String term) {
        this.classDay = classDay;
        this.classNum = classNum;
        this.term = term;
        this.classStart = classStart;
        this.classEnd = classEnd;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.className = className;
    }
    public TimeTable() {

    }

    public String getClassName() {
        return className;
    }
    public String getClassRoom() {
        return classRoom;
    }
    public String getTeacher() {
        return teacher;
    }
    public String getClassStart() {
        return classStart;
    }
    public String getClassEnd() {
        return classEnd;
    }
    public String getClassDay() {
        return classDay;
    }
    public String getClassNum() {
        return classNum;
    }

    public String getTerm() {
        return term;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public void setClassStart(String classStart) {
        this.classStart = classStart;
    }
    public void setClassEnd(String classEnd) {
        this.classEnd = classEnd;
    }
    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
