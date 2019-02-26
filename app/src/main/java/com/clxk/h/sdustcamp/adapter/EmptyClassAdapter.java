package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.EmptyClass;

import java.util.List;

public class EmptyClassAdapter extends BaseQuickAdapter<EmptyClass, BaseViewHolder> {

    public EmptyClassAdapter(int layoutResId, @Nullable List<EmptyClass> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, EmptyClass item) {
        helper.setText(R.id.tv_class_name, item.getName());
        helper.setText(R.id.tv_class_number, item.getCapacity());
    }

    protected int getItemLayoutId() {
        return R.layout.item_class;
    }
}
