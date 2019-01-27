package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    private ListView lv_market_market_gr;
    private MarketBuyAdapter myAdapter;
    private ArrayList<MarketGoods> source;

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

        lv_market_market_gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MarketGoods tt = source.get(position);
                MyApplication.getInstance().marketGoods = tt;
                Intent intent = new Intent(getContext(), MarketBuyGoodsDetails.class);
                intent.putExtra("Goods", (Parcelable)tt);
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });
    }

    /**
     * View初始化
     */
    private void initView() {

        lv_market_market_gr = currentView.findViewById(R.id.lv_market_market_gr);

        source = new ArrayList<>();
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
                    Log.i("manyq",list.size()+"");
                    for(MarketGoods mg : list) {
                        source.add(mg);
                        Log.i("manyq",mg.getgName());
                    }
                    myAdapter = new MarketBuyAdapter(getContext(),R.layout.market_buy_item,source);
                    lv_market_market_gr.setAdapter(myAdapter);
                } else {
                    Toast.makeText(getContext(),"未知错误404！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
