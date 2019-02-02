package com.clxk.h.sdustcamp.spider;

import android.content.Context;
import android.content.SharedPreferences;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfSchedule;
import com.clxk.h.sdustcamp.utils.LocalSave;
import com.clxk.h.sdustcamp.utils.Utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoLogin {
    private static String url_safecode = "http://jwgl.sdust.edu.cn/verifycode.servlet?0.124567"; // 验证码
    private static String url_encode = "http://jwgl.sdust.edu.cn/Logon.do?method=logon&flag=sess"; // 加密字符串
    private static String url_Login = "http://jwgl.sdust.edu.cn/Logon.do?method=logon"; // 登录
    private String username = "";
    private String password = "";
    private static String path = "/sdcard/safecode.png";
    private static SharedPreferences sp = MyApplication.getInstance().context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    private static Map<String, String> cookie;
    private static LocalSave localSave = new LocalSave(sp);

    /**
     * 下载验证码
     * 保存Cookie
     * @throws IOException
     */
    public static void getSafeCode() throws IOException {
        Connection.Response response = Jsoup.connect(url_safecode).ignoreContentType(true) // 获取图片需设置忽略内容类型
                .userAgent("Mozilla").method(Connection.Method.GET).timeout(3000).execute();
        cookie = response.cookies();
        localSave.setPreferences(sp);
        localSave.setCookies(cookie);
        byte[] bytes = response.bodyAsBytes();
        Utils.saveFile(path, bytes);
    }

    /**
     * 登录教务系统
     */
    public static void initLogin(String code,String username, String password) throws IOException {
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put("view", "1");
            data.put("encoded", getEncoded(username, password));
            data.put("RANDOMCODE", code);
            Connection connect = Jsoup.connect(url_Login)
                    .header("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .userAgent("Mozilla").method(Connection.Method.POST).data(data).cookies(cookie).timeout(3000);
            Connection.Response response = connect.execute();
            localSave.setCookies(response.cookies());
        } catch (IOException e) {

        }
    }

    /**
     * 加密参数（依具体环境而定，加密算法一般在JS中获得）
     */
    public static String getEncoded(String username, String password) {
        try {
            Connection connect = Jsoup.connect(url_encode)
                    .header("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .userAgent("Mozilla").method(Connection.Method.POST).cookies(cookie).timeout(3000);

            Connection.Response response = connect.execute();
            String dataStr = response.parse().text();
            // 把JS中的加密算法用Java写一遍：
            String scode = dataStr.split("#")[0];
            String sxh = dataStr.split("#")[1];
            String code = username + "%%%" + password;
            String encoded = "";
            for (int i = 0; i < code.length(); i++) {
                if (i < 20) {
                    encoded = encoded + code.substring(i, i + 1)
                            + scode.substring(0, Integer.parseInt(sxh.substring(i, i + 1)));
                    scode = scode.substring(Integer.parseInt(sxh.substring(i, i + 1)), scode.length());
                } else {
                    encoded = encoded + code.substring(i, code.length());
                    i = code.length();
                }
            }
            return encoded;
        } catch (IOException e) {

        }
        return null;
    }

    public static int authLogin(String username, String password, String code) throws IOException {
        initLogin(code,username,password);
        List<TimeTable> schedules = GetSchedule.getSchedule();
        MySQLiteOperatorOfSchedule sqLiteOperator = new MySQLiteOperatorOfSchedule(MyApplication.getInstance().context);
        sqLiteOperator.deleteAll();
        for(TimeTable t : schedules) {
            sqLiteOperator.add(t.getClassName(),t.getClassRoom(),t.getTeacher(),t.getClassStart(),t.getClassEnd(),t.getClassDay(),t.getClassNum(),t.getTerm());
        }
        return 1;
    }
}
