package com.clxk.h.sdustcamp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.UpdatingsTabAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.List;

public class UpdatingsFragment extends Fragment {

    private View currentView;

    private ViewPager vp_updatings;
    private TabPageIndicator ti_updatings;
    private UpdatingsTabAdapter mTabAdapter;

    private UnderlinePageIndicator mUnderlinePageIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_updatings, container, false);

        initView();

        initEvent();
        return currentView;
    }

    /**
     * View监听
     */
    private void initEvent() {

        vp_updatings.setAdapter(mTabAdapter);
        ti_updatings.setViewPager(vp_updatings, 0);

        mUnderlinePageIndicator.setViewPager(vp_updatings);
        mUnderlinePageIndicator.setFades(false);
        ti_updatings.setOnPageChangeListener(mUnderlinePageIndicator);
    }

    private void initView() {

        vp_updatings = currentView.findViewById(R.id.vp_updatings);
        ti_updatings = currentView.findViewById(R.id.ti_updatings);
        mTabAdapter = new UpdatingsTabAdapter(getChildFragmentManager());

        mUnderlinePageIndicator = currentView.findViewById(R.id.underline_indicator);
    }
}
