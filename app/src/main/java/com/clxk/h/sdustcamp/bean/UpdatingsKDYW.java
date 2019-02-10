package com.clxk.h.sdustcamp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class UpdatingsKDYW{
    private String title;
    private String context;
    private String time;
    private String image;

    public UpdatingsKDYW(String title, String context, String time, String image) {
        // TODO Auto-generated constructor stub
        this.title = title;
        this.context = context;
        this.time = time;
        this.image = image;
    }
    public UpdatingsKDYW() {

    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }
    public String getContext() {
        return this.context;
    }
    public String getTime() {
        return this.time;
    }
    public String getImage() {
        return image;
    }
}
