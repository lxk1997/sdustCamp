package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.bean.Student;
import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfSchedule;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfScore;
import com.clxk.h.sdustcamp.utils.LocalSave;
import com.clxk.h.sdustcamp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoLogin {

    private static String url_Login = "http://jwgl.sdust.edu.cn/app.do?method=authUser";
    private String username = "";
    private String password = "";


    /**
     * 登录教务系统
     */
    public static int initLogin(String username, String password) throws IOException, JSONException {
        url_Login = url_Login + "&xh="+username+"&pwd="+password;
        Connection connect = Jsoup.connect(url_Login).userAgent("Mozilla")
                .method(Connection.Method.GET).timeout(3000);
        Connection.Response response = connect.execute();
        String body = response.body();
        JSONObject json = new JSONObject(body);
        String flag = json.getString("flag");
        if(flag.equals("0")) {
            return 0;
        } else {
            String name = json.getString("userrealname");
            String school = json.getString("userdwmc");
            String token = json.getString("token");
            Log.i("111",name);
            Log.i("111",school);
            Log.i("111",token);

            MyApplication.getInstance().student = new Student(username,name,school);
            MyApplication.getInstance().sdust_token = token;
            return 1;
        }

    }


    public static int authLogin(String username, String password) throws IOException, JSONException {
        int flag = initLogin(username, password);
        if (flag == 0) return 0;
        List<TimeTable> schedules = GetSchedule.getSchedule();
        MySQLiteOperatorOfSchedule sqLiteOperator = new MySQLiteOperatorOfSchedule(MyApplication.getInstance().context);
        sqLiteOperator.deleteAll();
        if(schedules != null) {
            for (TimeTable t : schedules) {
                sqLiteOperator.add(t.getClassName(), t.getClassRoom(), t.getTeacher(), t.getClassStart(), t.getClassEnd(), t.getClassDay(), t.getClassNum(), t.getTerm());
            }
        }
        List<Score> scores = GetScore.getScore();
        MySQLiteOperatorOfScore sqLiteOperator_2 = new MySQLiteOperatorOfScore(MyApplication.getInstance().context);
        sqLiteOperator_2.deleteAll();
        for (Score s : scores) {
            sqLiteOperator_2.add(s.getTerm(), s.getClassname(), s.getScore(), s.getScoretag(), s.getClassprop()
                    , s.getCredit(), s.getGpa(), s.getTestway(), s.getRemark());
        }
        return 1;
    }

}
