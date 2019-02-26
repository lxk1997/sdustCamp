package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.EmptyClassAdapter;
import com.clxk.h.sdustcamp.bean.EmptyClass;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.spider.GetEmptyClass;
import com.othershe.groupindexlib.decoration.DivideItemDecoration;
import com.othershe.groupindexlib.decoration.GroupHeaderItemDecoration;
import com.othershe.groupindexlib.helper.SortHelper;
import com.othershe.groupindexlib.listener.OnSideBarTouchListener;
import com.othershe.groupindexlib.weiget.SideBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmptyClassDetails extends MyBaseActivity implements View.OnClickListener {

    private RecyclerView rv_classlist;
    private ImageButton ib_back;
    private SideBar side_bar;
    private TextView tip;
    private SortHelper<EmptyClass> sortHelper;
    private List<String> tags;
    private EmptyClassAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String indexarr[] = {"0","1","3","11","13","14","15","S","T","Y"};

    private List<com.clxk.h.sdustcamp.bean.EmptyClass> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        initView();
        initEvent();
    }

    private void initEvent() {
        ib_back.setOnClickListener(this);
    }

    private void initView() {
        rv_classlist = findViewById(R.id.rv_classlist);
        side_bar = findViewById(R.id.side_bar);
        tip = findViewById(R.id.tip);
        ib_back = findViewById(R.id.ib_market_header_back);
        side_bar.setIndexsArray(indexarr);

        datas = new ArrayList<>();
        System.out.println("开始查找");
        getEmptyclass();


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewManager();
        }
    };

    private void getEmptyclass() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String data = getIntent().getStringExtra("data");
                    String time = getIntent().getStringExtra("time");
                    datas = GetEmptyClass.getEmptyClass(data,time);
                    System.out.println("查找完成"+datas.size());
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void viewManager() {
        sortHelper = new SortHelper<EmptyClass>() {
            @Override
            public String sortField(EmptyClass data) {
                return data.getBuildingno();
            }
        };
        sortHelper.sortByLetter(datas);
        tags = sortHelper.getTags(datas);
        adapter = new EmptyClassAdapter(R.layout.item_class,datas);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_classlist.setLayoutManager(layoutManager);

        rv_classlist.addItemDecoration(new GroupHeaderItemDecoration(this)
                .setTags(tags)
                .setGroupHeaderHeight(30)
                .setGroupHeaderLeftPadding(20)
        );
        rv_classlist.addItemDecoration(new DivideItemDecoration().setTags(tags));
        rv_classlist.setAdapter(adapter);
        side_bar.setOnSideBarTouchListener(tags, new OnSideBarTouchListener() {
            @Override
            public void onTouch(String s, int i) {
                tip.setVisibility(View.VISIBLE);
                tip.setText(s);
                if("↑".equals(s)) {
                    layoutManager.scrollToPositionWithOffset(0,0);
                    return;
                }
                if(i != -1) {
                    layoutManager.scrollToPositionWithOffset(i,0);
                }
            }

            @Override
            public void onTouchEnd() {
                tip.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                intent = new Intent(EmptyClassDetails.this, com.clxk.h.sdustcamp.ui.EmptyClass.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(EmptyClassDetails.this, com.clxk.h.sdustcamp.ui.EmptyClass.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
