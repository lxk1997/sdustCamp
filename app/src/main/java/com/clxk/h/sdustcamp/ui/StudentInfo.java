package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;

/**
 * Created on 19/2/24
 * 学籍信息
 */
public class StudentInfo extends MyBaseActivity implements View.OnClickListener {

    private ImageButton ib_back;
    private TextView tv_stuid;
    private TextView tv_stuname;
    private TextView tv_stuschool;
    private Button btn_disconnect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_details);

        initView();

        initEvent();

    }

    private void initEvent() {
        ib_back.setOnClickListener(this);
        btn_disconnect.setOnClickListener(this);

    }

    private void initView() {

        ib_back = findViewById(R.id.ib_market_header_back);
        tv_stuid = findViewById(R.id.tv_stuid);
        tv_stuname = findViewById(R.id.tv_stuname);
        tv_stuschool = findViewById(R.id.tv_stuschool);
        btn_disconnect = findViewById(R.id.btn_disconnect);

        tv_stuid.setText(MyApplication.getInstance().sdust_id);
        tv_stuschool.setText(MyApplication.getInstance().sdust_school);
        tv_stuname.setText(MyApplication.getInstance().sdust_name);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch(v.getId()) {
            case R.id.ib_market_header_back:
                intent = new Intent(StudentInfo.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_mine);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_disconnect:
                SharedPreferences preferences = getSharedPreferences("stuconnect",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("stulogin",false);
                editor.commit();
                Toast.makeText(StudentInfo.this,"学号已解除绑定!", Toast.LENGTH_SHORT).show();
                intent = new Intent(StudentInfo.this, ConnectStuId.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(StudentInfo.this, MainActivity.class);
            intent.putExtra("frId", R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
