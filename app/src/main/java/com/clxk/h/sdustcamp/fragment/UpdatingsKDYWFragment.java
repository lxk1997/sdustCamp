package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.UpdatingsKDYWAdapter;

import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;
import com.clxk.h.sdustcamp.operator.KDYWOperator;
import com.clxk.h.sdustcamp.spider.GetKDXW;
import com.clxk.h.sdustcamp.ui.UpdatingsKDYWActivity;
import com.clxk.h.sdustcamp.utils.SpaceItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatingsKDYWFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {

    private View currentView;


    private ArrayList<UpdatingsKDYW> source;
    private RecyclerView rv_updatings;

    private LinearLayoutManager linearLayoutManager;
    private UpdatingsKDYWAdapter kdywAdapter;
    private EasyRefreshLayout erl_updatings_kdyw;
    private ArrayList<UpdatingsKDYW> curSources;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_updatings_kdyw, container, false);

        initView();

        initEvent();

        getKDXW();


        return currentView;
    }

    private void getKDXW() {
        KDYWOperator kdywOperator = new KDYWOperator(getContext());

        source = kdywOperator.queryAll();
        curSources.clear();
        for (int i = 0; i < 10 && i < source.size(); i++) {
            curSources.add(source.get(i));
        }
        kdywAdapter = new UpdatingsKDYWAdapter(R.layout.updating_kdyw_item, curSources);
        rv_updatings.setAdapter(kdywAdapter);
        kdywAdapter.setOnItemClickListener(this);

    }


    /**
     * View事件
     */
    private void initEvent() {

        erl_updatings_kdyw.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final int len = kdywAdapter.getData().size();
                        final List<UpdatingsKDYW> cur = new ArrayList<>();
                        for (int i = len; i < len + 10 && i < source.size(); i++) {
                            cur.add(source.get(i));
                        }
                        erl_updatings_kdyw.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                kdywAdapter.getData().addAll(cur);
                                kdywAdapter.notifyDataSetChanged();
                            }
                        }, 500);
                    }
                }, 2000);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getKDXW();
                        erl_updatings_kdyw.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    /**
     * View初始化
     */
    private void initView() {

        rv_updatings = currentView.findViewById(R.id.rv_updatings_kdyw);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_updatings.setLayoutManager(linearLayoutManager);
        rv_updatings.addItemDecoration(new SpaceItemDecoration(30));
        erl_updatings_kdyw = currentView.findViewById(R.id.erl_updatings_kdyw);

        curSources = new ArrayList<>();
        source = new ArrayList<>();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.i("qqq", "dasdass");
        MyApplication.getInstance().updatings = source.get(position);
        Intent intent = new Intent(getActivity(), UpdatingsKDYWActivity.class);
        startActivity(intent);
        getActivity().onBackPressed();
    }
}
