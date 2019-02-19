package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.utils.LocalSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobObject;

/**
 * Created on 2019/1/28
 * 爬取课程表
 */
public class GetSchedule {

    private static String url = "http://jwgl.sdust.edu.cn/app.do?method=getKbcxAzc";

    public static List<TimeTable> getSchedule() throws IOException, JSONException {
        GetCurrentTime.getCurrentTime();
//        if(MyApplication.getInstance().zc == 0) {
//            return null;
//        }
        List<TimeTable> sources = new ArrayList<>();
        for(int i = 1; i < 30; i++) {
            String url_cur = url+"&xh="+MyApplication.getInstance().student.getStuid()+"&xnxqid=2018-2019-2"+"&zc="+i;
            Log.i("111",url_cur);
            Connection connection = Jsoup.connect(url_cur).method(Connection.Method.GET)
                    .userAgent("Mozilla").header("token",MyApplication.getInstance().sdust_token)
                    .timeout(3000);
            Connection.Response res = connection.execute();
            if(res.body() == null || res.body().equals("") || res.body().trim().equals("[null]")) {
                break;
            }
            Log.i("111",res.body()+"111");
            JSONArray jsonArray = new JSONArray(res.body());
            for(int u = 0; u < jsonArray.length(); u++) {
                JSONObject o = (JSONObject) jsonArray.get(u);
                TimeTable table = new TimeTable(o.getString("kcmc"),o.getString("jsmc"),o.getString("jsxm"),o.getString("kkzc").split("-")[0]
                ,o.getString("kkzc").split("-")[1],o.get("kcsj").toString().substring(0,1),Integer.valueOf(o.get("kcsj").toString().substring(4,5))/2+"",MyApplication.getInstance().term);
                sources.add(table);
            }
        }

        return sources;
    }
}
