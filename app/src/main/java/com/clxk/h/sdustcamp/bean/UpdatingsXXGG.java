package com.clxk.h.sdustcamp.bean;

/**
 * Created on 19/2/10
 * 动态-学校公告bean
 */
public class UpdatingsXXGG {

    private String title;
    private String time;
    private String content;

    public UpdatingsXXGG() {

    }
    public UpdatingsXXGG(String title, String time, String content) {
        this.time = time;
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
