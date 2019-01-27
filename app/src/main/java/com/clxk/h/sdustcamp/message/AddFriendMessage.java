package com.clxk.h.sdustcamp.message;

import cn.bmob.newim.bean.BmobIMExtraMessage;


/**
 * Created by Chook_lxk on 19/1/2
 *
 * 自定义添加好友请求
 */
public class AddFriendMessage extends BmobIMExtraMessage {

    public AddFriendMessage() {

    }

    @Override
    public String getMsgType() {
        return "add";
    }

    @Override
    public boolean isTransient() {
        return true;
    }
}
