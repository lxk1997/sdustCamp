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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MineChangePassword extends AppCompatActivity {

    private TextView tv_cpasswordInTitle;
    private EditText et_oldPassword;
    private EditText et_newPassword;
    private EditText et_phoneModify;
    private TextView tv_showPhone;
    private Button btn_phoneModify;
    private Button btn_modifyChange;
    private CountDownTimerUtils myCountDownTimerUtils;
    private EventHandler eh;
    private String OBJECTID;
    private Boolean is_true = false;
    private String oldPassword;
    private String newPassword;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_cpassword);

        //find
        tv_cpasswordInTitle = (TextView)findViewById(R.id.tv_cpasswordInTitle);
        et_newPassword = (EditText)findViewById(R.id.et_newPassword);
        et_oldPassword = (EditText)findViewById(R.id.et_oldPassword);
        et_phoneModify = (EditText)findViewById(R.id.et_phoneModify);
        tv_showPhone = (TextView)findViewById(R.id.tv_showPhone);
        btn_modifyChange = (Button)findViewById(R.id.btn_modifyChange);
        btn_phoneModify = (Button)findViewById(R.id.btn_phoneModify);
        user = BmobUser.getCurrentUser(User.class);

        myCountDownTimerUtils = new CountDownTimerUtils(btn_phoneModify, 30000, 1000);

        //setListener
        tv_cpasswordInTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getX() <= tv_cpasswordInTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(MineChangePassword.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_mine);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_phoneModify.setOnClickListener(new ButtonClickListener());
        btn_modifyChange.setOnClickListener(new ButtonClickListener());

        tv_showPhone.setText("您绑定的手机号为" + user.getMobilePhoneNumber().substring(0,3) + "****" + user.getMobilePhoneNumber().substring(7,11));


        MobSDK.init(this, "27ab25ef673ca", "906d826efb97dc27ca6df4907ab66243");
        // 短信验证回调
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        oldPassword = et_oldPassword.getText().toString().trim();
                        newPassword = et_newPassword.getText().toString().trim();
                        //提交验证码成功
                        user.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null) {
                                    Toast.makeText(MineChangePassword.this, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MineChangePassword.this, LoginInActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MineChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    et_oldPassword.setText("");
                                    et_newPassword.setText("");
                                    et_phoneModify.setText("");
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
                            Toast.makeText(MineChangePassword.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
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
        tv_cpasswordInTitle.setCompoundDrawables(drawable,null,null,null);
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
            switch (view.getId()) {
                case R.id.btn_phoneModify:
                    if(et_oldPassword.length() < 8 || et_oldPassword.length() > 15) {
                        Toast.makeText(MineChangePassword.this,"原密码输入有误",Toast.LENGTH_SHORT).show();
                        et_oldPassword.setText("");
                        et_newPassword.setText("");
                        et_phoneModify.setText("");
                        et_oldPassword.requestFocus();
                    } else {
                        GetCode(user.getMobilePhoneNumber());
                        et_phoneModify.requestFocus();
                        myCountDownTimerUtils.start();
                    }
                    break;
                case R.id.btn_modifyChange:
                    String phoneKey = et_newPassword.getText().toString().trim();
                    if(phoneKey.length() < 8 || phoneKey.length() > 15) {
                        Toast.makeText(MineChangePassword.this, "新密码长度不小于8位", Toast.LENGTH_SHORT).show();
                    } else {
                        sendCode(user.getMobilePhoneNumber(),et_phoneModify.getText().toString().trim());
                    }
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MineChangePassword.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
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
