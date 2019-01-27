package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.clxk.h.sdustcamp.fragment.FragmentMarketInGR;
import com.clxk.h.sdustcamp.fragment.FragmentMarketInXZ;

/**
 * Created by Chook_lxk
 *
 * 二手市场-市场Fragment适配器
 */
public class MarketInTabAdapter extends FragmentPagerAdapter {

    public static String[] TITLE_IN_MARKETIN = {"我要购入","我有闲置"};

    public MarketInTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0) {
            FragmentMarketInGR fragmentMarketInGR = new FragmentMarketInGR();
            return fragmentMarketInGR;
        } else if(i == 1) {
            FragmentMarketInXZ fragmentMarketInXZ = new FragmentMarketInXZ();
            return fragmentMarketInXZ;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLE_IN_MARKETIN.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE_IN_MARKETIN[position];
    }
}
