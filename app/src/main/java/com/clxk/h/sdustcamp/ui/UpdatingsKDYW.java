package com.clxk.h.sdustcamp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.Updatings;

public class UpdatingsKDYW extends Activity {

    private TextView tv_titlekdyw;
    private TextView tv_timekdyw;
    private TextView tv_contentkdyw;
    private TextView tv_kdyw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updating_kdyw);

        //find
        tv_kdyw = (TextView)findViewById(R.id.tv_kdyw);
        tv_timekdyw = (TextView)findViewById(R.id.tv_timekdyw);
        tv_contentkdyw = (TextView)findViewById(R.id.tv_contentkdyw);
        tv_titlekdyw = (TextView)findViewById(R.id.tv_titlekdyw);

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_kdyw.setCompoundDrawables(drawable,null,null,null);

        //setOnClick
        tv_kdyw.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getX() <= tv_kdyw.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(UpdatingsKDYW.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_updatings);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        Log.i("hehehe","12131232323");

        Intent intent = getIntent();
        Updatings node = intent.getParcelableExtra("Updatings");
        tv_titlekdyw.setText(node.getTitle().toString());
        tv_timekdyw.setText("时间 " + node.getTime().toString() + " · 点击量 " + node.getRate().toString());
        tv_contentkdyw.setText(node.getContext().toString());
        tv_contentkdyw.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    //OnClickListener
    class ButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent  = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UpdatingsKDYW.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_updatings);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
