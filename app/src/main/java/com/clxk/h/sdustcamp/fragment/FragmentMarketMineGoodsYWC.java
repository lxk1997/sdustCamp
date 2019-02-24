package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
 * 二手市场-我的-闲置管理-已完成
 */
public class FragmentMarketMineGoodsYWC extends Fragment implements AdapterView.OnItemClickListener {
    private View currentView;

    private RecyclerView rv_market;
    private LinearLayoutManager linearLayoutManager;
    private EasyRefreshLayout erl_market;
    private MarketBuyAdapter mMarketAdapter;
    private List<MarketGoods> source;
    private List<MarketGoods> cursource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_market_mine_goods_wwc,container,false);

        initView();

        initEvent();

        return  currentView;
    }

    /**
     * View事件
     */
    private void initEvent() {
        getMineGoods();
        erl_market.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int len = mMarketAdapter.getData().size();
                        final List<MarketGoods> cur = new ArrayList<>();
                        for(int i = len; i < len+10 && i < source.size(); i++) {
                            cur.add(source.get(i));
                        }

                        erl_market.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                mMarketAdapter.getData().addAll(cur);
                                mMarketAdapter.notifyDataSetChanged();
                            }
                        },500);
                    }
                },2000);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMineGoods();
                        erl_market.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    /**
     * View初始化
     */
    private void initView() {

        rv_market = currentView.findViewById(R.id.rv_market);
        erl_market = currentView.findViewById(R.id.erl_market);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_market.setLayoutManager(linearLayoutManager);
        source = new ArrayList<>();
        cursource = new ArrayList<>();
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
                    source.clear();
                    cursource.clear();
                    for(MarketGoods mg : list) {
                        if(mg.getgStatue() == 2 && mg.getUserId().equals(BmobUser.getCurrentUser(User.class).getUsername())) {
                            source.add(mg);
                        }
                    }
                    for(int i = 0; i < 10 && i < source.size(); i++) {
                        cursource.add(source.get(i));
                    }
                    mMarketAdapter = new MarketBuyAdapter(R.layout.market_buy_item,cursource);
                    rv_market.setAdapter(mMarketAdapter);
                    mMarketAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            MyApplication.getInstance().marketGoods = source.get(position);
                            Intent intent = new Intent(getContext(), MarketBuyGoodsDetails.class);
                            startActivity(intent);
                            getActivity().onBackPressed();
                        }
                    });
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
