package com.clxk.h.sdustcamp.spider;

import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.EmptyClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetEmptyClass {
    private static String url = "http://jwgl.sdust.edu.cn/app.do?method=getKxJscx";

    public static List<EmptyClass> getEmptyClass(String time, String idletime) throws JSONException, IOException {
        List<EmptyClass> list = new ArrayList<>();
       url = url + "&time="+time+"&idleTime="+idletime;
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("token", MyApplication.getInstance().sdust_token)
                .timeout(3000).execute();
        if(response.body() == null || response.body().equals("") || response.body().trim().equals("[null]")) {
            return null;
        }
        JSONArray array = new JSONArray(response.body());
        Log.i("123",array.length()+"");
        String jxl;
        for(int i = 0; i < array.length(); i++) {
            JSONObject object = (JSONObject) array.get(i);
            jxl = object.getString("jxl");
            JSONArray classarr = object.getJSONArray("jsList");
            for(int j = 0; j < classarr.length(); j++) {
                JSONObject js = (JSONObject) classarr.get(j);
                String jsmc = js.getString("jsmc");
                String jzwid = js.getString("jzwid")+"";
                String  cap = js.getString("zws");
                list.add(new EmptyClass(jsmc,cap,jzwid));
            }
        }
        return list;
    }
}
