package com.clxk.h.sdustcamp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;

public class SettingActivity extends AppCompatActivity {

    private TextView tv_titleSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //find

        tv_titleSetting = (TextView)findViewById(R.id.tv_titleSetting);


        //setOnClick

        tv_titleSetting.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getX() <= tv_titleSetting.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_mine);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_titleSetting.setCompoundDrawables(drawable,null,null,null);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}

