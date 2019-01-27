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

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.UpdatingsAdapter;
import com.clxk.h.sdustcamp.bean.Updatings;
import com.clxk.h.sdustcamp.spider.Crawler;
import com.clxk.h.sdustcamp.ui.UpdatingsKDYW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UpdatingsKDYWFragment extends Fragment {

    private View currentView;

    private Crawler crawler;

    private UpdatingsAdapter myAdapter;
    private ArrayList<Updatings> source;
    private ListView lv_updatings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView =  inflater.inflate(R.layout.fragment_updatings_kdyw,container,false);

        initView();

        initEvent();

        crawler.GetUpdatingKDYW(getContext());

        getKDXW();

        return currentView;
    }

    /**
     * View事件
     */
    private void initEvent() {

        lv_updatings.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Updatings tt = source.get(position);
                Intent intent = new Intent(getActivity(), UpdatingsKDYW.class);
                intent.putExtra("Updatings", (Parcelable)tt);
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });

    }

    /**
     * View初始化
     */
    private void initView() {

        lv_updatings = currentView.findViewById(R.id.lv_updatings_kdyw);

        crawler = new Crawler();
        source = new ArrayList<Updatings>();

    }

    /**
     * 返回Bmob科大新闻信息到source
     */
    private void getKDXW() {
        BmobQuery<Updatings> bmobQuery = new BmobQuery<Updatings>();
        bmobQuery.findObjects(new FindListener<Updatings>() {

            private List<Updatings> object;
            private BmobException arg1;

            @Override
            public void done(List<Updatings> object, BmobException arg1) {
                this.object = object;
                this.arg1 = arg1;
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("查询成功：共" + object.size() + "条数据。","123");
                    for(Updatings t: object) {
                        source.add(t);
                        Log.i("111","1234");
                    }
                    Log.i("qyqyqyq",source.size() + "");
                    if(source.size() == 0) {
                        Toast.makeText(getContext(), "网络错误，请设置网络后再试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Collections.sort(source,new Comparator<Updatings>() {
                        @Override
                        public int compare(Updatings o1, Updatings o2) {
                            return o2.getTime().compareTo(o1.getTime());
                        }
                    });
                    myAdapter = new UpdatingsAdapter(getContext(), R.layout.updating_item, source);
                    lv_updatings.setAdapter(myAdapter);
                } else {
                    Toast.makeText(getContext(), "网络错误，请设置网络后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
