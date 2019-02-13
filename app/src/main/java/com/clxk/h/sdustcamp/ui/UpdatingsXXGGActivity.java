package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;

import butterknife.BindView;

/**
 * Created on 19/2/12
 * 动态-学校公告
 */
public class UpdatingsXXGGActivity extends MyBaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_updatings_title)
    private TextView tv_updatings_title;
    @BindView(R.id.tv_updatings_time)
    private TextView tv_updatings_time;
    @BindView(R.id.tv_updatings_content)
    private TextView tv_updatings_content;
    @BindView(R.id.ib_market_header_back)
    private ImageButton ib_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatings_xxgg);

        initView();

        initEvent();

    }

    private void initEvent() {
        ib_back.setOnClickListener(this);
    }

    private void initView() {

        UpdatingsXXGG xxgg = MyApplication.getInstance().updatingsXXGG;
        tv_updatings_title.setText(xxgg.getTitle());
        tv_updatings_time.setText(xxgg.getTime());
        tv_updatings_content.setText(xxgg.getContent());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ib_market_header_back:
                Intent intent = new Intent(UpdatingsXXGGActivity.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_updatings);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UpdatingsXXGGActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_updatings);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
