package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.MarketInTabAdapter;
import com.clxk.h.sdustcamp.ui.MainActivity;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

/**
 * Created by Chook_lxk
 *
 * 二手市场-市场
 */
public class MarketMarketFragment extends Fragment implements View.OnClickListener {

    private View currentView;

    private TabPageIndicator ti_market_market;
    private UnderlinePageIndicator upi_market_market;
    private ViewPager vp_market_market;
    private MarketInTabAdapter myTabAdapter;

    private ImageButton ib_market_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_market_market,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * View监听
     */
    private void initEvent() {

        vp_market_market.setAdapter(myTabAdapter);
        ti_market_market.setViewPager(vp_market_market,0);

        upi_market_market.setViewPager(vp_market_market);
        upi_market_market.setFades(false);
        ti_market_market.setOnPageChangeListener(upi_market_market);

        ib_market_back.setOnClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        ti_market_market = currentView.findViewById(R.id.ti_market_market);
        upi_market_market = currentView.findViewById(R.id.upi_market_market);
        vp_market_market = currentView.findViewById(R.id.vp_market_market);

        myTabAdapter = new MarketInTabAdapter(getChildFragmentManager());

        ib_market_back = currentView.findViewById(R.id.ib_market_back);
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.ib_market_back:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
        }
    }
}
