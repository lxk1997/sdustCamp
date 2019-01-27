package com.clxk.h.sdustcamp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.fragment.MarketIndexFragment;
import com.clxk.h.sdustcamp.fragment.MarketMarketFragment;
import com.clxk.h.sdustcamp.fragment.MarketMineFragment;
import com.clxk.h.sdustcamp.fragment.MarketNewsFragment;

public class MarketActivity extends MyBaseActivity {

    private LinearLayout ll_market_index;
    private LinearLayout ll_market_market;
    private LinearLayout ll_market_news;
    private LinearLayout ll_market_mine;

    private ImageButton ib_market_index;
    private ImageButton ib_market_market;
    private ImageButton ib_market_news;
    private ImageButton ib_market_mine;

    private Fragment fr_index;
    private Fragment fr_mine;
    private Fragment fr_news;
    private Fragment fr_market;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2market);

        initView();

        initEvent();

        setSelect(getIntent().getIntExtra("frId",R.id.ll_market_index));

    }

    /**
     * View监听
     */
    private void initEvent() {

        ll_market_mine.setOnClickListener(new onClick());
        ll_market_index.setOnClickListener(new onClick());
        ll_market_market.setOnClickListener(new onClick());
        ll_market_news.setOnClickListener(new onClick());

    }

    /**
     * View初始化
     */
    private void initView() {

        ll_market_index = findViewById(R.id.ll_market_index);
        ll_market_market = findViewById(R.id.ll_market_market);
        ll_market_mine = findViewById(R.id.ll_market_mine);
        ll_market_news = findViewById(R.id.ll_market_news);

        ib_market_index = findViewById(R.id.ib_market_index);
        ib_market_market = findViewById(R.id.ib_market_market);
        ib_market_news = findViewById(R.id.ib_market_news);
        ib_market_mine = findViewById(R.id.ib_market_mine);
    }

    class onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            setSelect(v.getId());
        }
    }

    /**
     * 把所有Tab置灰
     */
    private void resertImageButton() {

        ib_market_market.setImageResource(R.drawable.ic_market);
        ib_market_index.setImageResource(R.drawable.ic_home);
        ib_market_news.setImageResource(R.drawable.ic_news);
        ib_market_mine.setImageResource(R.drawable.ic_mine);
    }

    /**
     * 选择Fragment
     * @param u
     */
    private void setSelect(int u) {

        resertImageButton();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction mTrans = fm.beginTransaction();
        hideFragment(mTrans);
        switch (u) {

            case R.id.ll_market_index:
                if(fr_index == null) {
                    fr_index = new MarketIndexFragment();
                    mTrans.add(R.id.fl_market,fr_index);
                } else {
                    mTrans.show(fr_index);
                }
                ib_market_index.setImageResource(R.drawable.ic_home_fill);
                break;
            case R.id.ll_market_market:
                if(fr_market == null) {
                    fr_market = new MarketMarketFragment();
                    mTrans.add(R.id.fl_market,fr_market);
                } else {
                    mTrans.show(fr_market);
                }
                ib_market_market.setImageResource(R.drawable.ic_market_fill);
                break;
            case R.id.ll_market_news:
                if(fr_news == null) {
                    fr_news = new MarketNewsFragment();
                    mTrans.add(R.id.fl_market,fr_news);
                } else {
                    mTrans.show(fr_news);
                }
                ib_market_news.setImageResource(R.drawable.ic_news_fill);
                break;
            case R.id.ll_market_mine:
                if(fr_mine == null) {
                    fr_mine = new MarketMineFragment();
                    mTrans.add(R.id.fl_market,fr_mine);
                } else {
                    mTrans.show(fr_mine);
                }
                ib_market_mine.setImageResource(R.drawable.ic_mine_fill);
                break;
        }
        mTrans.commit();
    }

    private void hideFragment(FragmentTransaction mTrans) {

        if(fr_market != null) {
            mTrans.hide(fr_market);
        }
        if(fr_index != null) {
            mTrans.hide(fr_index);
        }
        if(fr_mine != null) {
            mTrans.hide(fr_mine);
        }
        if(fr_news != null) {
            mTrans.hide(fr_news);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MarketActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
