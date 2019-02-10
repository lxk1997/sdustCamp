package com.clxk.h.sdustcamp.bean;

/**
 * Created on 19/2/10
 * 视频科大bean
 */
public class UpdatingsSPKD {

    private String image;
    private String title;
    private String time;
    private String href;

    public UpdatingsSPKD() {

    }
    public UpdatingsSPKD(String image, String title, String time, String href) {
        this.href = href;
        this.time = time;
        this.title = title;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}
