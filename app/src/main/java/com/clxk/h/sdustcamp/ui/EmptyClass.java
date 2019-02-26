package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.utils.DateUtils;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

public class EmptyClass extends MyBaseActivity implements View.OnClickListener {

    private Button btn_searchclass;
    private ImageButton ib_back;
    private LinearLayout ll_data;
    private LinearLayout ll_time;
    private TextView tv_data;
    private TextView tv_time;
    private int yy  = 2019;
    private int mm  = 2;
    private int dd  = 26;
    private int indextime = 0;
    private String[] datatime = {"allday","am","pm","night","0102","0304","0506","0708","0910"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_class);

        initView();

        initEvent();
    }

    private void initEvent() {
        btn_searchclass.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        ll_time.setOnClickListener(this);
        ll_data.setOnClickListener(this);
    }

    private void initView() {
        btn_searchclass = findViewById(R.id.btn_searchclass);
        ib_back = findViewById(R.id.ib_market_header_back);
        ll_data = findViewById(R.id.ll_date);
        ll_time = findViewById(R.id.ll_time);
        tv_data = findViewById(R.id.tv_data);
        tv_time = findViewById(R.id.tv_time);
        tv_data.setText(DateUtils.getData());
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()) {
            case R.id.btn_searchclass:
                intent = new Intent(EmptyClass.this, EmptyClassDetails.class);
                intent.putExtra("date",yy+"-"+mm+"-"+dd);
                intent.putExtra("time",datatime[indextime]);
                startActivity(intent);
                finish();
                break;
            case R.id.ib_market_header_back:
                intent = new Intent(EmptyClass.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_time:
                onOptionPicker();
                break;
            case R.id.ll_date:
                onYearMonthDayPicker();
                break;
        }
    }

    public  void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setTextSize(24);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2050, 1, 11);
        picker.setRangeStart(2016, 1, 1);
        picker.setSelectedItem(Integer.valueOf(DateUtils.getYear()),Integer.valueOf(DateUtils.getMonth()), Integer.valueOf(DateUtils.getDay()));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv_data.setText(year+"-"+month+"-"+day);
                yy = Integer.valueOf(year);
                mm = Integer.valueOf(month);
                dd = Integer.valueOf(day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public void onOptionPicker() {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "全天","上午","下午","晚上", "一二节", "三四节","五六节","七八节","九十节"
        });
        picker.setTextSize(24);
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tv_time.setText(item);
                indextime = index;
            }
        });
        picker.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(EmptyClass.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
