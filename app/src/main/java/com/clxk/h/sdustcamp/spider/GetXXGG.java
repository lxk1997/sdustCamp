package com.clxk.h.sdustcamp.spider;

import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;

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
 * 获取学校公告
 */
public class GetXXGG {

    private static String url="http://www.sdust.edu.cn/kdgg.jsp?urltype=tree.TreeTempUrl&wbtreeid=1035";
    private static String url_pre = "http://www.sdust.edu.cn/";

    public static List<UpdatingsXXGG> getXXGG() throws IOException {
        List<UpdatingsXXGG> sources = new ArrayList<>();

        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").timeout(3000).execute();
        if(response.parse().select("div[class=new-list]").size() == 0) {
            return null;
        } else {
            Document document = response.parse();
            Elements lis = document.getElementsByClass("new-list").get(0).getElementsByTag("li");
            for(Element li:lis) {
                String title = li.getElementsByTag("a").attr("title");
                String time = li.getElementsByTag("span").get(1).text();
                String url = url_pre + li.getElementsByTag("a").attr("href");
                Connection.Response response1 = Jsoup.connect(url).method(Connection.Method.GET)
                        .userAgent("Mozilla").timeout(3000).execute();
                String content = "";
                Document document1 = response1.parse();
                if(document.select("div[class=v_news_content]").size() == 0) {
                    UpdatingsXXGG updatingsXXGG = new UpdatingsXXGG(title,time,"");
                    sources.add(updatingsXXGG);
                    continue;
                }
                Elements ps = document.select("div[class=v_news_content]").get(0).getElementsByTag("p");
                for(Element p : ps) {
                    content += "   ";
                    content += p.text();
                    content += '\n';
                }
                UpdatingsXXGG updatingsXXGG = new UpdatingsXXGG(title,time,content);
                sources.add(updatingsXXGG);
            }
            return sources;
        }
    }
}
