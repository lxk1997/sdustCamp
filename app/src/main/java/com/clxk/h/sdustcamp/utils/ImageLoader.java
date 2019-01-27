package com.clxk.h.sdustcamp.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.helper.DiskLruCacheHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Chook_lxk on 18/12/20
 *
 * @function: 用来加载网络图片， 并缓存图片到本地和内存
 */
public class ImageLoader {

    private static ImageLoader mLoader;
    public LruCache<String, Bitmap> mLrucache;
    private DiskLruCacheHelper mDiskLrucache;
    public static ImageLoader getInstance() {
        if(mLoader == null) {
            synchronized (ImageLoader.class) {
                if(mLoader == null) {
                    mLoader = new ImageLoader();
                }
            }
        }
        return mLoader;
    }

    private ImageLoader() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mLrucache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        try {
            mDiskLrucache = new DiskLruCacheHelper(MyApplication.getInstance().context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayImage(ImageView view, String url) throws IOException{
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap != null) {
            view.setImageBitmap(bitmap);
        } else {
            downloadImage(view, url);
        }
    }

    private Bitmap getBitmapFromCache(String url) {
        if(url != null) {
            if(mLrucache.get(url) != null) {
                return mLrucache.get(url);
            }
            if(mDiskLrucache.get(url) != null) {
                return mDiskLrucache.getAsBitmap(url);
            }
            return null;
        }
        return null;
    }

    private void putBitmapToCache(Bitmap bitmap, String url) {
        if(bitmap != null) {
            mLrucache.put(url, bitmap);
            mDiskLrucache.put(url, bitmap);
        }
    }

    public void downloadImage(final ImageView view, final String url) {

        //456.jpg
        OkHttpUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), url.substring(url.length() - 12, url.length() - 4) + ".jpg", new OkHttpUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Bitmap bitmap = BitmapUtil.radioFile(file.getAbsolutePath(),view.getWidth(), view.getHeight());
                putBitmapToCache(bitmap, url);
                view.setImageBitmap(bitmap);
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed(Exception e) {

            }
        });
    }

}
