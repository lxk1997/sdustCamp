package com.clxk.h.sdustcamp.bean;

/**
 * Created by Chook_lxk on 19/1/15
 * 消息类
 */
public class NewsMsg {

    private String avator;
    private String title;
    private String content;
    private String date;

    //setter
    public void setAvator(String url) {
        this.avator = url;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }

    //getter
    public String getAvator() {
        return this.avator;
    }
    public String getTitle() {
        return this.title;
    }
    public String getContent() {
        return this.content;
    }
    public String getDate() {
        return this.date;
    }
}
