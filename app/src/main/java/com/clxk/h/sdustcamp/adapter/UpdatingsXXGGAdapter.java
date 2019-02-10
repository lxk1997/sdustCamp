package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.UpdatingsXXGG;

import java.util.List;

/**
 * Created on 19/2/10
 * 动态-学校公告适配器
 */
public class UpdatingsXXGGAdapter extends BaseQuickAdapter<UpdatingsXXGG, BaseViewHolder> {

    public UpdatingsXXGGAdapter(int layoutResId, @Nullable List<UpdatingsXXGG> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpdatingsXXGG item) {
        helper.setText(R.id.tv_updatings_title,item.getTitle());
        helper.setText(R.id.tv_updatings_time,item.getTime());
    }
}
