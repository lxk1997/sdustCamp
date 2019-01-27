package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.SearchUserAdapter;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Chook_lxk
 *
 * 查找好友类
 */
public class SearchUserActivity extends MyBaseActivity implements View.OnClickListener {

    private EditText et_search_user;
    private Button btn_search_user;
    private ImageButton ib_header_back;

    private ListView lv_search_user;
    private List<User> source;
    private SearchUserAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        initView();

        initEvent();
    }

    /**
     * View事件
     */
    private void initEvent() {

        btn_search_user.setOnClickListener(this);

        ib_header_back.setOnClickListener(this);

    }

    /**
     * View初始化
     */
    private void initView() {

        et_search_user = findViewById(R.id.et_friendNum);
        btn_search_user = findViewById(R.id.btn_search_user);

        ib_header_back = findViewById(R.id.ib_market_header_back);

        lv_search_user = findViewById(R.id.lv_search_user);
        source = new ArrayList<>();
    }

    /**
     * 从数据库中获取User
     */
    private void getUser() {

        source.clear();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null) {
                    for(User u:list) {
                        if(u.getMobilePhoneNumber().equals(et_search_user.getText().toString().trim())) {
                            source.add(u);
                            break;
                        }
                    }
                    mAdapter = new SearchUserAdapter(SearchUserActivity.this,R.id.lv_search_user,source);
                    lv_search_user.setAdapter(mAdapter);
                    if(source.size() == 0) {
                        Toast.makeText(SearchUserActivity.this, "搜索ID不存在",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SearchUserActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_search_user:
                getUser();
                break;
            case R.id.ib_market_header_back:
                Intent intent = new Intent(SearchUserActivity.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_news);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(SearchUserActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_news);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
