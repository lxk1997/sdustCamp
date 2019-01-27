package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.utils.ImageLoader;
import com.clxk.h.sdustcamp.utils.PathGet;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserProf extends MyBaseActivity {

    private RoundedImageView iv_userProf;
    private Button btn_selectImage;
    private Button btn_exitUserProf;
    private TextView tv_userInfo;
    private PathGet pathGet;
    private String URLPATH;
    private String OBJECTID;
    private Bitmap myBitmap;
    private boolean IS_CHANGED = true;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_prof);

        //find
        iv_userProf = (RoundedImageView)findViewById(R.id.iv_userProf);
        btn_exitUserProf = (Button)findViewById(R.id.btn_exitUserProf);
        btn_selectImage = (Button)findViewById(R.id.btn_selectImage);
        tv_userInfo = (TextView)findViewById(R.id.tv_userInfo);
        pathGet = new PathGet();

        user = BmobUser.getCurrentUser(User.class);

        //setListener
        tv_userInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getX() <= tv_userInfo.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(UserProf.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_mine);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_selectImage.setOnClickListener(new ButtonClickListener());
        btn_exitUserProf.setOnClickListener(new ButtonClickListener());

        if(user.getAvatar() == null) {
            iv_userProf.setImageResource(R.drawable.profile);
        } else {
            UserProf.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageLoader.getInstance().displayImage(iv_userProf,user.getAvatar().getFileUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_userInfo.setCompoundDrawables(drawable,null,null,null);
    }

    public class ButtonClickListener implements View.OnClickListener {

        Intent intent = null;
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_selectImage:
                    intent = new Intent(Intent.ACTION_PICK,null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent,1);
                    break;
                case R.id.btn_exitUserProf:
                    if(URLPATH == null) {
                        intent = new Intent(UserProf.this, MainActivity.class);
                        intent.putExtra("frId",R.id.ll_mine);
                        startActivity(intent);
                        finish();
                        break;
                    }

                    File file = new File(URLPATH);
                    final BmobFile bmobFile = new BmobFile(file);
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                user.setAvatar(bmobFile);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        user.update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                }).start();
                                Toast.makeText(UserProf.this,"上传成功",Toast.LENGTH_SHORT).show();
                                intent = new Intent(UserProf.this, MainActivity.class);
                                intent.putExtra("frId",R.id.ll_mine);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.i("userInfo上传失败","QAQApQAQApQAQAQAQ");
                            }
                        }
                    });
                    break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    cropPhoto(data.getData());
                    URLPATH = PathGet.getPath(UserProf.this,data.getData());
                    iv_userProf.setImageURI(data.getData());
                }
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("result-data",true);
        startActivityForResult(intent,3);
    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UserProf.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
