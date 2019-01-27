package com.clxk.h.sdustcamp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.ImMyFriendAdapter;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.Friend;
import com.clxk.h.sdustcamp.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Chook_lxk on 19/1/2
 *
 * 我的好友列表
 */
public class ImMineFriendActivity extends MyBaseActivity implements View.OnClickListener {

    private ImageButton ib_hearder_back;
    private ListView lv_friend_list;
    private List<Friend> source;
    private ImMyFriendAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_friend_list);

        initView();

        initEvent();

    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_hearder_back.setOnClickListener(this);
        getFriendList();
        mAdapter = new ImMyFriendAdapter(ImMineFriendActivity.this, R.id.lv_friend_list,source);
        lv_friend_list.setAdapter(mAdapter);
    }

    /**
     * 获取好友列表
     */
    private void getFriendList() {

        BmobQuery<Friend> bmobQuery = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(User.class);
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.include("friendUser");
        bmobQuery.order("-updateAt");
        bmobQuery.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if(e == null) {
                    for(Friend f: list) {
                        source.add(f);
                    }
                } else {
                    Toast.makeText(ImMineFriendActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_hearder_back = findViewById(R.id.ib_market_header_back);
        lv_friend_list = findViewById(R.id.lv_friend_list);
        source = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {

    }
}
