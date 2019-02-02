package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.content.SharedPreferences;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfScore;
import com.clxk.h.sdustcamp.utils.LocalSave;

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

    private static String url = "http://jwgl.sdust.edu.cn/jsxsd/kscj/cjcx_list";
    private static SharedPreferences sp = MyApplication.getInstance().context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    private static LocalSave localSave = new LocalSave(sp);
    private static Map<String,String> cookie = localSave.getCookies();

    public static List<Score> getScore() throws IOException {
        List<Score> scores = new ArrayList<>();
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookie).timeout(3000).execute();
        if(response.statusCode() == 404) {
            return null;
        } else {
            Document doc = response.parse();
            Elements els = doc.getElementById("dataList").getElementsByTag("tr");
            for(Element e : els) {
                if(e == els.get(0)) continue;
                Elements td = e.getElementsByTag("td");
                Score score = new Score(td.get(0).text(),td.get(1).text(),td.get(2).text(),td.get(3).text(),td.get(4).text(),td.get(5).text(),td.get(6)
                        .text(),td.get(7).text(),td.get(8).text(),td.get(9).text(),td.get(10).text(),td.get(11).text());
                scores.add(score);
            }
            return scores;
        }

    }
}
