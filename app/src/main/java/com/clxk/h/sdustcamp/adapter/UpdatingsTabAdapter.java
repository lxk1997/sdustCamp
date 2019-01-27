package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.clxk.h.sdustcamp.fragment.UpdatingsKDYWFragment;
import com.clxk.h.sdustcamp.fragment.UpdatingsSPKDFragment;
import com.clxk.h.sdustcamp.fragment.UpdatingsXXGGFragment;

public class UpdatingsTabAdapter extends FragmentPagerAdapter {

    public static String[] TITLES_IN_UPDATINGS = {"科大要闻","视频科大","学校公告"};

    public UpdatingsTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                UpdatingsKDYWFragment fr_kdyw = new UpdatingsKDYWFragment();
                return fr_kdyw;
            case 1:
                UpdatingsSPKDFragment fr_spkd = new UpdatingsSPKDFragment();
                return fr_spkd;
            case 2:
                UpdatingsXXGGFragment fr_xxgg = new UpdatingsXXGGFragment();
                return fr_xxgg;
        }
        return null;
    }


    @Override
    public int getCount() {
        return TITLES_IN_UPDATINGS.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES_IN_UPDATINGS[position];
    }
}
