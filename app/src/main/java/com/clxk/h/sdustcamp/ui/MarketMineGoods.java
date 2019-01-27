package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.MarketMineGoodsTabAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

public class MarketMineGoods extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_market_mine_goods_back;
    private ImageButton ib_market_mine_goods_add;

    private TabPageIndicator ti_market_mine_goods;
    private UnderlinePageIndicator upi_market_mine_goods;
    private ViewPager vp_market_mine_goods;
    private MarketMineGoodsTabAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_mine_unused);

        initView();

        initEvent();

    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_market_mine_goods_back.setOnClickListener(this);
        ib_market_mine_goods_add.setOnClickListener(this);

        vp_market_mine_goods.setAdapter(mTabAdapter);
        ti_market_mine_goods.setViewPager(vp_market_mine_goods,0);

        upi_market_mine_goods.setViewPager(vp_market_mine_goods);
        upi_market_mine_goods.setFades(false);
        ti_market_mine_goods.setOnPageChangeListener(upi_market_mine_goods);
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_market_mine_goods_add = findViewById(R.id.ib_market_mine_goods_add);
        ib_market_mine_goods_back = findViewById(R.id.ib_market_mine_goods_back);

        ti_market_mine_goods = findViewById(R.id.ti_market_mine_goods);
        vp_market_mine_goods = findViewById(R.id.vp_market_mine_goods);
        upi_market_mine_goods = findViewById(R.id.upi_market_mine_goods);

        mTabAdapter = new MarketMineGoodsTabAdapter(getSupportFragmentManager());
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.ib_market_mine_goods_add:
                intent = new Intent(MarketMineGoods.this, MarketGoodsAdd.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ib_market_mine_goods_back:
                intent = new Intent(MarketMineGoods.this, MarketActivity.class);
                intent.putExtra("frId",R.id.ll_market_mine);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MarketMineGoods.this, MarketActivity.class);
            intent.putExtra("frId",R.id.ll_market_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
