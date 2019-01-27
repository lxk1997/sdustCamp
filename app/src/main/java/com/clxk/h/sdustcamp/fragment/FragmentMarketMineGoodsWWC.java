package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.MarketBuyAdapter;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.ui.MarketBuyGoodsDetails;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Chook_lxk
 *
 * 二手市场-我的-闲置管理-未完成
 */
public class FragmentMarketMineGoodsWWC extends Fragment implements AdapterView.OnItemClickListener {

    private View currentView;

    private ListView lv_market_mine_goods_wwc;
    private MarketBuyAdapter mMarketAdapter;
    private List<MarketGoods> source;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_market_mine_goods_wwc,container,false);

        initView();

        getMineGoods();

        initEvent();

        return  currentView;
    }

    /**
     * View事件
     */
    private void initEvent() {
        lv_market_mine_goods_wwc.setOnItemClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        lv_market_mine_goods_wwc = currentView.findViewById(R.id.lv_market_mine_wwc);
        source = new ArrayList<>();
    }

    /**
     * 数据库中获取我的Goods
     *
     */
    private void  getMineGoods() {

        BmobQuery<MarketGoods> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<MarketGoods>() {
            @Override
            public void done(List<MarketGoods> list, BmobException e) {
                if(e == null) {
                    for(MarketGoods mg : list) {
                        if(mg.getgStatue() == 1 && mg.getUserId().equals(BmobUser.getCurrentUser(User.class).getUsername())) {
                            source.add(mg);
                        }
                    }
                    mMarketAdapter = new MarketBuyAdapter(getContext(),R.layout.market_buy_item,source);
                    lv_market_mine_goods_wwc.setAdapter(mMarketAdapter);

                } else {
                    throw new IllegalArgumentException("网络连接失败！");
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MarketGoods tt = source.get(position);
        MyApplication.getInstance().marketGoods = tt;
        Intent intent = new Intent(getContext(), MarketBuyGoodsDetails.class);
        intent.putExtra("Goods", (Parcelable)tt);
        startActivity(intent);
        getActivity().onBackPressed();
    }
}
