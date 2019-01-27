package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by Chook_lxk on 18/12/30
 *
 * 服务-英语四六级查询
 */
public class Cet46Activity extends MyBaseActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageButton ib_header_back;
    private EditText et_cet46_num;
    private EditText et_cet46_name;
    private EditText et_cet46_modifyKey;
    private ImageView iv_cet46_modify;
    private Button btn_cet46_query;

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
        iv_cet46_modify.setOnTouchListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_header_back = findViewById(R.id.ib_market_header_back);
        et_cet46_modifyKey = findViewById(R.id.et_cet46_modifyKey);
        et_cet46_name = findViewById(R.id.et_cet46_name);
        et_cet46_num = findViewById(R.id.et_cet46_num);
        iv_cet46_modify = findViewById(R.id.iv_cet46_modify);
        btn_cet46_query = findViewById(R.id.btn_cet46_query);
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
                Toast.makeText(Cet46Activity.this,"未知错误",Toast.LENGTH_SHORT).show();
                break;

        }
    }

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
