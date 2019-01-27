package com.clxk.h.sdustcamp.operator;


import android.content.Context;
import android.util.Log;

import com.clxk.h.sdustcamp.bean.MarketGoods;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobOperatorMarketGoods {
    private static Context context;
    private MarketGoods marketGoods;

    public BmobOperatorMarketGoods() {
        this.marketGoods = new MarketGoods();
    }
    public void add(Context context, MarketGoods marketGoods) {
        this.context = context;
        this.marketGoods = marketGoods;
        marketGoods.save(new SaveListener<String>() {

            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("数据插入成功", "1111");
                } else {
                    Log.i("数据插入失败", "111");
                }
            }
        });
    }

    public void delete(Context context, MarketGoods marketGoods) {
        this.context = context;
        this.marketGoods = marketGoods;
        marketGoods.delete(new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("数据删除成功","22222");
                } else {
                    Log.i("数据删除失败","22222");
                }
            }
        });
    }

    public void update(Context context, MarketGoods marketGoods,String id) {
        this.context = context;
        this.marketGoods = marketGoods;
        marketGoods.update(id,new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("qwer","数据更新成功");
                } else {
                    Log.i("qwer","数据更新失败");
                    Log.i("qwer","数据更新失败");
                }
            }
        });
    }

}

