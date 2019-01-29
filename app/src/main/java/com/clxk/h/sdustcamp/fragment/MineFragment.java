package com.clxk.h.sdustcamp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.ui.ConnectStuId;
import com.clxk.h.sdustcamp.ui.LoginInActivity;
import com.clxk.h.sdustcamp.ui.MineChangePassword;
import com.clxk.h.sdustcamp.ui.SettingActivity;
import com.clxk.h.sdustcamp.ui.UserInfoActivity;
import com.clxk.h.sdustcamp.ui.UserProf;
import com.clxk.h.sdustcamp.utils.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import cn.bmob.v3.BmobUser;

/**
 * Created by Chook_lxk on 18/9
 *
 * 主页Fragment
 */
public class MineFragment extends Fragment {

    private LinearLayout ll_setting;
    private LinearLayout ll_modifyKey;
    private LinearLayout ll_stuId;
    private LinearLayout ll_stuQQ;
    private LinearLayout ll_stuWechat;

    private TextView tv_signIn;
    private RoundedImageView iv_prof;

    private MyApplication myApp;
    private View currentView;

    private User user;
    private boolean isLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_mine,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * view监听
     */
    private void initEvent() {

        ll_setting.setOnClickListener(new onClick());
        tv_signIn.setOnClickListener(new onClick());
        iv_prof.setOnClickListener(new onClick());
        ll_modifyKey.setOnClickListener(new onClick());
        ll_stuId.setOnClickListener(new onClick());
    }

    /**
     * 初始化View
     */
    private void initView() {

        ll_modifyKey = currentView.findViewById(R.id.ll_modifyKey);
        ll_setting = currentView.findViewById(R.id.ll_setting);
        ll_stuId = currentView.findViewById(R.id.ll_stuId);
        ll_stuQQ = currentView.findViewById(R.id.ll_stuQQ);
        ll_stuWechat = currentView.findViewById(R.id.ll_stuWecha);

        tv_signIn = currentView.findViewById(R.id.tv_signIn);
        iv_prof = currentView.findViewById(R.id.iv_prof);

        SharedPreferences sharedPreferences = currentView.getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("loginbool",false);

        if(isLogin) {
            user = BmobUser.getCurrentUser(User.class);
        }

        if(isLogin) {
            if(user.getNick() == null || user.getNick().length() == 0) {
                tv_signIn.setText(user.getMobilePhoneNumber());
            } else {
                tv_signIn.setText(user.getNick());
            }
            if(user.getAvatar() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ImageLoader.getInstance().displayImage(iv_prof,user.getAvatar().getFileUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    /**
     * ClickListener
     */
    class onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent;
            switch(v.getId()) {

                case R.id.tv_signIn:
                    if(!isLogin) {
                        intent = new Intent(getActivity(), LoginInActivity.class);
                    } else {
                        intent = new Intent(getActivity(), UserInfoActivity.class);
                    }
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.iv_prof:
                    if(!isLogin) {
                        intent = new Intent(getActivity(), LoginInActivity.class);
                    } else {
                        intent = new Intent(getActivity(), UserProf.class);
                    }
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_modifyKey:
                    if(!isLogin) {
                        intent = new Intent(getActivity(), LoginInActivity.class);
                    } else {
                        intent = new Intent(getActivity(), MineChangePassword.class);
                    }
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_setting:
                    if(!isLogin) {
                        intent = new Intent(getActivity(), LoginInActivity.class);
                    } else {
                        intent = new Intent(getActivity(), SettingActivity.class);
                    }
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_stuId:
                    if(!isLogin) {
                        intent = new Intent(getActivity(), LoginInActivity.class);
                    } else {
                        intent = new Intent(getActivity(), ConnectStuId.class);
                    }
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;

            }
        }
    }
}
