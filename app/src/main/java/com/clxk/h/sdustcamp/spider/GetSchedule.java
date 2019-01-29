package com.clxk.h.sdustcamp.spider;

import android.util.Log;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.TimeTable;

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

    private static String url = "http://jwgl.sdust.edu.cn/jsxsd/xskb/xskb_list.do";
    private static Map<String,String> cookie = MyApplication.getInstance().cookie;
    private static String term;

    public static List<BmobObject> getSchedule() throws IOException {
        List<BmobObject> sources = new ArrayList<>();
        Connection connection = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .cookies(cookie).timeout(3000);
        Connection.Response res = connection.execute();
        if(res.statusCode() == 404) {
            return null;
        } else {
            Document doc = res.parse();
            Elements els = doc.getElementsByTag("option");
            term = els.select("option[selected=selected]").html();
            Log.i("GetSchedule",term);
            els = doc.getElementById("kbtable").getElementsByTag("tr");
            for(int i = 1; i <= 4; i++) {
                Elements td = els.get(i).getElementsByTag("td");
                for(int j = 0; j < 7; j++) {
                    String arrclass = td.get(j).getElementsByTag("div").get(1).text();
                    if(arrclass == null || arrclass.equals("")) {
                        continue;
                    } else {
                        String spclass[] = arrclass.split("\\s");
                        if(spclass.length == 5) {
                            sources.add(new TimeTable(spclass[0],spclass[4],spclass[2],spclass[3].split("-")[0]
                                    ,spclass[3].split("-")[1].substring(0,spclass[3].split("-")[1].length()-3),j+1+"",i+"",term));
                        } else if(spclass.length == 11) {
                            sources.add(new TimeTable(spclass[0],spclass[4],spclass[2],spclass[3].split("-")[0]
                                    ,spclass[3].split("-")[1].substring(0,1),j+1+"",i+"",term));
                            sources.add(new TimeTable(spclass[6],spclass[10],spclass[8],spclass[9].split("-")[0]
                                    ,spclass[9].split("-")[1].substring(0,spclass[9].split("-")[1].length()-3),j+1+"",i+"",term));
                        }
                    }
                }
            }
            return sources;
        }

    }
}
