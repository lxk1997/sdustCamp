package com.clxk.h.sdustcamp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.utils.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MarketBuyGoodsDetails extends AppCompatActivity implements View.OnTouchListener{

    private TextView tv_2marketTitle;
    private RoundedImageView iv_2marketSellerPro;
    private TextView tv_2marketSellerName;
    private TextView tv_2marketSellerAddress;
    private TextView tv_2marketUserTime;
    private TextView tv_2marketGoodsName;
    private TextView tv_2marketGoodsMoney;
    private TextView tv_2marketGoodsDetails;
    private ImageView iv_2marketGoodsImage1;
    private ImageView iv_2marketGoodsImage2;
    private ImageView iv_2marketGoodsImage3;
    private MarketGoods mg;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_buy_details);
        setWindowStatusBarColor(this, Color.parseColor("#0359a2"));

        //find
        tv_2marketTitle = (TextView)findViewById(R.id.tv_2marketInTitle);
        iv_2marketSellerPro = (RoundedImageView) findViewById(R.id.iv_2marketSellerPro);
        tv_2marketGoodsName = (TextView)findViewById(R.id.tv_2marketGoodsName);
        tv_2marketSellerAddress = (TextView)findViewById(R.id.tv_2marketSellerAddress);
        tv_2marketSellerName = (TextView)findViewById(R.id.tv_2marketSellerName);
        tv_2marketUserTime = (TextView)findViewById(R.id.tv_2marketUserTime);
        tv_2marketGoodsMoney = (TextView)findViewById(R.id.tv_2marketGoodsMoney);
        tv_2marketGoodsDetails = (TextView)findViewById(R.id.tv_2marketGoodsDetails);
        iv_2marketGoodsImage1 = (ImageView)findViewById(R.id.iv_2marketGoodsImage1);
        iv_2marketGoodsImage2 = (ImageView)findViewById(R.id.iv_2marketGoodsImage2);
        iv_2marketGoodsImage3 = (ImageView)findViewById(R.id.iv_2marketGoodsImage3);
        mg = MyApplication.getInstance().marketGoods;

        //setText
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null) {
                    for(User u : list) {
                        if(u.getMobilePhoneNumber().equals(mg.getUserId())) {
                            user = u;
                            if(user.getNick() == null) {
                                tv_2marketSellerName.setText(user.getMobilePhoneNumber());
                            } else {
                                tv_2marketSellerName.setText(user.getNick());
                            }
                            if(user.getAvatar() == null) {
                                iv_2marketSellerPro.setImageResource(R.drawable.profile);
                            } else {
                                MarketBuyGoodsDetails.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if(user.getAvatar() != null) ImageLoader.getInstance().displayImage(iv_2marketSellerPro,user.getAvatar().getFileUrl());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            break;
                        }
                    }
                }
            }
        });

        tv_2marketGoodsName.setText(mg.getgName());
        tv_2marketSellerAddress.setText("山东 淄博");
        //tv_2marketUserTime.setText("刚刚来过");
        tv_2marketGoodsMoney.setText(mg.getgPrice()+"¥");
        tv_2marketGoodsDetails.setText(mg.getgDetail());
        if(mg.getgImage1() != null){
            MarketBuyGoodsDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageLoader.getInstance().displayImage(iv_2marketGoodsImage1,mg.getgImage1().getFileUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if(mg.getgImage2() != null){
            MarketBuyGoodsDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageLoader.getInstance().displayImage(iv_2marketGoodsImage2,mg.getgImage2().getFileUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if(mg.getgImage3() != null){
            MarketBuyGoodsDetails.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ImageLoader.getInstance().displayImage(iv_2marketGoodsImage3,mg.getgImage3().getFileUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //setOnClick
        tv_2marketTitle.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if(event.getX() <= tv_2marketTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(MarketBuyGoodsDetails.this, MarketActivity.class);
                    intent.putExtra("frId",R.id.ll_market_market);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        //drawable
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_2marketTitle.setCompoundDrawables(drawable,null,null,null);

    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){

            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent  = new Intent(MarketBuyGoodsDetails.this, MarketActivity.class);
            intent.putExtra("frId",R.id.ll_market_market);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
