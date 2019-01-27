package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.Updatings;
import com.clxk.h.sdustcamp.operator.BmobOperatorUpdatings;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Crawler extends AppCompatActivity {

    private String URL = "http://www.sdust.edu.cn/";
    private BmobOperatorUpdatings bmobOperatorUpdatings = new BmobOperatorUpdatings();
    public void GetUpdatingKDYW(final Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Document doc = null;
                Updatings updatings = null;
                try {
                    doc = Jsoup.connect(URL).get();
                    Elements els = doc.select(".text-item.cl");
                    for(Element e: els) {
                        //title
                        String title = "";
                        String content = "";
                        String rate = "9999";
                        String arr[] = e.text().split("\\s");
                        for(int i = 0; i < arr.length - 1; i++) {
                            if (i == 0) title = arr[i];
                            else title += ' ' + arr[i];
                        }
                        String time = arr[arr.length - 1];
                        if(e.attr("href").substring(0, 4).equals("info")) {
                            Document dec_detail = Jsoup.connect(URL + e.attr("href")).get();
                            if(dec_detail.select("div#vsb_content_1001").size() == 0) continue;
                            Elements els_detail = dec_detail.select(".v_news_content").select("p");
                            for(Element e_detail : els_detail) {
                                content += e_detail.text();
                                content += '\n';
                                if(e_detail.attr("class").equals("vsbcontent_end") || (e_detail != els_detail.get(0) && e_detail.attr("class").equals("vsbcontent_img"))) break;
                            }
                            updatings = new Updatings(title, content, rate, time);
                            UpdatingsAdd(context, updatings);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void UpdatingsAdd(final Context context, final Updatings updatings) {
        BmobQuery<Updatings> bmobQuery = new BmobQuery<Updatings>();
        bmobQuery.findObjects(new FindListener<Updatings>() {
            @Override
            public void done(List<Updatings> list, BmobException e) {
                if(e == null) {
                    boolean is_exist = false;
                    for(Updatings up:list) {
                        if(up.getTitle().equals(updatings.getTitle())) {
                            is_exist = true;
                            break;
                        }
                    }
                    if(!is_exist) {
                        bmobOperatorUpdatings.add(context,updatings);
                    }
                }
            }
        });
    }
}
