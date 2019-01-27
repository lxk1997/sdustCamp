package com.clxk.h.sdustcamp.bean;

import cn.bmob.v3.BmobObject;

public class Student extends BmobObject {
    private String phoneNum;
    private String password;
    private String username;
    private String stuCardId;

    public String getUserName() {
        return username;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public String getStuCardId() {
        return stuCardId;
    }
    public void setStuCardId(String stuCardId) {
        this.stuCardId = stuCardId;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}