package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;

public class Cet46ScoreForm extends MyBaseActivity implements View.OnClickListener {

    private ImageButton ib_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cet46_details);

        initView();

        initEvent();
    }

    private void initEvent() {
        ib_back.setOnClickListener(this);
    }

    private void initView() {
        ib_back = findViewById(R.id.ib_market_header_back);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ib_market_header_back:
                Intent intent = new Intent(Cet46ScoreForm.this, Cet46Activity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Cet46ScoreForm.this, Cet46Activity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
