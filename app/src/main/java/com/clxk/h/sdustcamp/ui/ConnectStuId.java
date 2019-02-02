package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.spider.AutoLogin;

import java.io.IOException;

/**
 * Created on 19/1/28
 * 学号绑定
 */
public class ConnectStuId extends MyBaseActivity implements View.OnClickListener, View.OnTouchListener {

    private EditText et_sdustcode;
    private EditText et_sdustpass;
    private EditText et_sdustvertif;
    private ImageView iv_sdustvertif;
    private Button btn_connectsdust;

    private ImageButton ib_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectstuid);

        initView();

        initEvent();

    }

    private void initEvent() {

        btn_connectsdust.setOnClickListener(this);
        ib_back.setOnClickListener(this);

        iv_sdustvertif.setOnTouchListener(this);

        getSafeCode();

    }

    private void initView() {

        et_sdustcode = findViewById(R.id.et_sdustcode);
        et_sdustpass = findViewById(R.id.et_sdustpass);
        et_sdustvertif = findViewById(R.id.et_sdustvertif);
        iv_sdustvertif = findViewById(R.id.iv_sdustvertif);
        btn_connectsdust = findViewById(R.id.btn_sdustconnect);

        ib_back = findViewById(R.id.ib_market_header_back);
    }

    public void getSafeCode() {
        //验证码显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AutoLogin.getSafeCode();
                    Message msg = new Message();
                    msg.what = 1;
                    handle.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sdustconnect:
                authLogin(et_sdustcode.getText().toString(),et_sdustpass.getText().toString(),et_sdustvertif.getText().toString());
                break;
            case R.id.ib_market_header_back:
                Intent intent = new Intent(ConnectStuId.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_mine);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void authLogin(final String code, final String pass, final String authcode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AutoLogin.authLogin(code, pass, authcode);
                    Message msg = new Message();
                    msg.what = 2;
                    handle.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.iv_sdustvertif) {
            getSafeCode();
        }
        return false;
    }

    public Handler handle = new Handler() {

        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            if(msg.what == 1) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/safecode.png");
                       iv_sdustvertif.setImageBitmap(bitmap);
                   }
               });
            }
            if(msg.what == 2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ConnectStuId.this, "绑定学号成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return true;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ConnectStuId.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
