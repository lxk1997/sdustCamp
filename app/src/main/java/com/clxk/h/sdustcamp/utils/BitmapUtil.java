package com.clxk.h.sdustcamp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Chook_lxk on 18/12/20
 *
 * @function Bitmap解码压缩
 */
public class BitmapUtil {

    /**
     * 压缩本地资源文件并获取压缩优化之后的Bitmap对象
     * @param filePath
     * @param pixelW
     * @param piexlH
     * @return
     */
    public static Bitmap radioFile(String filePath, int pixelW, int piexlH) {

        BitmapFactory.Options options = new BitmapFactory.Options();//声明Options对象
        options.inJustDecodeBounds = true; //加载的时候只加载图片的宽高属性，不加载原图
        options.inPreferredConfig = Bitmap.Config.RGB_565;//降低色彩模式，如果对透明度没有要求，RGB_565即可满足需求
        //预加载
        BitmapFactory.decodeFile(filePath, options);

        //获得原图的宽高属性
        int originalW = options.outWidth;
        int originalH = options.outHeight;

        options.inSampleSize = getSimpleSize(originalW, originalH, pixelW, piexlH);//按照比例确定宽、高，降低分辨率
        options.inJustDecodeBounds = false;//取消加载时只加载宽高
        return BitmapFactory.decodeFile(filePath, options);//加载图片
    }

    /**
     * 根据原图和目的控件的大小确定压缩比例
     * @param originalW
     * @param originalH
     * @param pixelW
     * @param pixelH
     * @return
     */
    private static int getSimpleSize(int originalW, int originalH, int pixelW, int pixelH) {

        int simpleSize = 1;
        if(originalW > originalH && originalW > pixelW) { //按照宽度确定比例
            simpleSize = originalW / pixelW;
        } else if(originalH > originalW && originalH > pixelH) { //按照高度确定比例
            simpleSize = originalH / pixelH;
        }
        if(simpleSize <= 1) { //原图比目的控件小，加载原图
            simpleSize = 1;
        }
        return simpleSize;
    }

}
