package com.clxk.h.sdustcamp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.TimeTable;
import com.clxk.h.sdustcamp.operator.ScheduleOperator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Schedule extends AppCompatActivity{
    private Spinner sp_week;
    private Button btn_11;
    private Button btn_12;
    private Button btn_13;
    private Button btn_14;
    private Button btn_15;
    private Button btn_16;
    private Button btn_17;
    private Button btn_21;
    private Button btn_22;
    private Button btn_23;
    private Button btn_24;
    private Button btn_25;
    private Button btn_26;
    private Button btn_27;
    private Button btn_31;
    private Button btn_32;
    private Button btn_33;
    private Button btn_34;
    private Button btn_35;
    private Button btn_36;
    private Button btn_37;
    private Button btn_41;
    private Button btn_42;
    private Button btn_43;
    private Button btn_44;
    private Button btn_45;
    private Button btn_46;
    private Button btn_47;
    private ArrayList<TimeTable> list;
    private ArrayAdapter<String> adapter;
    private Map<String, TimeTable> classItem;

    private LinearLayout ll_week;
    private LinearLayout ll_day;
    private LinearLayout ll_classItem;
    private LinearLayout ll_classInput;
    private LinearLayout ll_ifInput;

    private EditText et_kcmc;
    private EditText et_zc;
    private EditText et_js;
    private EditText et_skjs;

    private TextView tv_schedule;

    private TextView tv_week1;
    private TextView tv_day1;

    private TextView tv_kcmcc;
    private TextView tv_zcc;
    private TextView tv_jsc;
    private TextView tv_skjsc;

    private Button btn_schedule_left;

    private Button btn_closeItem;

    private Button btn_sureItem;
    private Button btn_quitItem;

    private Button btn_iedit;
    private Button btn_dedit;
    private Button btn_cedit;

    private ScheduleOperator mySql;

    private Boolean is_item = false;
    private Boolean is_have;
    private int st, en;
    private String dn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //find
        sp_week = findViewById(R.id.sp_week);
        btn_11 = findViewById(R.id.btn_11);
        btn_12 = findViewById(R.id.btn_12);
        btn_13 = findViewById(R.id.btn_13);
        btn_14 = findViewById(R.id.btn_14);
        btn_15 = findViewById(R.id.btn_15);
        btn_16 = findViewById(R.id.btn_16);
        btn_17 = findViewById(R.id.btn_17);
        btn_21 = findViewById(R.id.btn_21);
        btn_22 = findViewById(R.id.btn_22);
        btn_23 = findViewById(R.id.btn_23);
        btn_24 = findViewById(R.id.btn_24);
        btn_25 = findViewById(R.id.btn_25);
        btn_26 = findViewById(R.id.btn_26);
        btn_27 = findViewById(R.id.btn_27);
        btn_31 = findViewById(R.id.btn_31);
        btn_32 = findViewById(R.id.btn_32);
        btn_33 = findViewById(R.id.btn_33);
        btn_34 = findViewById(R.id.btn_34);
        btn_35 = findViewById(R.id.btn_35);
        btn_36 = findViewById(R.id.btn_36);
        btn_37 = findViewById(R.id.btn_37);
        btn_41 = findViewById(R.id.btn_41);
        btn_42 = findViewById(R.id.btn_42);
        btn_43 = findViewById(R.id.btn_43);
        btn_44 = findViewById(R.id.btn_44);
        btn_45 = findViewById(R.id.btn_45);
        btn_46 = findViewById(R.id.btn_46);
        btn_47 = findViewById(R.id.btn_47);
        ll_week = findViewById(R.id.ll_week);
        ll_day = findViewById(R.id.ll_day);
        ll_classItem = findViewById(R.id.ll_classItem);
        ll_classInput = findViewById(R.id.ll_classInput);
        ll_ifInput = findViewById(R.id.ll_ifInput);
        if(!is_item) ll_classItem.setVisibility(View.GONE);
        ll_classInput.setVisibility(View.GONE);
        ll_ifInput.setVisibility(View.GONE);

        et_js = findViewById(R.id.et_js);
        et_zc = findViewById(R.id.et_zc);
        et_kcmc = findViewById(R.id.et_kcmc);
        et_skjs = findViewById(R.id.et_skjs);

        tv_schedule = findViewById(R.id.tv_schedule);

        tv_day1 = findViewById(R.id.tv_day1);
        tv_week1 = findViewById(R.id.tv_week1);

        tv_kcmcc = findViewById(R.id.tv_kcmcc);
        tv_zcc = findViewById(R.id.tv_zcc);
        tv_jsc = findViewById(R.id.tv_jsc);
        tv_skjsc = findViewById(R.id.tv_skjsc);

        btn_closeItem = findViewById(R.id.btn_closeItem);
        btn_sureItem = findViewById(R.id.btn_sureItem);
        btn_quitItem = findViewById(R.id.btn_quitItem);

        btn_iedit = findViewById(R.id.btn_iedit);
        btn_dedit = findViewById(R.id.btn_dedit);
        btn_cedit = findViewById(R.id.btn_cedit);

        mySql = new ScheduleOperator(this);
        list = new ArrayList<>();
        list = mySql.queryAll();
        SharedPreferences preferences = getSharedPreferences("stuconnect",MODE_PRIVATE);
        if(preferences == null || preferences.getBoolean("stulogin", false) == false || list == null || list.size() == 0) {
            Toast.makeText(Schedule.this, "请先绑定学号之后查看课表", Toast.LENGTH_SHORT).show();
        }
        classItem = new HashMap<>();

        //设置长宽
        DisplayMetrics dm = new DisplayMetrics();
        dm = getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ll_week.measure(width,height);
        int h_1 = ll_week.getMeasuredHeight();
        int w_1 = ll_week.getMeasuredWidth();

        int width_1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height_1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ll_day.measure(width,height);
        int h_2 = ll_day.getMeasuredHeight();
        int w_2 = ll_day.getMeasuredWidth();

        int width_2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height_2 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv_schedule.measure(width,height);
        int h_3 = tv_schedule.getMeasuredHeight();
        int w_3 = tv_schedule.getMeasuredWidth();
        Window window = this.getWindow();
        int h = (int) (((double)screenHeight - (double)h_1 - (double)h_3 - getStatusBarHeight(window.getContext()))/4.0 - dip2px(this, 10));
        int w = (int) (((double)screenWidth - (double)w_2)/7.0 - dip2px(this, 4));
        RelativeLayout.LayoutParams lp_11=(RelativeLayout.LayoutParams)btn_11.getLayoutParams();
        lp_11.height = h;lp_11.width=w;
        btn_11.setLayoutParams(lp_11);
        RelativeLayout.LayoutParams lp_12=(RelativeLayout.LayoutParams)btn_12.getLayoutParams();
        lp_12.height = h;lp_12.width=w;
        btn_12.setLayoutParams(lp_12);
        RelativeLayout.LayoutParams lp_13=(RelativeLayout.LayoutParams)btn_13.getLayoutParams();
        lp_13.height = h;lp_13.width=w;
        btn_13.setLayoutParams(lp_13);
        RelativeLayout.LayoutParams lp_14=(RelativeLayout.LayoutParams)btn_14.getLayoutParams();
        lp_14.height = h;lp_14.width=w;
        btn_14.setLayoutParams(lp_14);
        RelativeLayout.LayoutParams lp_15=(RelativeLayout.LayoutParams)btn_15.getLayoutParams();
        lp_15.height = h;lp_15.width=w;
        btn_15.setLayoutParams(lp_15);
        RelativeLayout.LayoutParams lp_16=(RelativeLayout.LayoutParams)btn_16.getLayoutParams();
        lp_16.height = h;lp_16.width=w;
        btn_16.setLayoutParams(lp_16);
        RelativeLayout.LayoutParams lp_17=(RelativeLayout.LayoutParams)btn_17.getLayoutParams();
        lp_17.height = h;lp_17.width=w;
        btn_17.setLayoutParams(lp_17);
        RelativeLayout.LayoutParams lp_21=(RelativeLayout.LayoutParams)btn_21.getLayoutParams();
        lp_21.height = h;lp_21.width=w;
        btn_21.setLayoutParams(lp_21);
        RelativeLayout.LayoutParams lp_22=(RelativeLayout.LayoutParams)btn_22.getLayoutParams();
        lp_22.height = h;lp_22.width=w;
        btn_22.setLayoutParams(lp_22);
        RelativeLayout.LayoutParams lp_23=(RelativeLayout.LayoutParams)btn_23.getLayoutParams();
        lp_23.height = h;lp_23.width=w;
        btn_23.setLayoutParams(lp_23);
        RelativeLayout.LayoutParams lp_24=(RelativeLayout.LayoutParams)btn_24.getLayoutParams();
        lp_24.height = h;lp_24.width=w;
        btn_24.setLayoutParams(lp_24);
        RelativeLayout.LayoutParams lp_25=(RelativeLayout.LayoutParams)btn_25.getLayoutParams();
        lp_25.height = h;lp_25.width=w;
        btn_25.setLayoutParams(lp_25);
        RelativeLayout.LayoutParams lp_26=(RelativeLayout.LayoutParams)btn_26.getLayoutParams();
        lp_26.height = h;lp_26.width=w;
        btn_26.setLayoutParams(lp_26);
        RelativeLayout.LayoutParams lp_27=(RelativeLayout.LayoutParams)btn_27.getLayoutParams();
        lp_27.height = h;lp_27.width=w;
        btn_27.setLayoutParams(lp_27);
        RelativeLayout.LayoutParams lp_31=(RelativeLayout.LayoutParams)btn_31.getLayoutParams();
        lp_31.height = h;lp_31.width=w;
        btn_31.setLayoutParams(lp_31);
        RelativeLayout.LayoutParams lp_32=(RelativeLayout.LayoutParams)btn_32.getLayoutParams();
        lp_32.height = h;lp_32.width=w;
        btn_32.setLayoutParams(lp_32);
        RelativeLayout.LayoutParams lp_33=(RelativeLayout.LayoutParams)btn_33.getLayoutParams();
        lp_33.height = h;lp_33.width=w;
        btn_33.setLayoutParams(lp_33);
        RelativeLayout.LayoutParams lp_34=(RelativeLayout.LayoutParams)btn_34.getLayoutParams();
        lp_34.height = h;lp_34.width=w;
        btn_34.setLayoutParams(lp_34);
        RelativeLayout.LayoutParams lp_35=(RelativeLayout.LayoutParams)btn_35.getLayoutParams();
        lp_35.height = h;lp_35.width=w;
        btn_35.setLayoutParams(lp_35);
        RelativeLayout.LayoutParams lp_36=(RelativeLayout.LayoutParams)btn_36.getLayoutParams();
        lp_36.height = h;lp_36.width=w;
        btn_36.setLayoutParams(lp_36);
        RelativeLayout.LayoutParams lp_37=(RelativeLayout.LayoutParams)btn_37.getLayoutParams();
        lp_37.height = h;lp_37.width=w;
        btn_37.setLayoutParams(lp_37);
        RelativeLayout.LayoutParams lp_41=(RelativeLayout.LayoutParams)btn_41.getLayoutParams();
        lp_41.height = h;lp_41.width=w;
        btn_41.setLayoutParams(lp_41);
        RelativeLayout.LayoutParams lp_42=(RelativeLayout.LayoutParams)btn_42.getLayoutParams();
        lp_42.height = h;lp_42.width=w;
        btn_42.setLayoutParams(lp_42);
        RelativeLayout.LayoutParams lp_43=(RelativeLayout.LayoutParams)btn_43.getLayoutParams();
        lp_43.height = h;lp_43.width=w;
        btn_43.setLayoutParams(lp_43);
        RelativeLayout.LayoutParams lp_44=(RelativeLayout.LayoutParams)btn_44.getLayoutParams();
        lp_44.height = h;lp_44.width=w;
        btn_44.setLayoutParams(lp_44);
        RelativeLayout.LayoutParams lp_45=(RelativeLayout.LayoutParams)btn_45.getLayoutParams();
        lp_45.height = h;lp_45.width=w;
        btn_45.setLayoutParams(lp_45);
        RelativeLayout.LayoutParams lp_46=(RelativeLayout.LayoutParams)btn_46.getLayoutParams();
        lp_46.height = h;lp_46.width=w;
        btn_46.setLayoutParams(lp_46);
        RelativeLayout.LayoutParams lp_47=(RelativeLayout.LayoutParams)btn_47.getLayoutParams();
        lp_47.height = h;lp_47.width=w;
        btn_47.setLayoutParams(lp_47);

        btn_schedule_left = findViewById(R.id.btn_schedule_left);
        //setOnClick
        String[] week = {"第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周",
                "第13周","第14周","第15周","第16周","第17周","第18周","第19周","第20周","第21周","第22周"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, week);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_week.setAdapter(adapter);
        //加载课表
        String we = sp_week.getItemAtPosition(0).toString();
        String index = we.substring(1, we.length() - 1);
        loadingTimeTable(index);
        btn_11.setOnClickListener(new ButtonClickListener());
        btn_12.setOnClickListener(new ButtonClickListener());
        btn_13.setOnClickListener(new ButtonClickListener());
        btn_14.setOnClickListener(new ButtonClickListener());
        btn_15.setOnClickListener(new ButtonClickListener());
        btn_16.setOnClickListener(new ButtonClickListener());
        btn_17.setOnClickListener(new ButtonClickListener());
        btn_21.setOnClickListener(new ButtonClickListener());
        btn_22.setOnClickListener(new ButtonClickListener());
        btn_23.setOnClickListener(new ButtonClickListener());
        btn_24.setOnClickListener(new ButtonClickListener());
        btn_25.setOnClickListener(new ButtonClickListener());
        btn_26.setOnClickListener(new ButtonClickListener());
        btn_27.setOnClickListener(new ButtonClickListener());
        btn_31.setOnClickListener(new ButtonClickListener());
        btn_32.setOnClickListener(new ButtonClickListener());
        btn_33.setOnClickListener(new ButtonClickListener());
        btn_34.setOnClickListener(new ButtonClickListener());
        btn_35.setOnClickListener(new ButtonClickListener());
        btn_36.setOnClickListener(new ButtonClickListener());
        btn_37.setOnClickListener(new ButtonClickListener());
        btn_41.setOnClickListener(new ButtonClickListener());
        btn_42.setOnClickListener(new ButtonClickListener());
        btn_43.setOnClickListener(new ButtonClickListener());
        btn_44.setOnClickListener(new ButtonClickListener());
        btn_45.setOnClickListener(new ButtonClickListener());
        btn_46.setOnClickListener(new ButtonClickListener());
        btn_47.setOnClickListener(new ButtonClickListener());

        btn_11.setOnLongClickListener(new ButtonLongClickListener());
        btn_12.setOnLongClickListener(new ButtonLongClickListener());
        btn_13.setOnLongClickListener(new ButtonLongClickListener());
        btn_14.setOnLongClickListener(new ButtonLongClickListener());
        btn_15.setOnLongClickListener(new ButtonLongClickListener());
        btn_16.setOnLongClickListener(new ButtonLongClickListener());
        btn_17.setOnLongClickListener(new ButtonLongClickListener());
        btn_21.setOnLongClickListener(new ButtonLongClickListener());
        btn_22.setOnLongClickListener(new ButtonLongClickListener());
        btn_23.setOnLongClickListener(new ButtonLongClickListener());
        btn_24.setOnLongClickListener(new ButtonLongClickListener());
        btn_25.setOnLongClickListener(new ButtonLongClickListener());
        btn_26.setOnLongClickListener(new ButtonLongClickListener());
        btn_27.setOnLongClickListener(new ButtonLongClickListener());
        btn_31.setOnLongClickListener(new ButtonLongClickListener());
        btn_32.setOnLongClickListener(new ButtonLongClickListener());
        btn_33.setOnLongClickListener(new ButtonLongClickListener());
        btn_34.setOnLongClickListener(new ButtonLongClickListener());
        btn_35.setOnLongClickListener(new ButtonLongClickListener());
        btn_36.setOnLongClickListener(new ButtonLongClickListener());
        btn_37.setOnLongClickListener(new ButtonLongClickListener());
        btn_41.setOnLongClickListener(new ButtonLongClickListener());
        btn_42.setOnLongClickListener(new ButtonLongClickListener());
        btn_43.setOnLongClickListener(new ButtonLongClickListener());
        btn_44.setOnLongClickListener(new ButtonLongClickListener());
        btn_45.setOnLongClickListener(new ButtonLongClickListener());
        btn_46.setOnLongClickListener(new ButtonLongClickListener());
        btn_47.setOnLongClickListener(new ButtonLongClickListener());

        btn_closeItem.setOnClickListener(new ButtonClickListener());
        btn_quitItem.setOnClickListener(new ButtonClickListener());
        btn_sureItem.setOnClickListener(new ButtonClickListener());
        btn_iedit.setOnClickListener(new ButtonClickListener());
        btn_dedit.setOnClickListener(new ButtonClickListener());
        btn_cedit.setOnClickListener(new ButtonClickListener());

        btn_schedule_left.setOnClickListener(new ButtonClickListener());
        sp_week.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String data = (String)sp_week.getItemAtPosition(position);//从适配器中获取被选择的数据项
                String index = data.substring(1, data.length() - 1);
                loadingTimeTable(index);
            }
        });

        sp_week.setSelection(MyApplication.getInstance().zc-1);
    }

    //获得状态栏高度
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //OnClickListener
    class ButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            // TODO Auto-generated method stub
            Intent intent;
            TimeTable t = new TimeTable();
            switch(v.getId()) {
                case R.id.btn_schedule_left:
                    intent = new Intent(Schedule.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_service);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_11:
                    is_item = true;
                    if(classItem.get("11") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("11");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_12:
                    is_item = true;
                    if(classItem.get("12") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("12");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_13:
                    is_item = true;
                    if(classItem.get("13") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("13");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_14:
                    is_item = true;
                    if(classItem.get("14") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("14");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_15:
                    is_item = true;
                    if(classItem.get("15") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("15");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_16:
                    is_item = true;
                    if(classItem.get("16") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("16");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_17:
                    is_item = true;
                    if(classItem.get("17") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("17");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_21:
                    is_item = true;
                    if(classItem.get("21") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("21");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_22:
                    is_item = true;
                    if(classItem.get("22") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("22");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_23:
                    is_item = true;
                    if(classItem.get("23") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("23");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_24:
                    is_item = true;
                    if(classItem.get("24") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("24");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_25:
                    is_item = true;
                    if(classItem.get("25") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("25");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_26:
                    is_item = true;
                    if(classItem.get("26") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("26");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_27:
                    is_item = true;
                    if(classItem.get("27") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("27");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_31:
                    is_item = true;
                    if(classItem.get("31") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("31");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_32:
                    is_item = true;
                    if(classItem.get("32") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("32");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_33:
                    is_item = true;
                    if(classItem.get("33") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("33");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_34:
                    is_item = true;
                    if(classItem.get("34") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("34");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_35:
                    is_item = true;
                    if(classItem.get("35") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("35");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_36:
                    is_item = true;
                    if(classItem.get("36") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("36");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_37:
                    is_item = true;
                    if(classItem.get("37") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("37");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_41:
                    is_item = true;
                    if(classItem.get("41") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("41");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_42:
                    is_item = true;
                    if(classItem.get("42") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("42");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_43:
                    is_item = true;
                    if(classItem.get("43") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("43");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_44:
                    is_item = true;
                    if(classItem.get("44") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("44");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_45:
                    is_item = true;
                    if(classItem.get("45") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("45");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_46:
                    is_item = true;
                    if(classItem.get("46") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("46");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_47:
                    is_item = true;
                    if(classItem.get("47") == null) break;
                    ll_classItem.setVisibility(View.VISIBLE);
                    t = classItem.get("47");
                    getItem(t);
                    ifClick(false);
                    break;
                case R.id.btn_closeItem:
                    if(is_item) {
                        is_item = false;
                        ll_classItem.setVisibility(View.GONE);
                        ifClick(true);
                    }
                    break;
                case R.id.btn_sureItem:
                    if(et_kcmc.getText().toString() == "") {
                        Toast.makeText(Schedule.this, "课程名格式错误", Toast.LENGTH_SHORT).show();
                    } else if(judge(et_zc.getText().toString()) == false) {
                        Toast.makeText(Schedule.this, "周次格式错误", Toast.LENGTH_SHORT).show();
                    } else if(et_js.getText().toString() == "") {
                        Toast.makeText(Schedule.this, "教室格式错误", Toast.LENGTH_SHORT).show();
                    } else if(et_skjs.getText().toString() == "") {
                        Toast.makeText(Schedule.this, "授课教师格式错误", Toast.LENGTH_SHORT).show();
                    } else {
                        mySql.add(et_kcmc.getText().toString(),et_js.getText().toString(), et_skjs.getText().toString(), st+"", en+"",dn.substring(1, 2) , dn.substring(0, 1),"2018-2019-1");
                        Toast.makeText(Schedule.this, "添加成功，更新后显示", Toast.LENGTH_SHORT).show();
                        ll_classInput.setVisibility(View.GONE);
                        ifClick(true);
                        reFresh();
                    }
                    break;
                case R.id.btn_quitItem:
                    ll_classInput.setVisibility(View.GONE);
                    ifClick(true);
                    break;
                case R.id.btn_iedit:
                    ll_ifInput.setVisibility(View.GONE);
                    TimeTable tt = new TimeTable();
                    tt = classItem.get(dn);
                    et_kcmc.setText(tt.getClassName());
                    et_zc.setText(tt.getClassStart() + '-' + tt.getClassEnd());
                    et_js.setText(tt.getClassRoom());
                    et_skjs.setText(tt.getTeacher());
                    ll_classInput.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_cedit:
                    ll_ifInput.setVisibility(View.GONE);
                    ifClick(true);
                    break;
                case R.id.btn_dedit:
                    TimeTable tb = new TimeTable();
                    tb = classItem.get(dn);
                    int id = mySql.getId(tb.getClassName(), tb.getClassRoom(), tb.getTeacher(), tb.getClassStart(), tb.getClassEnd(), tb.getClassDay(), tb.getClassNum(),"2018-2019-1");
                    mySql.delete(id);
                    Toast.makeText(Schedule.this, "删除成功", Toast.LENGTH_SHORT).show();
                    ll_ifInput.setVisibility(View.GONE);
                    ifClick(true);
                    reFresh();
                    break;
            }
        }
    }

    //OnClickListener
    class ButtonLongClickListener implements OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            // TODO Auto-generated method stub
            Intent intent  = null;
            TimeTable t = new TimeTable();
            switch(v.getId()) {
                case R.id.btn_11:
                    is_item = true;
                    dn = "11";
                    if(classItem.get("11") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_12:
                    is_item = true;
                    dn = "12";
                    if(classItem.get("12") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_13:
                    is_item = true;
                    dn = "13";
                    if(classItem.get("13") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_14:
                    is_item = true;
                    dn = "14";
                    if(classItem.get("14") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_15:
                    is_item = true;
                    dn = "15";
                    if(classItem.get("15") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_16:
                    is_item = true;
                    dn = "16";
                    if(classItem.get("16") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_17:
                    is_item = true;
                    dn = "17";
                    if(classItem.get("17") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_21:
                    is_item = true;
                    dn = "21";
                    if(classItem.get("21") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_22:
                    is_item = true;
                    dn = "22";
                    if(classItem.get("22") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_23:
                    is_item = true;
                    dn = "23";
                    if(classItem.get("23") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_24:
                    is_item = true;
                    dn = "24";
                    if(classItem.get("24") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_25:
                    is_item = true;
                    dn = "25";
                    if(classItem.get("25") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_26:
                    is_item = true;
                    dn = "26";
                    if(classItem.get("26") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_27:
                    is_item = true;
                    dn = "27";
                    if(classItem.get("27") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_31:
                    is_item = true;
                    dn = "31";
                    if(classItem.get("31") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_32:
                    is_item = true;
                    dn = "32";
                    if(classItem.get("32") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_33:
                    is_item = true;
                    dn = "33";
                    if(classItem.get("33") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_34:
                    is_item = true;
                    dn = "34";
                    if(classItem.get("34") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_35:
                    is_item = true;
                    dn = "35";
                    if(classItem.get("35") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_36:
                    is_item = true;
                    dn = "36";
                    if(classItem.get("36") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_37:
                    is_item = true;
                    dn = "37";
                    if(classItem.get("37") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_41:
                    is_item = true;
                    dn = "41";
                    if(classItem.get("41") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_42:
                    is_item = true;
                    dn = "42";
                    if(classItem.get("42") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_43:
                    is_item = true;
                    dn = "43";
                    if(classItem.get("43") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_44:
                    is_item = true;
                    dn = "44";
                    if(classItem.get("44") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_45:
                    is_item = true;
                    dn = "45";
                    if(classItem.get("45") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    ifClick(false);
                    break;
                case R.id.btn_46:
                    is_item = true;
                    dn = "46";
                    if(classItem.get("46") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
                case R.id.btn_47:
                    is_item = true;
                    dn = "47";
                    if(classItem.get("47") == null) {
                        is_have = false;
                        ll_classInput.setVisibility(View.VISIBLE);
                        ifClick(false);
                        break;
                    }
                    is_have = true;
                    ll_ifInput.setVisibility(View.VISIBLE);
                    ifClick(false);
                    break;
            }
            return true;
        }

    }

    //加载课表
    @SuppressWarnings("deprecation")
    public void loadingTimeTable(String index) {
        initTimeTable();
        String[] colorString = new String[]{"#FFB5C5","#FFC125", "#AB82FF", "#6E8B3D", "#87CEFF", "#98FB98", "#FF7F50", "#00EE76", "#00CED1", "#00CD66", "#6495ED", "#7D9EC0", "#BDB76B", "#F08080"};
        int idx = Integer.parseInt(index);
        for(TimeTable t : list) {
            int st = Integer.parseInt(t.getClassStart());
            int en = Integer.parseInt(t.getClassEnd());
            if(st <= idx && en >= idx) {
                String dw = t.getClassNum() + t.getClassDay();
                Log.i("123周课",dw);
                Random random;
                GradientDrawable myGrad;
                classItem.put(dw, t);
                switch (dw.trim()) {
                    case "11":
                        Log.i("123class","123");
                        btn_11.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_11.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "12":
                        btn_12.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_12.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "13":
                        btn_13.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_13.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "14":
                        btn_14.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_14.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "15":
                        btn_15.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_15.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "16":
                        btn_16.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_16.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "17":
                        btn_17.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_17.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "21":
                        btn_21.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_21.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "22":
                        btn_22.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_22.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "23":
                        btn_23.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_23.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "24":
                        btn_24.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_24.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "25":
                        btn_25.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_25.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "26":
                        btn_26.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_26.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "27":
                        btn_27.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_27.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "31":
                        btn_31.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_31.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "32":
                        btn_32.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_32.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "33":
                        btn_33.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_33.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "34":
                        btn_34.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_34.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "35":
                        btn_35.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_35.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "36":
                        btn_36.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_36.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "37":
                        btn_37.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_37.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "41":
                        btn_41.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_41.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "42":
                        btn_42.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_42.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "43":
                        btn_43.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_43.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "44":
                        btn_44.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_44.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "45":
                        btn_45.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_45.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "46":
                        btn_46.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_46.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                    case "47":
                        btn_47.setText(t.getClassName() + "@" + t.getClassRoom());
                        random = new Random();
                        myGrad = (GradientDrawable)btn_47.getBackground();
                        myGrad.setColor(Color.parseColor(colorString[random.nextInt(14)]));
                        break;
                }

            }
        }
    }
    public void initTimeTable() {
        classItem.clear();
        GradientDrawable myGrad;
        btn_11.setText(null);
        myGrad = (GradientDrawable)btn_11.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_11.setBackgroundColor(Color.TRANSPARENT);
        btn_12.setText(null);
        myGrad = (GradientDrawable)btn_12.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_12.setBackgroundColor(Color.TRANSPARENT);
        btn_13.setText(null);
        myGrad = (GradientDrawable)btn_13.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_13.setBackgroundColor(Color.TRANSPARENT);
        btn_14.setText(null);
        myGrad = (GradientDrawable)btn_14.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_14.setBackgroundColor(Color.TRANSPARENT);
        btn_15.setText(null);
        myGrad = (GradientDrawable)btn_15.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_15.setBackgroundColor(Color.TRANSPARENT);
        btn_16.setText(null);
        myGrad = (GradientDrawable)btn_16.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_16.setBackgroundColor(Color.TRANSPARENT);
        btn_17.setText(null);
        myGrad = (GradientDrawable)btn_17.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_17.setBackgroundColor(Color.TRANSPARENT);
        btn_21.setText(null);
        myGrad = (GradientDrawable)btn_21.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_21.setBackgroundColor(Color.TRANSPARENT);
        btn_22.setText(null);
        myGrad = (GradientDrawable)btn_22.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_22.setBackgroundColor(Color.TRANSPARENT);
        btn_23.setText(null);
        myGrad = (GradientDrawable)btn_23.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_23.setBackgroundColor(Color.TRANSPARENT);
        btn_24.setText(null);
        myGrad = (GradientDrawable)btn_24.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_24.setBackgroundColor(Color.TRANSPARENT);
        btn_25.setText(null);
        myGrad = (GradientDrawable)btn_25.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_25.setBackgroundColor(Color.TRANSPARENT);
        btn_26.setText(null);
        myGrad = (GradientDrawable)btn_26.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_26.setBackgroundColor(Color.TRANSPARENT);
        btn_27.setText(null);
        myGrad = (GradientDrawable)btn_27.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_27.setBackgroundColor(Color.TRANSPARENT);
        btn_31.setText(null);
        myGrad = (GradientDrawable)btn_31.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_31.setBackgroundColor(Color.TRANSPARENT);
        btn_32.setText(null);
        myGrad = (GradientDrawable)btn_32.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_32.setBackgroundColor(Color.TRANSPARENT);
        btn_33.setText(null);
        myGrad = (GradientDrawable)btn_33.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_33.setBackgroundColor(Color.TRANSPARENT);
        btn_34.setText(null);
        myGrad = (GradientDrawable)btn_34.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_34.setBackgroundColor(Color.TRANSPARENT);
        btn_35.setText(null);
        myGrad = (GradientDrawable)btn_35.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_35.setBackgroundColor(Color.TRANSPARENT);
        btn_36.setText(null);
        myGrad = (GradientDrawable)btn_36.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_36.setBackgroundColor(Color.TRANSPARENT);
        btn_37.setText(null);
        myGrad = (GradientDrawable)btn_37.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_37.setBackgroundColor(Color.TRANSPARENT);
        btn_41.setText(null);
        myGrad = (GradientDrawable)btn_41.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_41.setBackgroundColor(Color.TRANSPARENT);
        btn_42.setText(null);
        myGrad = (GradientDrawable)btn_42.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_42.setBackgroundColor(Color.TRANSPARENT);
        btn_43.setText(null);
        myGrad = (GradientDrawable)btn_43.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_43.setBackgroundColor(Color.TRANSPARENT);
        btn_44.setText(null);
        myGrad = (GradientDrawable)btn_44.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_44.setBackgroundColor(Color.TRANSPARENT);
        btn_45.setText(null);
        myGrad = (GradientDrawable)btn_45.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_45.setBackgroundColor(Color.TRANSPARENT);
        btn_46.setText(null);
        myGrad = (GradientDrawable)btn_46.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_46.setBackgroundColor(Color.TRANSPARENT);
        btn_47.setText(null);
        myGrad = (GradientDrawable)btn_47.getBackground();
        myGrad.setColor(Color.TRANSPARENT);
        //btn_47.setBackgroundColor(Color.TRANSPARENT);

    }

    public static int dip2px(Context context,float dipValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int) (scale*dipValue+0.5f);
    }

    public void getItem(TimeTable t) {
        tv_kcmcc.setText(t.getClassName());
        tv_zcc.setText(t.getClassStart() + " - " + t.getClassEnd());
        tv_jsc.setText(t.getClassRoom());
        tv_skjsc.setText(t.getTeacher());
    }

    public void ifClick(Boolean b) {
        btn_11.setClickable(b);
        btn_12.setClickable(b);
        btn_13.setClickable(b);
        btn_14.setClickable(b);
        btn_15.setClickable(b);
        btn_16.setClickable(b);
        btn_17.setClickable(b);
        btn_21.setClickable(b);
        btn_22.setClickable(b);
        btn_23.setClickable(b);
        btn_24.setClickable(b);
        btn_25.setClickable(b);
        btn_26.setClickable(b);
        btn_27.setClickable(b);
        btn_31.setClickable(b);
        btn_32.setClickable(b);
        btn_33.setClickable(b);
        btn_34.setClickable(b);
        btn_35.setClickable(b);
        btn_36.setClickable(b);
        btn_37.setClickable(b);
        btn_41.setClickable(b);
        btn_42.setClickable(b);
        btn_43.setClickable(b);
        btn_44.setClickable(b);
        btn_45.setClickable(b);
        btn_46.setClickable(b);
        btn_47.setClickable(b);
        sp_week.setEnabled(b);

        btn_11.setLongClickable(b);
        btn_12.setLongClickable(b);
        btn_13.setLongClickable(b);
        btn_14.setLongClickable(b);
        btn_15.setLongClickable(b);
        btn_16.setLongClickable(b);
        btn_17.setLongClickable(b);
        btn_21.setLongClickable(b);
        btn_22.setLongClickable(b);
        btn_23.setLongClickable(b);
        btn_24.setLongClickable(b);
        btn_25.setLongClickable(b);
        btn_26.setLongClickable(b);
        btn_27.setLongClickable(b);
        btn_31.setLongClickable(b);
        btn_32.setLongClickable(b);
        btn_33.setLongClickable(b);
        btn_34.setLongClickable(b);
        btn_35.setLongClickable(b);
        btn_36.setLongClickable(b);
        btn_37.setLongClickable(b);
        btn_41.setLongClickable(b);
        btn_42.setLongClickable(b);
        btn_43.setLongClickable(b);
        btn_44.setLongClickable(b);
        btn_45.setLongClickable(b);
        btn_46.setLongClickable(b);
        btn_47.setLongClickable(b);
    }

    public Boolean judge(String s) {
        char[] c = s.toCharArray();
        st = 0;
        en = 0;
        int flag = 0;
        for(int i = 0; i < s.length(); i++) {
            if(c[i] >= '0' && c[i] <= '9' && flag == 0) {
                st *= 10;
                st += c[i] - '0';
            } else if(c[i] == '-') {
                flag = 1;
            } else if(c[i] >= '0' && c[i] <= '9') {
                flag = 2;
                en *= 10;
                en += c[i] - '0';
            } else return false;
        }
        if(st > en) return false;
        if(st <= 0 || en <= 0) return false;
        if(flag != 2) return false;
        return true;
    }

    public void reFresh() {
        finish();
        Intent intent = new Intent(Schedule.this, Schedule.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Schedule.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
