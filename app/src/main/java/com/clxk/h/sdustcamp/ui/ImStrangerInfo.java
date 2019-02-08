package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.NewFriend;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.message.AddFriendMessage;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Chook_lxk
 *
 * 查看陌生人名片
 */
public class ImStrangerInfo extends MyBaseActivity implements View.OnClickListener {

    private ImageButton ib_header_back;
    private RoundedImageView riv_im_user_avatar;
    private TextView tv_im_user_nick;
    private Button btn_im_user_add;
    private Button btn_im_user_chatAsStranger;
    private User stranger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_user_info);

        initView();

        ImServerConnect();

        initEvent();
    }

    private void ImServerConnect() {

        BmobUser user = BmobUser.getCurrentUser(User.class);
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
                    Toast.makeText(ImStrangerInfo.this,status.getMsg(),Toast.LENGTH_SHORT).show();
                    Log.i("UserUtils",BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        }
    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_header_back.setOnClickListener(this);
        btn_im_user_chatAsStranger.setOnClickListener(this);
        btn_im_user_add.setOnClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_header_back = findViewById(R.id.ib_market_header_back);
        riv_im_user_avatar = findViewById(R.id.riv_im_user_avatar);
        tv_im_user_nick = findViewById(R.id.tv_im_user_nick);
        btn_im_user_add = findViewById(R.id.btn_im_user_add);
        btn_im_user_chatAsStranger = findViewById(R.id.btn_im_user_chatAsStranger);

        stranger = MyApplication.getInstance().user;
        tv_im_user_nick.setText(stranger.getNick());
        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("loginbool",false) == true) {
            if(stranger.getMobilePhoneNumber().equals(BmobUser.getCurrentUser(User.class).getMobilePhoneNumber())) {
                btn_im_user_add.setClickable(false);
                btn_im_user_add.setVisibility(btn_im_user_add.INVISIBLE);
                btn_im_user_chatAsStranger.setClickable(false);
                btn_im_user_chatAsStranger.setVisibility(btn_im_user_add.INVISIBLE);
            }
        }
        if(stranger.getAvatar() != null) {
            Glide.with(riv_im_user_avatar).load(stranger.getAvatar().getFileUrl()).into(riv_im_user_avatar);
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                intent = new Intent(ImStrangerInfo.this, SearchUserActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_im_user_add:
                sendAddFriendMessage();
                break;
            case R.id.btn_im_user_chatAsStranger:
                chat();
                break;
        }
    }

    /**
     * 发送添加好友的请求
     */
    //TODO 好友管理：9.7、发送添加好友请求
    private void sendAddFriendMessage() {
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            Log.i("ImStrangerInfo","没有连接Im服务器");
            return;
        }
        NewFriend add = new NewFriend();
        add.setUid(stranger.getObjectId());
        add.setName(stranger.getNick());
        add.setAvatar(stranger.getAvatar().getFileUrl());
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(), add.getName(), add.getAvatar());
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        //TODO 这里只是举个例子，其实可以不需要传发送者的信息过去
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getNick());//发送者姓名
        map.put("avatar", currentUser.getAvatar());//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    Toast.makeText(ImStrangerInfo.this,"好友请求发送成功，等待验证",Toast.LENGTH_SHORT).show();
                } else {//发送失败
                    Toast.makeText(ImStrangerInfo.this,"发送失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 与陌生人聊天
     */
    private void chat() {
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            Log.i("ImStrangerInfo","没有连接Im服务器");
            return;
        }
        NewFriend add = new NewFriend();
        add.setUid(stranger.getObjectId());
        add.setName(stranger.getNick());
        add.setAvatar(stranger.getAvatar().getFileUrl());
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(), add.getName(), add.getAvatar());
        //TODO 会话：4.1、创建一个常态会话入口，陌生人聊天
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", conversationEntrance);
        Intent intent = new Intent(ImStrangerInfo.this,ChatActivity.class);
        intent.putExtra("c",bundle);
        startActivity(intent);
        finish();
    }
}
