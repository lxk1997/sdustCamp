package com.clxk.h.sdustcamp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.v3.BmobObject;

public class Updatings extends BmobObject implements Parcelable{
    private String title;
    private String context;
    private String rate;
    private String time;

    public Updatings(String readString, String readString2, String readString3, String readString4) {
        // TODO Auto-generated constructor stub
        this.title = readString;
        this.context = readString2;
        this.rate = readString3;
        this.time = readString4;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }
    public String getContext() {
        return this.context;
    }
    public String getRate() {
        return this.rate;
    }
    public String getTime() {
        return this.time;
    }

    public static final Creator<Updatings> CREATOR = new Creator<Updatings>() {
        @Override
        public Updatings createFromParcel(Parcel in) {
            return new Updatings(in.readString(),in.readString(),in.readString(),in.readString());
        }

        @Override
        public Updatings[] newArray(int size) {
            return new Updatings[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(getTitle());
        dest.writeString(getContext());
        dest.writeString(getRate());
        dest.writeString(getTime());
    }
}
