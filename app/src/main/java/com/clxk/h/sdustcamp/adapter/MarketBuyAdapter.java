package com.clxk.h.sdustcamp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.MarketGoods;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.utils.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MarketBuyAdapter extends ArrayAdapter {

    private ViewHolder viewHolder = new ViewHolder();
    private MarketGoods goods = new MarketGoods();
    private User user = new User();
    public MarketBuyAdapter(Context context, int resource, List<MarketGoods> source) {
        super(context, resource, source);
        // TODO Auto-generated constructor stub
        SharedPreferences sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("loginbool",false)) {
            user = BmobUser.getCurrentUser(User.class);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        goods = (MarketGoods) getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.market_buy_item, null);
            viewHolder.iv_2marketUserPro = (RoundedImageView) convertView.findViewById(R.id.iv_2marketUserPro);
            viewHolder.iv_2marketImage1 = (ImageView) convertView.findViewById(R.id.iv_2marketImage1);
            viewHolder.iv_2marketImage2 = (ImageView) convertView.findViewById(R.id.iv_2marketImage2);
            viewHolder.iv_2marketImage3 = (ImageView) convertView.findViewById(R.id.iv_2marketImage3);
            viewHolder.tv_2marketMoney = (TextView)convertView.findViewById(R.id.tv_2marketMoney);
            viewHolder.tv_2marketUserName = (TextView)convertView.findViewById(R.id.tv_2marketUserName);
            viewHolder.tv_2marketUserTime = (TextView)convertView.findViewById(R.id.tv_2marketUserTime);
            viewHolder.tv_2marketRecomment = (TextView)convertView.findViewById(R.id.tv_2marketRecomment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Activity a = (Activity) viewHolder.iv_2marketImage1.getContext();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null) {
                    for(User u : list) {
                        if(u.getMobilePhoneNumber().trim().equals(goods.getUserId().trim())) {
                            user = u;
                            if(user.getAvatar() == null) {
                                viewHolder.iv_2marketUserPro.setImageResource(R.drawable.profile);
                            } else {
                                a.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(user.getAvatar() != null) {
                                            try {
                                                ImageLoader.getInstance().displayImage(viewHolder.iv_2marketUserPro, user.getAvatar().getFileUrl());
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        if(user.getNick() != null) {
                                            viewHolder.tv_2marketUserName.setText(user.getNick());
                                        } else {
                                            viewHolder.tv_2marketRecomment.setText(user.getMobilePhoneNumber());
                                        }
                                    }
                                });
                            }
                            break;
                        }
                    }
                }
            }
        });
        a.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(goods.getgImage1() != null) {
                        ImageLoader.getInstance().displayImage(viewHolder.iv_2marketImage1,goods.getgImage1().getFileUrl());
                    }
                    if(goods.getgImage2() != null) {
                        ImageLoader.getInstance().displayImage(viewHolder.iv_2marketImage2,goods.getgImage2().getFileUrl());
                    }
                    if(goods.getgImage3() != null) {
                        ImageLoader.getInstance().displayImage(viewHolder.iv_2marketImage3,goods.getgImage3().getFileUrl());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.i("manyq","qaqa");

        viewHolder.tv_2marketMoney.setText(goods.getgPrice()+"¥");
        viewHolder.tv_2marketUserTime.setText("刚刚来过");
        viewHolder.tv_2marketRecomment.setText(goods.getgDetail());
        return convertView;
    }

    public class ViewHolder {
        RoundedImageView iv_2marketUserPro;
        TextView tv_2marketUserName;
        TextView tv_2marketUserTime;
        TextView tv_2marketMoney;
        TextView tv_2marketRecomment;
        ImageView iv_2marketImage1;
        ImageView iv_2marketImage2;
        ImageView iv_2marketImage3;
        public ViewHolder() {
            super();
        }
    }

}
