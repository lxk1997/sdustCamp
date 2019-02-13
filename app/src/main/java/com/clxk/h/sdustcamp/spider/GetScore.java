package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfScore;
import com.clxk.h.sdustcamp.utils.LocalSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 19/1/29
 *
 * 爬取成绩数据
 */
public class GetScore {

    private static String url = "http://jwgl.sdust.edu.cn/app.do?method=getCjcx";

    public static List<Score> getScore() throws IOException, JSONException {
        List<Score> scores = new ArrayList<>();
        url = url+"&xh="+MyApplication.getInstance().student.getStuid() + "&xnxqid=";
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("token",MyApplication.getInstance().sdust_token)
                .timeout(3000).execute();
        JSONArray jsonArray = new JSONArray(response.body());
        Log.i("111","array "+jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = (JSONObject) jsonArray.get(i);
            double gpa;
            switch (o.getString("zcj")) {
                case "优":
                    gpa = 4.5;
                    break;
                case "良":
                    gpa = 3.5;
                    break;
                case "中":
                    gpa = 2.5;
                    break;
                case "及格":
                    gpa = 1.5;
                    break;
                case "差":
                    gpa = 0.5;
                    break;
                case "不及格":
                    gpa = 0.5;
                    break;
                    default: gpa = (Double.valueOf(o.getString("zcj"))/10.0 - 5);
            }
            Score score = new Score(o.getString("xqmc"),o.getString("kcmc"),o.getString("zcj"),o.getString("cjbsmc")
            ,o.getString("kclbmc"),o.getString("xf"),String.format("%.02f",gpa),o.getString("ksxzmc"),o.getString("bz"));
            scores.add(score);
        }
        return scores;

    }
}
