package com.clxk.h.sdustcamp.bean;

public class Student{
    private String username;
    private String school;
    private String stuid;

    public Student() {

    }
    public Student(String stuid,String username, String school) {
        this.stuid = stuid;
        this.school = school;
        this.username = username;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getSchool() {
        return school;
    }

    public String getUsername() {
        return username;
    }

    public String getStuid() {
        return stuid;
    }
}