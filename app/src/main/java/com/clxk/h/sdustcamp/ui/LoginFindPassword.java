package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.utils.CountDownTimerUtils;
import com.mob.MobSDK;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginFindPassword extends AppCompatActivity {

    private TextView tv_findPasswordInTitle;
    private EditText et_phoneModify;
    private EditText et_Phone;
    private Button btn_phoneModify;
    private Button btn_modifyPhone;
    private EventHandler eh;
    private CountDownTimerUtils myCountDownTimerUtils;
    private String PHONE;
    private String OBJECTID;
    private Boolean is_true = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_findpassword);

        //find
        tv_findPasswordInTitle = (TextView)findViewById(R.id.tv_findPasswordInTitle);
        et_Phone = (EditText) findViewById(R.id.et_phone);
        et_phoneModify = (EditText)findViewById(R.id.et_phoneModify);
        btn_modifyPhone = (Button)findViewById(R.id.btn_modifyPhone);
        btn_phoneModify = (Button)findViewById(R.id.btn_phoneModify);

        myCountDownTimerUtils = new CountDownTimerUtils(btn_phoneModify, 30000, 1000);

        //setListener
        tv_findPasswordInTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getX() <= tv_findPasswordInTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(LoginFindPassword.this, LoginInActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_phoneModify.setOnClickListener(new ButtonClickListener());
        btn_modifyPhone.setOnClickListener(new ButtonClickListener());

        MobSDK.init(this, "27ab25ef673ca", "906d826efb97dc27ca6df4907ab66243");
        // 短信验证回调
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        PHONE = et_Phone.getText().toString().trim();
                        //提交验证码成功
                        BmobQuery<User> bmobQuery = new BmobQuery<User>();
                        bmobQuery.findObjects(new FindListener<User>() {

                            @Override
                            public void done(List<User> object, BmobException arg1) {
                                // TODO Auto-generated method stub
                                if(arg1 == null) {
                                    Log.i("查询成功：共" + object.size() + "条数据。","123");
                                    for(User t: object) {
                                        Log.i("id",t.getMobilePhoneNumber());
                                        if(t.getMobilePhoneNumber().equals(PHONE)) {
                                            OBJECTID = t.getObjectId();
                                            is_true = true;
                                            break;
                                        }
                                    }
                                    if(is_true == false) {
                                        Toast.makeText(LoginFindPassword.this, "该手机号未注册", Toast.LENGTH_SHORT).show();
                                        et_Phone.setText("");
                                        et_phoneModify.setText("");

                                    } else {
                                        Intent intent = new Intent(LoginFindPassword.this, LoginChangePassword.class);
                                        intent.putExtra("phone",PHONE);
                                        intent.putExtra("id",OBJECTID);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(LoginFindPassword.this, "未知错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
                            Toast.makeText(LoginFindPassword.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                            et_phoneModify.setText("");
                            et_phoneModify.requestFocus();
                        }
                    });
                    Log.i("EventHandler", "回调失败");
                }
            }
        };

        SMSSDK.registerEventHandler(eh);//注册短信回调接口

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_findPasswordInTitle.setCompoundDrawables(drawable,null,null,null);
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


    public class ButtonClickListener implements View.OnClickListener {

        Intent intent = null;
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_phoneModify:
                    if(et_Phone.getText().toString().length() != 11) {
                        Toast.makeText(LoginFindPassword.this,"手机号不存在",Toast.LENGTH_SHORT).show();
                        et_Phone.setText("");
                        et_phoneModify.setText("");
                        break;
                    } else {
                        GetCode(et_Phone.getText().toString().trim());
                        et_phoneModify.requestFocus();
                        myCountDownTimerUtils.start();
                    }
                    break;
                case R.id.btn_modifyPhone:
                    sendCode(et_Phone.getText().toString().trim(), et_phoneModify.getText().toString().trim());
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginFindPassword.this, LoginInActivity.class);
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
