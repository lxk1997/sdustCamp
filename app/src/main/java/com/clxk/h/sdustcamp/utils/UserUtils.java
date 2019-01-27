package com.clxk.h.sdustcamp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.ui.LoginInActivity;
import com.clxk.h.sdustcamp.ui.MainActivity;

import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.content.Context.MODE_PRIVATE;
import static com.mob.tools.utils.DeviceHelper.getApplication;

/**
 * Created by Chook_lxk
 *
 * 用户登陆注册类
 */
public class UserUtils {


    /**
     * 用户注册
     * @param context
     * @param user
     */
    public static void signUp(final Context context, User user) {

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null) {
                    Toast.makeText(context,"注册成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LoginInActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                } else {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signIn(final Context context, User user) {

        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null) {
                    user = BmobUser.getCurrentUser(User.class);
                    Toast.makeText(context,"登陆成功！",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("account", user.getUsername());
                    editor.putBoolean("loginbool", true);
                    editor.commit();


                    //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
                    if (!TextUtils.isEmpty(user.getObjectId())&&
                            BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
                        BmobIM.connect(user.getObjectId(), new ConnectListener() {
                            @Override
                            public void done(String uid, BmobException e) {
                                if (e == null) {
                                    final User user1 = BmobUser.getCurrentUser(User.class);
                                    Log.i("UserUtils","连接成功");
                                    //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                                    BmobIM.getInstance().
                                            updateUserInfo(new BmobIMUserInfo(user1.getObjectId(),
                                                    user1.getUsername(), user1.getAvatar().getFileUrl()));
                                } else {
                                    Log.i("UserUtils",e.getMessage());
                                }
                            }
                        });
                        //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
                        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                            @Override
                            public void onChange(ConnectionStatus status) {
                                Toast.makeText(context,status.getMsg(),Toast.LENGTH_SHORT).show();
                                Log.i("UserUtils",BmobIM.getInstance().getCurrentStatus().getMsg());
                            }
                        });
                    }
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("frId", R.id.ll_index);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                } else {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loginbool", false);
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("frId",R.id.ll_index);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });
    }

}
