package com.clxk.h.sdustcamp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;

/**
 * Created on 19/2/10
 * 动态-视频科大-条目
 */
public class UpdatingsSPKDActivity extends MyBaseActivity implements View.OnClickListener {

    private VideoView vv_updating_href;
    private TextView tv_updating_title;
    private TextView tv_updating_time;
    private MediaController mediaController;
    private UpdatingsSPKD updatingsSPKD;
    private ImageButton ib_back;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(0x7f0a006e);

        initView();
        initEvent();
    }

    private void initEvent() {
        ib_back.setOnClickListener(this);
    }

    private void initView() {

        vv_updating_href = findViewById(R.id.vv_updatings_href);
        tv_updating_time = findViewById(R.id.tv_updatings_time);
        tv_updating_title = findViewById(R.id.tv_updatings_title);
        mediaController = new MediaController(this);
        ib_back = findViewById(R.id.ib_market_header_back);

        updatingsSPKD = MyApplication.getInstance().updatingsSPKD;

        tv_updating_title.setText(updatingsSPKD.getTitle());
        tv_updating_time.setText(updatingsSPKD.getTime());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_updatings);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_updatings);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
