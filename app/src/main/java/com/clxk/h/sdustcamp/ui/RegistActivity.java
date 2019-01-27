package com.clxk.h.sdustcamp.ui;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.operator.MySQLiteOperator;
import com.clxk.h.sdustcamp.utils.CountDownTimerUtils;
import com.clxk.h.sdustcamp.utils.UserUtils;
import com.mob.MobSDK;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegistActivity extends AppCompatActivity {

    private EditText et_phoneNum;
    private EditText et_phoneKey;
    private EditText et_phoneModify;
    private Button btn_phoneModify;
    private Button btn_phoneRegist;
    private ImageView iv_logo;

    private boolean is_registed;
    private String phoneNum;
    private String phoneKey;
    private Boolean is_true;

    private TextView tv_registTitle;
    private EventHandler eh;
    private MySQLiteOperator mySqlLiteOperator;
    private CountDownTimerUtils myCountDownTimerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一：默认初始化
        Bmob.initialize(this, "dbd18b062cdaf305286133d99022b40d");
        setContentView(R.layout.activity_regist);

        //find
        tv_registTitle = (TextView)findViewById(R.id.tv_registTitle);
        et_phoneNum = (EditText)findViewById(R.id.et_phoneNum);
        et_phoneKey = (EditText)findViewById(R.id.et_phoneKey);
        et_phoneModify = (EditText)findViewById(R.id.et_phoneModify);
        btn_phoneModify = (Button)findViewById(R.id.btn_phoneModify);
        btn_phoneRegist = (Button)findViewById(R.id.btn_phoneRegist);
        iv_logo = (ImageView)findViewById(R.id.iv_loginLogo);

        myCountDownTimerUtils = new CountDownTimerUtils(btn_phoneModify, 30000, 1000);
        mySqlLiteOperator = new MySQLiteOperator(this);
        et_phoneNum.setText("");
        et_phoneKey.setText("");
        et_phoneModify.setText("");

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_registTitle.setCompoundDrawables(drawable,null,null,null);

        drawable = ContextCompat.getDrawable(this,R.drawable.stuphone);
        drawable.setBounds(0,0,42,42);
        et_phoneNum.setCompoundDrawables(drawable,null,null,null);

        drawable = ContextCompat.getDrawable(this,R.drawable.key2);
        drawable.setBounds(0,0,50,50);
        et_phoneKey.setCompoundDrawables(drawable,null,null,null);

        //setOnClick
        tv_registTitle.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getX() <= tv_registTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(RegistActivity.this, LoginInActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_phoneModify.setOnClickListener(new ButtonClickListener());
        btn_phoneRegist.setOnClickListener(new ButtonClickListener());


        MobSDK.init(this, "27ab25ef673ca", "906d826efb97dc27ca6df4907ab66243");
        // 短信验证回调
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        //第二：新建一个User
                        User user = new User();
                        user.setUsername(phoneNum);
                        user.setMobilePhoneNumber(phoneNum);
                        user.setPassword(phoneKey);
                        UserUtils.signUp(RegistActivity.this,user);

                        Log.i("EventHandler", "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成
                        Log.i("EventHandler", "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.i("EventHandler", "返回支持发送验证码的国家列表");
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegistActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                            et_phoneModify.setText("");
                            et_phoneModify.requestFocus();
                        }
                    });
                    Log.i("EventHandler", "回调失败");
                }
            }
        };

        SMSSDK.registerEventHandler(eh);//注册短信回调接口
    }

    // 获取验证码
    public void GetCode(String PhoneNumber)
    {
        SMSSDK.getVerificationCode("86", PhoneNumber);

    }

    // 提交验证码
    public void sendCode(String PhoneNumber,String VerifyCode)
    {
        SMSSDK.submitVerificationCode("86", PhoneNumber,VerifyCode);
    }

    //最后注销监听，否则可能会造成内存泄露，我这里随便找的位置调用的，就是在一次认证成功之后，可以放在关闭程序的时候调用
    public void unRegisterEventHandler()
    {
        SMSSDK.unregisterEventHandler(eh);
    }

    //OnClickListener
    class ButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch(v.getId()) {
                case R.id.btn_phoneModify:
                    phoneNum = et_phoneNum.getText().toString().trim();
                    phoneKey = et_phoneKey.getText().toString().trim();
                    if(phoneNum.length() != 11) {
                        Toast.makeText(RegistActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    } else if(phoneKey.length() < 8 || phoneKey.length() > 15) {
                        Toast.makeText(RegistActivity.this, "密码长度不小于8位", Toast.LENGTH_SHORT).show();
                    } else {
                        GetCode(phoneNum);
                        et_phoneModify.requestFocus();
                        myCountDownTimerUtils.start();
                    }
                    break;
                case R.id.btn_phoneRegist:
                    phoneNum = et_phoneNum.getText().toString().trim();
                    phoneKey = et_phoneKey.getText().toString().trim();
                    if(phoneNum.length() != 11) {
                        Toast.makeText(RegistActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    } else if(phoneKey.length() < 8 || phoneKey.length() > 15) {
                        Toast.makeText(RegistActivity.this, "密码长度不小于8位", Toast.LENGTH_SHORT).show();
                    } else {
                        sendCode(phoneNum,et_phoneModify.getText().toString().trim());
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent  = new Intent(RegistActivity.this, LoginInActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
