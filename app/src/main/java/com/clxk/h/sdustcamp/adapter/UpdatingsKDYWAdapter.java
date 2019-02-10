package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;

import java.util.List;

/**
 * Created on 19/2/3
 * 动态-科大要问适配器
 */
public class UpdatingsKDYWAdapter extends BaseQuickAdapter<UpdatingsKDYW, BaseViewHolder> {


    public UpdatingsKDYWAdapter(int layoutResId, @Nullable List<UpdatingsKDYW> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UpdatingsKDYW item) {
        viewHolder.setText(R.id.tv_updatingsItemTitle, item.getTitle());
        viewHolder.setText(R.id.tv_updatingsItemTime,item.getTime());
        if(item.getImage() != null) {
            Glide.with(viewHolder.itemView.getContext()).load(item.getImage()).into((ImageButton) viewHolder.getView(R.id.ib_updatingsItemImage));
        } else {
            Glide.with(viewHolder.itemView.getContext()).load(R.drawable.kdywgeneral).into((ImageButton) viewHolder.getView(R.id.ib_updatingsItemImage));
        }
    }
}
