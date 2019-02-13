package com.clxk.h.sdustcamp.spider;

import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取当前的周次，学期
 */
public class GetCurrentTime {
    private static String url="http://jwgl.sdust.edu.cn/app.do?method=getCurrentTime&currDate=";
    private static int zc;
    private static String term;

    public static void getCurrentTime() throws IOException, JSONException {

        long longmillis = System.currentTimeMillis();
        Date date = new Date(longmillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = dateFormat.format(date);

        url = url+str_date;
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("token", MyApplication.getInstance().sdust_token).execute();

        JSONObject jsonObject = new JSONObject(response.body());
        if(jsonObject.getString("zc") == null || jsonObject.getString("zc").equals("null")) {
            Log.i("111",response.body());
            return;
        }
        zc = Integer.valueOf(jsonObject.getString("zc"));
        term = jsonObject.getString("xnxqh");
        MyApplication.getInstance().zc = zc;
        MyApplication.getInstance().term = term;
        Log.i("111",zc+" "+term);
    }
}
