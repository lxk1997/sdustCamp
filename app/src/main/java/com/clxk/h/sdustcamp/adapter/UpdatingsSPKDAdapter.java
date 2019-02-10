package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.UpdatingsSPKD;

import java.util.List;

/**
 * Created on 19/2/10
 * 动态-视频科大-适配器
 */
public class UpdatingsSPKDAdapter extends BaseQuickAdapter<UpdatingsSPKD, BaseViewHolder> {

    public UpdatingsSPKDAdapter(int layoutResId, @Nullable List<UpdatingsSPKD> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpdatingsSPKD item) {
        helper.setText(R.id.tv_updatings_title,item.getTitle());
        helper.setText(R.id.tv_updatings_time, item.getTime());
        Glide.with(helper.itemView.getContext()).load(item.getImage()).into((ImageView) helper.getView(R.id.iv_updatings_image));
    }
}
