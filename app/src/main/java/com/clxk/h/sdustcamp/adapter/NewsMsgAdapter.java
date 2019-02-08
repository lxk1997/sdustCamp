package com.clxk.h.sdustcamp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clxk.h.sdustcamp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * 消息-适配器
 */
public class NewsMsgAdapter extends ArrayAdapter {

    private ViewHolder viewHolder;
    private List<BmobIMMessage> source;
    public static int index = -1;
    public NewsMsgAdapter(Context context, int resource, List<BmobIMMessage> objects) {
        super(context, resource, objects);
        source = objects;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BmobIMMessage msg = (BmobIMMessage) getItem(position);
        viewHolder = new ViewHolder();

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news_msg,null);

            viewHolder.riv_news_msg_avator = convertView.findViewById(R.id.riv_news_msg_avator);
            viewHolder.tv_news_msg_title = convertView.findViewById(R.id.tv_news_msg_title);
            viewHolder.tv_news_msg_content = convertView.findViewById(R.id.tv_news_msg_content);
            viewHolder.tv_news_msg_date = convertView.findViewById(R.id.tv_news_msg_date);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final BmobIMUserInfo userInfo = msg.getBmobIMUserInfo();
        viewHolder.tv_news_msg_title.setText(userInfo.getName());
        viewHolder.tv_news_msg_content.setText(msg.getContent());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.tv_news_msg_date.setText(format.format(msg.getUpdateTime()));
        Log.i("NewsgAdapter",userInfo.getAvatar());
        Log.i("NewsgAdapter","aaa");
        final Activity a = (Activity) viewHolder.riv_news_msg_avator.getContext();
        if(userInfo.getAvatar() != null) {
            Glide.with(viewHolder.riv_news_msg_avator.getContext()).load(userInfo.getAvatar()).into(viewHolder.riv_news_msg_avator);
        }
        return convertView;
    }

    class ViewHolder {

        TextView tv_news_msg_title;
        TextView tv_news_msg_content;
        TextView tv_news_msg_date;
        RoundedImageView riv_news_msg_avator;

        public ViewHolder() {
            super();
        }
    }

    public int findMsg(String index) {
        BmobIMMessage msg;
        for(int i = 0; i < source.size();i++) {
            msg = source.get(i);
            if(msg.getConversationId().equals(index)) {
                return i;
            }
        }
        return -1;
    }

}
