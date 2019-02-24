package com.clxk.h.sdustcamp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class MarketGoods extends BmobObject implements Parcelable {

    private String userId;
    private String gName;
    private int gPrice;
    private String gDetail;
    private String gImage1;
    private String gImage2;
    private String gImage3;
    private int gStatue;

    protected MarketGoods(Parcel in) {
        userId = in.readString();
        gName = in.readString();
        gPrice = in.readInt();
        gDetail = in.readString();
        gStatue = in.readInt();
    }

    public MarketGoods() {
        this.gImage1 = null;
        this.gImage3 = null;
        this.gImage3 = null;
        this.gStatue = 1;
    }
    public static final Creator<MarketGoods> CREATOR = new Creator<MarketGoods>() {
        @Override
        public MarketGoods createFromParcel(Parcel in) {
            return new MarketGoods(in);
        }

        @Override
        public MarketGoods[] newArray(int size) {
            return new MarketGoods[size];
        }
    };

    //setter
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setgName(String gName) {
        this.gName = gName;
    }
    public void setgPrice(int gPrice) {
        this.gPrice = gPrice;
    }
    public void setgDetail(String gDetail) {
        this.gDetail = gDetail;
    }
    public void setgImage1(String gImage1) {
        this.gImage1 = gImage1;
    }
    public void setgImage2(String gImage2) {
        this.gImage2 = gImage2;
    }
    public void setgImage3(String gImage3) {
        this.gImage3 = gImage3;
    }
    public void setgStatue(int statue) {
        this.gStatue = statue;
    }
    //getter
    public String getUserId() {
        return this.userId;
    }
    public String getgName() {
        return this.gName;
    }
    public int getgPrice() {
        return this.gPrice;
    }
    public String getgDetail() {
        return this.gDetail;
    }
    public String getgImage1() {
        return this.gImage1;
    }
    public String getgImage2() {
        return this.gImage2;
    }
    public String getgImage3() {
        return this.gImage3;
    }
    public int getgStatue() {return this.gStatue; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(gName);
        dest.writeInt(gPrice);
        dest.writeString(gDetail);
        dest.writeString(gImage1);
        dest.writeString(gImage2);
        dest.writeString(gImage3);
        dest.writeInt(gStatue);
    }
}
