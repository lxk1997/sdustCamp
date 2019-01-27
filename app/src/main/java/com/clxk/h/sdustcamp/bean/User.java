package com.clxk.h.sdustcamp.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author Chook_lxk
 * 用户类
 */
public class User extends BmobUser {

    private String sex;
    private String name;
    private int age;
    private BmobFile avatar;
    private String nick;

    //setter
    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }

    public User setNick(String nick) {
        this.nick = nick;
        return this;
    }

    //getter
    public String getSex() {
        return this.sex;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public BmobFile getAvatar() {
        return this.avatar;
    }

    public String getNick() {
        return this.nick;
    }



}
