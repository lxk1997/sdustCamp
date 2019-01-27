package com.clxk.h.sdustcamp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.Updatings;

public class UpdatingsAdapter extends ArrayAdapter {

    public UpdatingsAdapter(Context context, int resource, List<Updatings> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Updatings updating = (Updatings)getItem(position);
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.updating_item, null);
            viewHolder.tv_updatingsItemTitle = convertView.findViewById(R.id.tv_updatingsItemTitle);
            viewHolder.tv_updatingsItemContext = convertView.findViewById(R.id.tv_updatingsItemContext);
            viewHolder.tv_updatingsItemRate = convertView.findViewById(R.id.tv_updatingsItemRate);
            viewHolder.tv_updatingsItemTime = convertView.findViewById(R.id.tv_updatingsItemTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tv_updatingsItemTitle.setText(updating.getTitle().toString());
        viewHolder.tv_updatingsItemContext.setText(updating.getContext().toString());
        viewHolder.tv_updatingsItemRate.setText(updating.getRate().toString() + " 点击量");
        viewHolder.tv_updatingsItemTime.setText(updating.getTime().toString());
        return convertView;
    }

    public class ViewHolder {
        TextView tv_updatingsItemTitle;
        TextView tv_updatingsItemContext;
        TextView tv_updatingsItemRate;
        TextView tv_updatingsItemTime;

        public ViewHolder() {
            super();
        }
    }

}
