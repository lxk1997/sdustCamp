package com.clxk.h.sdustcamp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.ui.MainActivity;
import com.clxk.h.sdustcamp.utils.UserUtils;

public class StartActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //自动登陆
        autoLogin();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    public void autoLogin() {
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
}
