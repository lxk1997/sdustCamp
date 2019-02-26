package com.clxk.h.sdustcamp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
    public static String getYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    public static String getMonth() {
        return new SimpleDateFormat("MM").format(new Date());
    }
    public static String getDay() {
        return new SimpleDateFormat("dd").format(new Date());
    }
    public static String getData() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    public static String getTime() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }
}
