package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
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

import org.json.JSONException;

import java.io.IOException;

/**
 * Created on 19/1/28
 * 学号绑定
 */
public class ConnectStuId extends MyBaseActivity implements View.OnClickListener {

    private EditText et_sdustcode;
    private EditText et_sdustpass;
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


    }

    private void initView() {

        et_sdustcode = findViewById(R.id.et_sdustcode);
        et_sdustpass = findViewById(R.id.et_sdustpass);
        btn_connectsdust = findViewById(R.id.btn_sdustconnect);

        ib_back = findViewById(R.id.ib_market_header_back);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_sdustconnect:
                Log.i("111","aaaaa");
                authLogin(et_sdustcode.getText().toString(), et_sdustpass.getText().toString());
                break;
            case R.id.ib_market_header_back:
                Intent intent = new Intent(ConnectStuId.this, MainActivity.class);
                intent.putExtra("frId", R.id.ll_mine);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void authLogin(final String code, final String pass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int flag = AutoLogin.authLogin(code, pass);
                    Message msg = new Message();
                    msg.what = 2 + flag;
                    handle.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.i("111flag",msg.what + "");
            if (msg.what == 2) {
                et_sdustcode.setText(null);
                et_sdustpass.setText(null);
                Toast.makeText(ConnectStuId.this, "输入有误", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 3) {
                Toast.makeText(ConnectStuId.this, "绑定学号成功", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("stuconnect",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("stuid", String.valueOf(et_sdustcode.getText()));
                editor.putString("stupass", String.valueOf(et_sdustpass.getText()));
                editor.putBoolean("stulogin",true);
                editor.commit();
                Intent intent = new Intent(ConnectStuId.this, MainActivity.class);
                intent.putExtra("frId", R.id.ll_mine);
                startActivity(intent);
                getStuMsg();
                finish();
            }
        }
    };

    private void getStuMsg() {
        new Thread() {
            @Override
            public void run() {
                try {
                    AutoLogin.getStudentMsg();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ConnectStuId.this, MainActivity.class);
            intent.putExtra("frId", R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
