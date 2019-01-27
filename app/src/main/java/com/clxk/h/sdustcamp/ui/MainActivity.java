package com.clxk.h.sdustcamp.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clxk.h.sdustcamp.Constans;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.fragment.MainFragment;
import com.clxk.h.sdustcamp.fragment.MineFragment;
import com.clxk.h.sdustcamp.fragment.NewsFragment;
import com.clxk.h.sdustcamp.fragment.ServiceFragment;
import com.clxk.h.sdustcamp.fragment.UpdatingsFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallationManager;

/**
 * Created by Chook_lxk on 18/9
 * 主页
 */
public class MainActivity extends MyBaseActivity {

    private LinearLayout ll_updatings;
    private LinearLayout ll_service;
    private LinearLayout ll_index;
    private LinearLayout ll_news;
    private LinearLayout ll_mine;

    private ImageButton ib_updatings;
    private ImageButton ib_service;
    private ImageButton ib_index;
    private ImageButton ib_news;
    private ImageButton ib_mine;

    private Fragment fr_main;
    private Fragment fr_updatings;
    private Fragment fr_service;
    private Fragment fr_news;
    private Fragment fr_mine;

    private int down_flag = 0;
    private long st_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndGetPermission();

        initView();
        initEvent();


        setSelect(getIntent().getIntExtra("frId",R.id.ll_index));

        MyApplication.getInstance().context = this;

        //TODO 集成：1.4、初始化数据服务SDK、初始化设备信息并启动推送服务
        // 初始化BmobSDK
        Bmob.initialize(this, "dbd18b062cdaf305286133d99022b40d");
        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstallationId();
        BmobInstallationManager.getInstance().getCurrentInstallation();
        // 启动推送服务
        //BmobPush.startWork(this);

    }

    //ClickListener
    class onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            setSelect(v.getId());
        }
    }

    private void setSelect(int u) {

        resetImageButton();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);

        switch (u) {
            case R.id.ll_updatings:
                if(fr_updatings == null) {
                    fr_updatings = new UpdatingsFragment();
                    transaction.add(R.id.fl_main, fr_updatings);
                } else {
                    transaction.show(fr_updatings);
                }
                ib_updatings.setImageResource(R.drawable.ic_updatings_fill);
                break;
            case R.id.ll_service:
                if(fr_service == null) {
                    fr_service = new ServiceFragment();
                    transaction.add(R.id.fl_main, fr_service);
                } else {
                    transaction.show(fr_service);
                }
                ib_service.setImageResource(R.drawable.ic_server_fill);
                break;
            case R.id.ll_news:
                if(fr_news == null) {
                    fr_news = new NewsFragment();
                    transaction.add(R.id.fl_main, fr_news);
                } else {
                    transaction.show(fr_news);
                }
                ib_news.setImageResource(R.drawable.ic_news_fill);
                break;
            case R.id.ll_mine:
                if(fr_mine == null) {
                    fr_mine = new MineFragment();
                    transaction.add(R.id.fl_main, fr_mine);
                } else {
                    transaction.show(fr_mine);
                }
                ib_mine.setImageResource(R.drawable.ic_mine_fill);
                break;
            case R.id.ll_index:
                if(fr_main == null) {
                    fr_main = new MainFragment();
                    transaction.add(R.id.fl_main, fr_main);
                } else {
                    transaction.show(fr_main);
                }
                ib_index.setImageResource(R.drawable.ic_home_fill);
                break;
        }
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if(fr_main != null) {
            transaction.hide(fr_main);
        }
        if(fr_mine != null) {
            transaction.hide(fr_mine);
        }
        if(fr_updatings != null) {
            transaction.hide(fr_updatings);
        }
        if(fr_service != null) {
            transaction.hide(fr_service);
        }
        if(fr_news != null) {
            transaction.hide(fr_news);
        }
    }

    /**
     * 初始化View
     */
    private void initView() {

        ll_updatings = findViewById(R.id.ll_updatings);
        ll_service = findViewById(R.id.ll_service);
        ll_index = findViewById(R.id.ll_index);
        ll_news = findViewById(R.id.ll_news);
        ll_mine = findViewById(R.id.ll_mine);

        ib_updatings = findViewById(R.id.ib_updations);
        ib_service = findViewById(R.id.ib_service);
        ib_index = findViewById(R.id.ib_index);
        ib_news = findViewById(R.id.ib_news);
        ib_mine = findViewById(R.id.ib_mine);
    }

    /**
     * View监听
     */
    private void initEvent() {
        ll_updatings.setOnClickListener(new onClick());
        ll_service.setOnClickListener(new onClick());
        ll_index.setOnClickListener(new onClick());
        ll_news.setOnClickListener(new onClick());
        ll_mine.setOnClickListener(new onClick());
    }

    /**
     * 检查获取权限
     */
    private void checkAndGetPermission() {
        if(!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(Constans.WRITE_EXTERNAL_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!hasPermission(Manifest.permission.CAMERA)) {
            requestPermission(Constans.CAMERA_CODE, Manifest.permission.CAMERA);
        }
        if(!hasPermission(Manifest.permission.SEND_SMS)) {
            requestPermission(Constans.SMS_CODE, Manifest.permission.SEND_SMS);
        }
        if(!hasPermission(Manifest.permission.CALL_PHONE)) {
            requestPermission(Constans.CALL_PHONE_CODE,Manifest.permission.CALL_PHONE);
        }
    }

    /**
     * ImageButton颜色置灰
     */
    private void resetImageButton() {
        ib_index.setImageResource(R.drawable.ic_home);
        ib_mine.setImageResource(R.drawable.ic_mine);
        ib_news.setImageResource(R.drawable.ic_news);
        ib_updatings.setImageResource(R.drawable.ic_updatings);
        ib_service.setImageResource(R.drawable.ic_server);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && down_flag == 0) {
            down_flag = 1;
            st_time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this,"再按一次返回键关闭程序",Toast.LENGTH_SHORT).show();
        } else if(keyCode == KeyEvent.KEYCODE_BACK && down_flag == 1) {
            long en_time = System.currentTimeMillis();
            if(en_time - st_time <= 2000) {
                finish();
            } else {
                Toast.makeText(MainActivity.this,"再按一次返回键关闭程序",Toast.LENGTH_SHORT).show();
            }
            st_time = System.currentTimeMillis();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
