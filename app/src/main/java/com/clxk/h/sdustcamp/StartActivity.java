package com.clxk.h.sdustcamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;
import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;
import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.operator.KDYWOperator;
import com.clxk.h.sdustcamp.operator.SPKDOperator;
import com.clxk.h.sdustcamp.operator.XXGGOperator;
import com.clxk.h.sdustcamp.spider.AutoLogin;
import com.clxk.h.sdustcamp.spider.GetKDXW;
import com.clxk.h.sdustcamp.spider.GetSPKD;
import com.clxk.h.sdustcamp.spider.GetXXGG;
import com.clxk.h.sdustcamp.ui.MainActivity;
import com.clxk.h.sdustcamp.utils.UserUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class StartActivity extends MyBaseActivity {
    private List<UpdatingsKDYW> kdywList;
    private List<UpdatingsSPKD> spkdList;
    private List<UpdatingsXXGG> xxggList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //自动登陆
        autoLogin();
        //获取新闻动态
        getUpdatings();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    public void autoLogin() {
        SharedPreferences preferences = getSharedPreferences("stuconnect",MODE_PRIVATE);
        if(preferences == null || preferences.getBoolean("stulogin",false) == true) {
            final String stuid = preferences.getString("stuid","1234");
            final String stupass = preferences.getString("stupass","1234");
            new Thread() {
                @Override
                public void run() {
                    try {
                        AutoLogin.authLogin(stuid,stupass);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        if(sharedPreferences == null || sharedPreferences.getBoolean("loginbool",false) == false) return;
        final String phoneNum = sharedPreferences.getString("account","12345678");
        final String phoneKey = sharedPreferences.getString("password","12345678");
        if(phoneKey == null || phoneKey.equals("12345678") || phoneNum.equals("")) return;
        User user = new User();
        user.setUsername(phoneNum);
        user.setPassword(phoneKey);
        UserUtils.signIn(StartActivity.this,user);
    }

    private void getUpdatings() {
        new Thread() {
            @Override
            public void run() {
                try {
                    kdywList = GetKDXW.getKDYW(null);
                    spkdList = GetSPKD.getSPKD();
                    xxggList = GetXXGG.getXXGG();
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            KDYWOperator kdywOperator = new KDYWOperator(MyApplication.getInstance().context);
            SPKDOperator spkdOperator = new SPKDOperator(MyApplication.getInstance().context);
            XXGGOperator xxggOperator = new XXGGOperator(MyApplication.getInstance().context);
            kdywOperator.deleteAll();
            spkdOperator.deleteAll();
            xxggOperator.deleteAll();
            for(UpdatingsKDYW u: kdywList) {
                kdywOperator.add(u.getTitle(),u.getContext(),u.getTime(),u.getImage());
            }
            for(UpdatingsXXGG u: xxggList) {
                xxggOperator.add(u.getTitle(),u.getTime(),u.getContent());
            }
            for(UpdatingsSPKD u: spkdList) {
                spkdOperator.add(u.getImage(),u.getTitle(),u.getTime(),u.getHref());
            }
        }
    };
}
