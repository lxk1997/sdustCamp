package com.clxk.h.sdustcamp.message;

import cn.bmob.newim.bean.BmobIMExtraMessage;

public class AgreeAddFriendMessage extends BmobIMExtraMessage {

    private String uid;//最初发送方
    private long time;
    private String msg;//通知栏显示

    @Override
    public String getMsgType() {
        return "agree";
    }

    @Override
    public boolean isTransient() {
        return false;
    }
}
