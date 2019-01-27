package com.clxk.h.sdustcamp.bean;


import cn.bmob.v3.BmobObject;

/**好友表
 * @author Chook_lck
 * @project Friend
 * @date 19/1
 */
//TODO 好友管理：9.1、创建好友表
public class Friend extends BmobObject {
    //用户
    private User user;
    //好友
    private User friendUser;

    public void setUser(User user) {
        this.user = user;
    }
    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public User getUser() {
        return this.user;
    }
    public User getFriendUser() {
        return this.friendUser;
    }
}
