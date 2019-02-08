package com.clxk.h.sdustcamp.spider;

import android.support.v7.app.AppCompatActivity;

import com.clxk.h.sdustcamp.bean.Updatings;
import com.clxk.h.sdustcamp.operator.BmobOperatorUpdatings;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetKDXW extends AppCompatActivity {

    private static String url = "http://www.sdust.edu.cn/sylm/kdyw.htm";
    private static String url_pre = "http://www.sdust.edu.cn";

    public static List<Updatings> getKDYW(String title) throws IOException {

        List<Updatings> updatings = new ArrayList<>();
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).userAgent("Mozilla")
                .timeout(3000).execute();

        Document doc = response.parse();
        Elements lis = doc.select("div[class=new-list]").select("li");
        for(Element li:lis) {
            String href = url_pre + li.getElementsByTag("a").get(0).attr("href").substring(2);
            if(li.getElementsByTag("a").get(0).attr("title").equals(title)) {
                break;
            }
            Connection.Response res_1 = Jsoup.connect(href).method(Connection.Method.GET).userAgent("Mozilla")
                    .timeout(3000).execute();
            Document doc_1 = res_1.parse();
            String nodeTitle = doc_1.select("p[class=title]").html();
            String nodeTime = doc_1.select("p[class=time]").html().substring(5, 15);
            Elements ps = doc_1.getElementsByClass("v_news_content").get(0).children();
            String nodeContent = "";
            String url_image = null;
            for(Element p:ps) {
                if(p.getElementsByTag("img").size() != 0) {
                    url_image = url_pre + p.getElementsByTag("img").attr("src");
                    continue;
                }
                nodeContent+="   ";
                nodeContent+=p.text();
                nodeContent += '\n';
            }
            if (url_image == null || url_image.equals("") || url_image.equals(url_pre)){
                Updatings up = new Updatings(nodeTitle, nodeContent,nodeTime,null);
                updatings.add(up);
            } else {
                Updatings up = new Updatings(nodeTitle, nodeContent,nodeTime,url_image);
                updatings.add(up);
            }
        }
        return updatings;
    }

}
