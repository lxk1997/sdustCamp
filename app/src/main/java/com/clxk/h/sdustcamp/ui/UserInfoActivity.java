package com.clxk.h.sdustcamp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobUser;

public class UserInfoActivity extends AppCompatActivity {

    private TextView tv_titleUserInfo;
    private Button btn_userExit;
    private TextView tv_userName;
    private TextView tv_userNime;
    private TextView tv_userSex;
    private MyApplication myApp;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //find
        tv_titleUserInfo = (TextView)findViewById(R.id.tv_userInfo);
        btn_userExit = (Button)findViewById(R.id.btn_exitUser);
        tv_userName = (TextView)findViewById(R.id.tv_userName);
        tv_userNime = (TextView)findViewById(R.id.tv_userNime);
        tv_userSex = (TextView)findViewById(R.id.tv_userSex);

        user = BmobUser.getCurrentUser(User.class);

        //setOnClick
        tv_userNime.setOnClickListener(new ButtonClickListener());
        btn_userExit.setOnClickListener(new ButtonClickListener());
        tv_titleUserInfo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getX() <= tv_titleUserInfo.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_mine);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });


        tv_userName.setText(user.getMobilePhoneNumber());
        tv_userNime.setText(user.getNick());
        tv_userSex.setText(user.getSex());


        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_titleUserInfo.setCompoundDrawables(drawable,null,null,null);

        drawable = ContextCompat.getDrawable(this,R.drawable.arrowrightt);
        drawable.setBounds(0,0,50,50);
        tv_userNime.setCompoundDrawables(null,null,drawable,null);

    }

    private class ButtonClickListener implements View.OnClickListener {
        Intent intent = null;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_exitUser:
                    AlertDialog isExit = new AlertDialog.Builder(UserInfoActivity.this)
                            .setTitle("")
                            .setMessage("您确认要退出当前账号吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BmobUser.logOut();
                                    //TODO 连接：3.2、退出登录需要断开与IM服务器的连接
                                    BmobIM.getInstance().disConnect();
                                    Toast.makeText(UserInfoActivity.this, "退出成功",Toast.LENGTH_SHORT).show();
                                    intent = new Intent(UserInfoActivity.this, MainActivity.class);
                                    intent.putExtra("frId",R.id.ll_mine);
                                    SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("loginbool",false);
                                    editor.commit();
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    break;
                case R.id.tv_userNime:
                    intent = new Intent(UserInfoActivity.this,UserNime.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
