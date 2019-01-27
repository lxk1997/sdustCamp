package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.clxk.h.sdustcamp.fragment.FragmentMarketMineGoodsWWC;
import com.clxk.h.sdustcamp.fragment.FragmentMarketMineGoodsYQX;
import com.clxk.h.sdustcamp.fragment.FragmentMarketMineGoodsYWC;

/**
 * Created by Chook_lxk
 *
 * 二手市场-我的-闲置管理Tab适配器
 */
public class MarketMineGoodsTabAdapter extends FragmentPagerAdapter {

    private String[] TITLE_IN_MARKET_MINE_GOODS = {"已完成","未完成","已取消"};

    public MarketMineGoodsTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                FragmentMarketMineGoodsYWC fragmentMarketMineGoodsYWC = new FragmentMarketMineGoodsYWC();
                return fragmentMarketMineGoodsYWC;
            case 1:
                FragmentMarketMineGoodsWWC fragmentMarketMineGoodsWWC = new FragmentMarketMineGoodsWWC();
                return fragmentMarketMineGoodsWWC;
            case 2:
                FragmentMarketMineGoodsYQX fragmentMarketMineGoodsYQX = new FragmentMarketMineGoodsYQX();
                return fragmentMarketMineGoodsYQX;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLE_IN_MARKET_MINE_GOODS.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE_IN_MARKET_MINE_GOODS[position];
    }
}
