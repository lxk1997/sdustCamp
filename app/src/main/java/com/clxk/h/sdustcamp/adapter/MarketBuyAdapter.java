package com.clxk.h.sdustcamp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created on 19/2/8
 * 二手市场-市场-我要购入适配器
 */
public class MarketBuyAdapter extends BaseQuickAdapter<MarketGoods, BaseViewHolder> {

    private User user;

    public MarketBuyAdapter(int layoutResId, @Nullable List<MarketGoods> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final MarketGoods item) {

        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    for (User u : list) {
                        if (u.getMobilePhoneNumber().trim().equals(item.getUserId().trim())) {
                            user = u;
                            setItem(user);
                            break;
                        }
                    }
                }
            }
            private void setItem(User user) {
                if (user.getAvatar() == null) {
                    Glide.with(helper.itemView.getContext()).load(R.drawable.profile).into((RoundedImageView) helper.getView(R.id.iv_2marketUserPro));
                } else {
                    if (user.getAvatar() != null) {
                        Glide.with(helper.itemView.getContext()).load(user.getAvatar().getFileUrl()).into((RoundedImageView) helper.getView(R.id.iv_2marketUserPro));
                    }
                    if (user.getNick() != null) {
                        helper.setText(R.id.tv_2marketUserName,user.getNick());
                    } else {
                        helper.setText(R.id.tv_2marketUserName,user.getMobilePhoneNumber());
                    }
                }
                if (item.getgImage1() != null) {
                    Glide.with(helper.itemView.getContext()).load(item.getgImage1()).into((ImageView)helper.getView(R.id.iv_2marketImage1));
                }
                if (item.getgImage2() != null) {
                    Glide.with(helper.itemView.getContext()).load(item.getgImage2()).into((ImageView)helper.getView(R.id.iv_2marketImage2));
                }
                if (item.getgImage3() != null) {
                    Glide.with(helper.itemView.getContext()).load(item.getgImage3()).into((ImageView)helper.getView(R.id.iv_2marketImage3));
                }
                helper.setText(R.id.tv_2marketMoney,item.getgPrice() + "¥");
                helper.setText(R.id.tv_2marketUserTime,"刚刚来过");
                helper.setText(R.id.tv_2marketRecomment,item.getgDetail());
            }
        });
    }
}
