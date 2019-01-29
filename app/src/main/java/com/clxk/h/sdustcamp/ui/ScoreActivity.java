package com.clxk.h.sdustcamp.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;

/**
 * Created on 19/1/29
 */
public class ScoreActivity extends MyBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }
}
