package com.clxk.h.sdustcamp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.UpdatingsXXGGAdapter;
import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;
import com.clxk.h.sdustcamp.spider.GetXXGG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatingsXXGGFragment extends Fragment {

    private View currentView;
    private RecyclerView rv_updatings_xxgg;
    private EasyRefreshLayout erl_updatings_xxgg;
    private LinearLayoutManager linearLayoutManager;
    private List<UpdatingsXXGG>sources;
    private List<UpdatingsXXGG>cursources;
    private UpdatingsXXGGAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_updatings_xxgg,container,false);

        initView();
        initEvent();
        return currentView;
    }

    private void initEvent() {
        getXXGG();

        erl_updatings_xxgg.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int len = mAdapter.getData().size();
                        final List<UpdatingsXXGG> cur = new ArrayList<>();
                        for(int i = len; i < len+10 && i < sources.size(); i++) {
                            cur.add(sources.get(i));
                        }
                        erl_updatings_xxgg.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                mAdapter.getData().addAll(cur);
                                mAdapter.notifyDataSetChanged();
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
                        getXXGG();
                        erl_updatings_xxgg.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        rv_updatings_xxgg = currentView.findViewById(R.id.rv_updatings_xxgg);
        erl_updatings_xxgg = currentView.findViewById(R.id.erl_updatings_xxgg);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_updatings_xxgg.setLayoutManager(linearLayoutManager);

        sources = new ArrayList<>();
        cursources = new ArrayList<>();
    }

    private void getXXGG() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sources = GetXXGG.getXXGG();
                    Message msg = new Message();
                    msg.obj = sources;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cursources.clear();
            for(int i = 0; i < 10 && i < sources.size(); i++) {
                cursources.add(sources.get(i));
            }
            mAdapter = new UpdatingsXXGGAdapter(R.layout.updating_xxgg_item, cursources);
            rv_updatings_xxgg.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                }
            });
        }
    };
}
