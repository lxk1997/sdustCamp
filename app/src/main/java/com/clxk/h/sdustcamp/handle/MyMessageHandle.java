package com.clxk.h.sdustcamp.handle;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;



/**
 * Created by Chook_lxk
 *
 * 消息接收
 */
public class MyMessageHandle extends BmobIMMessageHandler {


    @Override
    public void onMessageReceive(final MessageEvent event) {
        //在线消息
        super.onMessageReceive(event);
        EventBus.getDefault().post(event);


    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
    }
}
