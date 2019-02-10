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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.UpdatingsSPKDAdapter;
import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;
import com.clxk.h.sdustcamp.spider.GetSPKD;
import com.clxk.h.sdustcamp.ui.UpdatingsSPKDActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdatingsSPKDFragment extends Fragment {

    private View currentView;

    private RecyclerView rv_updatings_spkd;
    private EasyRefreshLayout erl_updatings_spkd;
    private LinearLayoutManager linearLayoutManager;
    private List<UpdatingsSPKD> sources;
    private List<UpdatingsSPKD> cursources;
    private UpdatingsSPKDAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_updatings_spkd,container,false);

        initView();

        initEvent();

        return currentView;
    }

    private void initEvent() {
        getSPKD();

        erl_updatings_spkd.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int len = mAdapter.getData().size();
                        final List<UpdatingsSPKD> cur = new ArrayList<>();
                        for(int i = len; i < len+10 && i < sources.size(); i++) {
                            cur.add(sources.get(i));
                        }

                        erl_updatings_spkd.loadMoreComplete(new EasyRefreshLayout.Event() {
                            @Override
                            public void complete() {
                                mAdapter.getData().addAll(cur);
                                mAdapter.notifyDataSetChanged();
                            }
                        },500);
                    }
                }, 2000);
            }

            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSPKD();
                        erl_updatings_spkd.refreshComplete();
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        sources = new ArrayList<>();
        cursources = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_updatings_spkd = currentView.findViewById(R.id.rv_updatings_spkd);
        erl_updatings_spkd = currentView.findViewById(R.id.erl_updatings_spkd);
        rv_updatings_spkd.setLayoutManager(linearLayoutManager);
    }

    private void getSPKD() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sources = GetSPKD.getSPKD();
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
            mAdapter = new UpdatingsSPKDAdapter(R.layout.updating_spkd_item, cursources);
            rv_updatings_spkd.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    MyApplication.getInstance().updatingsSPKD = sources.get(position);
                    Intent intent = new Intent(getActivity(), UpdatingsSPKDActivity.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                }
            });
        }
    };
}
