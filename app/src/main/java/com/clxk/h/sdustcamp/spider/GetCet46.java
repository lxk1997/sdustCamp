package com.clxk.h.sdustcamp.spider;

import com.clxk.h.sdustcamp.bean.Cet46;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCet46 {

    private static String url="https://www.chsi.com.cn/cet/query";

    public static List<Cet46> getCet46(String id, String name) throws IOException {
        List<Cet46> cet46s = new ArrayList<>();
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET)
                .userAgent("Mozilla").data("zkzh", id).data("xm",name).timeout(3000).execute();

        if(response.parse().toString().indexOf("查询结果") <= 0) {
            return null;
        }
        Document document = response.parse();
        Element table = document.select("table[name=cetTable]").get(0);
        Elements trs = table.getElementsByTag("tr");
        String sname  = trs.get(0).getElementsByTag("td").get(0).text().trim();
        String school  = trs.get(1).getElementsByTag("td").get(0).text().trim();
        String testlevel  = trs.get(2).getElementsByTag("td").get(0).text().trim();
        String testid  = trs.get(4).getElementsByTag("td").get(0).text().trim();
        String grade  = trs.get(5).getElementsByTag("td").get(0).text().trim();
        String listen  = trs.get(6).getElementsByTag("td").get(1).text().trim();
        String read  = trs.get(7).getElementsByTag("td").get(1).text().trim();
        String write  = trs.get(8).getElementsByTag("td").get(1).text().trim();
        String readlevel  = trs.get(11).getElementsByTag("td").get(0).text().trim();

        Cet46 cet4 = new Cet46(name,school,testlevel,testid,grade,listen,read,write,readlevel);
        cet46s.add(cet4);
        return cet46s;
    }
}
