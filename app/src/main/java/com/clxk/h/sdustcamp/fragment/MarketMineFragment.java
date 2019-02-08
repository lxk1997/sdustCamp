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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.ui.MainActivity;
import com.clxk.h.sdustcamp.ui.MarketMineAddress;
import com.clxk.h.sdustcamp.ui.MarketMineGoods;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

import cn.bmob.v3.BmobUser;

/**
 * Ctrated by Chook_lxk
 * 二手市场-我的
 */
public class MarketMineFragment extends Fragment implements View.OnClickListener {

    private View currentView;

    private RoundedImageView riv_marketMineProf;
    private TextView tv_marketMineUserName;

    private LinearLayout ll_market_unsed;
    private LinearLayout ll_market_address;

    private ImageButton ib_market_back;

    private Boolean isLogin;

    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView =  inflater.inflate(R.layout.fragment_market_mine,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * View监听
     */
    private void initEvent() {

        ib_market_back.setOnClickListener(this);

        ll_market_address.setOnClickListener(this);
        ll_market_unsed.setOnClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        riv_marketMineProf = currentView.findViewById(R.id.riv_marketMineProf);
        tv_marketMineUserName = currentView.findViewById(R.id.tv_marketMineUserName);

        SharedPreferences sharedPreferences = currentView.getContext().getSharedPreferences("login",Context.MODE_PRIVATE);
        isLogin = sharedPreferences.getBoolean("loginbool",false);
        if(isLogin) {
            user = BmobUser.getCurrentUser(User.class);
        }

        ll_market_unsed = currentView.findViewById(R.id.ll_market_unused);
        ll_market_address = currentView.findViewById(R.id.ll_market_address);

        ib_market_back = currentView.findViewById(R.id.ib_market_back);

        //初始化头像和昵称
        if(isLogin) {
            if(user.getNick() == null || user.getNick().length() == 0) {
                tv_marketMineUserName.setText(user.getMobilePhoneNumber());
            } else {
                tv_marketMineUserName.setText(user.getNick());
            }
            if(user.getAvatar() != null) {
                Glide.with(riv_marketMineProf).load(user.getAvatar().getFileUrl()).into(riv_marketMineProf);
            }
        }

    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.ll_market_address:
                if(!isLogin) {
                    Toast.makeText(currentView.getContext(), "您还未登录，请先登录！",Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(getActivity(), MarketMineAddress.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                }
                break;
            case R.id.ll_market_unused:
                if(!isLogin) {
                    Toast.makeText(currentView.getContext(), "您还未登录，请先登录！",Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(getActivity(), MarketMineGoods.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                }
                break;
            case R.id.ib_market_back:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
        }
    }
}
