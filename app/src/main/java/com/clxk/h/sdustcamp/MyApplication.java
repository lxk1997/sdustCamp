package com.clxk.h.sdustcamp;

import android.app.Application;
import android.content.Context;

import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.handle.MyMessageHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;

public class MyApplication extends Application {

    private static MyApplication myApp;
    public MarketGoods marketGoods = new MarketGoods();
    public User user = new User();
    public Map<String,String> cookie = new HashMap<>();

    public Context context;
    @Override
    public void onCreate() {
        myApp = this;
        super.onCreate();
        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
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
