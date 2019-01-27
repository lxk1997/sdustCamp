package com.clxk.h.sdustcamp.bean;

import android.content.Intent;

/**本地的好友请求表
 * @author :smile
 * @project:NewFriend
 * @date :2016-04-26-17:28
 */
//TODO 好友管理：9.4、本地数据库存储添加好友的请求
public class NewFriend implements java.io.Serializable {

    private Long id;
    //用户uid
    private String uid;
    //留言消息
    private String msg;
    //用户名
    private String name;
    //头像
    private String avatar;
    //状态：未读、已读、已添加、已拒绝等
    private Integer status;
    //请求时间
    private Long time;

    //getter setter...
    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setTime(Long time) {
        this.time = time;
    }

    public String getUid() {
        return this.uid;
    }
    public String getMsg() {
        return this.msg;
    }
    public String getName() {
        return this.name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public Integer getStatus() {
        return this.status;
    }
    public Long getTime() {
        return this.time;
    }
}
