package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.bin.david.form.core.SmartTable;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.Score;
import com.clxk.h.sdustcamp.operator.MySQLiteOperatorOfScore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created on 19/1/29
 */
public class ScoreActivity extends MyBaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.ib_market_header_back)
    ImageButton ib_back;
    @BindView(R.id.sp_term)
    Spinner sp_term;
    @BindView(R.id.sp_classprop)
    Spinner sp_classprop;
    @BindView(R.id.st_score)
    SmartTable st_score;

    private List<String> props;
    private List<String> terms;
    private MySQLiteOperatorOfScore sqLiteOperatorOfScore;
    private List<Score> sources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        initView();

        initEvent();


    }

    private void initEvent() {

        ib_back.setOnClickListener(this);
        sp_term.setOnItemSelectedListener(this);
        sp_classprop.setOnItemSelectedListener(this);
    }

    private void initView() {

        ib_back = findViewById(R.id.ib_market_header_back);
        sp_classprop = findViewById(R.id.sp_classprop);
        sp_term = findViewById(R.id.sp_term);
        st_score = findViewById(R.id.st_score);

        terms = new ArrayList<>();
        terms.add("全部");
        terms.add("2016-2017-1");
        terms.add("2016-2017-2");
        terms.add("2017-2018-1");
        terms.add("2017-2018-2");
        terms.add("2018-2019-1");
        terms.add("2018-2019-2");
        terms.add("2019-2020-1");
        terms.add("2019-2020-2");
        ArrayAdapter<String> adapter_term = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,terms);
        adapter_term.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_term.setAdapter(adapter_term);

        props = new ArrayList<>();
        props.add("全部");
        props.add("必修");
        props.add("选修");
        props.add("公选");
        ArrayAdapter<String> adapter_prop = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, props);
        adapter_prop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_classprop.setAdapter(adapter_prop);
        sqLiteOperatorOfScore = new MySQLiteOperatorOfScore(this);
        sources = sqLiteOperatorOfScore.queryAll();
        st_score.setData(sources);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                intent.putExtra("frId", R.id.ll_service);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            intent.putExtra("frId", R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String term = sp_term.getSelectedItem().toString();
        String classprop = sp_classprop.getSelectedItem().toString();
        List<Score> cursources = new ArrayList<>();
        for(Score s : sources) {
            if((s.getTerm().equals(term) && s.getClassprop().equals(classprop))
                    || (term.equals("全部") && s.getClassprop().equals(classprop))
                    || (classprop.equals("全部") && s.getTerm().equals(term))
                    || (classprop.equals("全部") && term.equals("全部"))){
                cursources.add(s);
            }
        }
        if(cursources.size() == 0) {
            cursources.add(new Score("-1","暂无数据","","","","","","",""));
        }
        st_score.setData(cursources);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
