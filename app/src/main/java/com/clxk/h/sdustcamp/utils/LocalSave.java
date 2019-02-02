package com.clxk.h.sdustcamp.utils;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 19/2/2
 * 保存文件到本地
 */
public class LocalSave {

    private SharedPreferences preferences;

    public LocalSave(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void setPreferences(SharedPreferences sharedPreferences) {
        this.preferences = sharedPreferences;
    }

    public void setCookies(Map<String, String> cookies) {
        SharedPreferences.Editor edit = preferences.edit();
        for(Map.Entry<String,String> cookie : cookies.entrySet()) {
            edit.putString(cookie.getKey(),cookie.getValue());
            Log.i("lalala",cookie.getKey() + " " +cookie.getValue());
        }
        edit.commit();
    }

    public Map<String,String> getCookies() {
        Map<String, String> cookies = new HashMap<>();
        String session = preferences.getString("JSESSIONID","123");
        cookies.put("JSESSIONID",session);
        return cookies;
    }
}
