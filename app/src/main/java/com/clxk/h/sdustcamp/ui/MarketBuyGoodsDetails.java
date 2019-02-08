package com.clxk.h.sdustcamp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

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
        tv_2marketTitle = findViewById(R.id.tv_2marketInTitle);
        iv_2marketSellerPro = findViewById(R.id.iv_2marketSellerPro);
        TextView tv_2marketGoodsName = findViewById(R.id.tv_2marketGoodsName);
        TextView tv_2marketSellerAddress = findViewById(R.id.tv_2marketSellerAddress);
        tv_2marketSellerName = findViewById(R.id.tv_2marketSellerName);
        TextView tv_2marketUserTime = findViewById(R.id.tv_2marketUserTime);
        TextView tv_2marketGoodsMoney = findViewById(R.id.tv_2marketGoodsMoney);
        TextView tv_2marketGoodsDetails = findViewById(R.id.tv_2marketGoodsDetails);
        iv_2marketGoodsImage1 = findViewById(R.id.iv_2marketGoodsImage1);
        iv_2marketGoodsImage2 = findViewById(R.id.iv_2marketGoodsImage2);
        iv_2marketGoodsImage3 = findViewById(R.id.iv_2marketGoodsImage3);
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
                                Glide.with(iv_2marketSellerPro).load(user.getAvatar().getFileUrl()).into(iv_2marketSellerPro);
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
            Glide.with(iv_2marketGoodsImage1).load(mg.getgImage1().getFileUrl()).into(iv_2marketGoodsImage1);
        }
        if(mg.getgImage2() != null){
            Glide.with(iv_2marketGoodsImage2).load(mg.getgImage2().getFileUrl()).into(iv_2marketGoodsImage2);
        }
        if(mg.getgImage3() != null){
            Glide.with(iv_2marketGoodsImage3).load(mg.getgImage3().getFileUrl()).into(iv_2marketGoodsImage3);
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
