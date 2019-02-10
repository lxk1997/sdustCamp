package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;
import com.clxk.h.sdustcamp.bean.UpdatingsKDYW;

public class UpdatingsKDYWActivity extends MyBaseActivity {

    private TextView tv_titlekdyw;
    private TextView tv_timekdyw;
    private TextView tv_contentkdyw;
    private TextView tv_kdyw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updating_kdyw);

        //find
        tv_kdyw = findViewById(R.id.tv_kdyw);
        tv_timekdyw = findViewById(R.id.tv_timekdyw);
        tv_contentkdyw = findViewById(R.id.tv_contentkdyw);
        tv_titlekdyw = findViewById(R.id.tv_titlekdyw);

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_kdyw.setCompoundDrawables(drawable,null,null,null);

        //setOnClick
        tv_kdyw.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getX() <= tv_kdyw.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(UpdatingsKDYWActivity.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_updatings);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        Log.i("hehehe","12131232323");


        UpdatingsKDYW node = MyApplication.getInstance().updatings;
        tv_titlekdyw.setText(node.getTitle().toString());
        tv_timekdyw.setText("时间 " + node.getTime());
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
            Intent intent = new Intent(UpdatingsKDYWActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_updatings);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
