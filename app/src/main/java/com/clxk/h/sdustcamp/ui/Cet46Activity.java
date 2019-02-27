package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.Cet46;
import com.clxk.h.sdustcamp.spider.GetCet46;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chook_lxk on 18/12/30
 *
 * 服务-英语四六级查询
 */
public class Cet46Activity extends MyBaseActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageButton ib_header_back;
    private EditText et_cet46_num;
    private EditText et_cet46_name;
    private Button btn_cet46_query;
    private List<Cet46> cet46s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet46);

        initView();

        initEvent();

    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_header_back.setOnClickListener(this);
        btn_cet46_query.setOnClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_header_back = findViewById(R.id.ib_market_header_back);
        et_cet46_name = findViewById(R.id.et_cet46_name);
        et_cet46_num = findViewById(R.id.et_cet46_num);
        btn_cet46_query = findViewById(R.id.btn_cet46_query);
        cet46s = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                intent = new Intent(Cet46Activity.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_cet46_query:
                quertCet46();
                break;

        }
    }

    private void quertCet46() {
        new Thread() {
            @Override
            public void run() {
                //cet46s = GetCet46.getCet46(et_cet46_num.getText().toString(),et_cet46_name.getText().toString());
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(Cet46Activity.this, Cet46ScoreForm.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Cet46Activity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
