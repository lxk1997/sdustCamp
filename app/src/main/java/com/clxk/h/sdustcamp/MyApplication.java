package com.clxk.h.sdustcamp;

import android.app.Application;
import android.content.Context;

import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.Student;
import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;
import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;
import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.handle.MyMessageHandle;
import com.mob.MobSDK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;

public class MyApplication extends Application {

    private static MyApplication myApp;
    public MarketGoods marketGoods = new MarketGoods();
    public UpdatingsKDYW updatings = new UpdatingsKDYW();
    public UpdatingsSPKD updatingsSPKD = new UpdatingsSPKD();
    public UpdatingsXXGG updatingsXXGG = new UpdatingsXXGG();
    public User user = new User();
    public Student student = new Student();
    public String sdust_token = "";
    public String sdust_id = "";
    public String sdust_name = "";
    public String sdust_school = "";
    public String password = "";
    public int zc;
    public String term;

    public Context context;
    @Override
    public void onCreate() {
        myApp = this;
        super.onCreate();
        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            MobSDK.init(this);
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new MyMessageHandle());
        }
    }

    public static MyApplication getInstance(){
        return myApp;
    }
    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
