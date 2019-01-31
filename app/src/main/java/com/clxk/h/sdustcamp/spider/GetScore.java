package com.clxk.h.sdustcamp.spider;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.Score;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created on 19/1/29
 *
 * 爬取成绩数据
 */
public class GetScore {

    private static String url = "http://jwgl.sdust.edu.cn/jsxsd/kscj/cjcx_list";
    private static Map<String,String> cookie = MyApplication.getInstance().cookie;

    public static List<Score> getScore() throws IOException {



        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookie).timeout(3000).execute();
        return null;

    }
}
