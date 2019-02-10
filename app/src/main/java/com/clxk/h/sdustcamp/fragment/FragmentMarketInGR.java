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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.MarketBuyAdapter;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.ui.MarketBuyGoodsDetails;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Chook_lxk
 *
 * 二手市场-市场-我要购入Fragment
 */
public class FragmentMarketInGR extends Fragment {

    private View currentView;

    private RecyclerView rv_market_market_gr;
    private EasyRefreshLayout erl_market_market_gr;
    private LinearLayoutManager linearLayoutManager;
    private MarketBuyAdapter myAdapter;
    private ArrayList<MarketGoods> source;
    private ArrayList<MarketGoods> cursources;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_market_market_gr,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * View事件
     */
    private void initEvent() {

        getSource();

        erl_market_market_gr.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int len = myAdapter.getData().size();
                        final List<MarketGoods> cur = new ArrayList<>();
                        for(int i = len; i < len+10 && i < source.size(); i++) {
                            cur.add(source.get(i));
                        }

                        erl_market_market_gr.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                myAdapter.getData().addAll(cur);
                                myAdapter.notifyDataSetChanged();
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
                        getSource();
                        erl_market_market_gr.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    /**
     * View初始化
     */
    private void initView() {

        rv_market_market_gr = currentView.findViewById(R.id.rv_market_market_gr);
        erl_market_market_gr = currentView.findViewById(R.id.erl_market_market_gr);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_market_market_gr.setLayoutManager(linearLayoutManager);

        source = new ArrayList<>();
        cursources = new ArrayList<>();
    }

    /**
     * 数据库中获取Source
     */
    private void getSource() {
        BmobQuery<MarketGoods> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<MarketGoods>() {
            @Override
            public void done(List<MarketGoods> list, BmobException e) {
                if(e == null) {
                    source.clear();
                    for(MarketGoods mg : list) {
                        source.add(mg);
                    }
                    cursources.clear();
                    for(int i = 0; i < 10 && i < source.size(); i++) {
                        cursources.add(source.get(i));
                    }
                    myAdapter = new MarketBuyAdapter(R.layout.market_buy_item,cursources);
                    rv_market_market_gr.setAdapter(myAdapter);
                    myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            MyApplication.getInstance().marketGoods = source.get(position);
                            Intent intent = new Intent(getContext(), MarketBuyGoodsDetails.class);
                            startActivity(intent);
                            getActivity().onBackPressed();
                        }
                    });
                } else {
                    Toast.makeText(getContext(),"未知错误404！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
