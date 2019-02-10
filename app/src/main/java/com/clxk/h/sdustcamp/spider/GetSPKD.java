package com.clxk.h.sdustcamp.spider;

import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 19/2/10
 * 视频科大爬取
 */
public class GetSPKD {

    private static String url="http://www.sdust.edu.cn/sylm/stkd.htm";
    private static String url_pre = "http://www.sdust.edu.cn";

    public static List<UpdatingsSPKD> getSPKD () throws IOException {
        List<UpdatingsSPKD> sources = new ArrayList<>();
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").timeout(3000).execute();

        if(response.parse().select("li[class=video-item]").size() == 0) {
            return null;
        } else {
            Document document = response.parse();
            Elements lis = document.select("li[class=video-item]");
            for(Element li:lis) {
                String href = li.getElementsByTag("a").get(0).attr("href");
                String image = url_pre + li.getElementsByClass("img").get(0).child(0).attr("src");
                String title = li.getElementsByClass("title").text();
                String time = li.getElementsByClass("time").text();
                UpdatingsSPKD updatingsSPKD = new UpdatingsSPKD(image,title,time,href);
                sources.add(updatingsSPKD);
            }
            return sources;
        }
    }
}
