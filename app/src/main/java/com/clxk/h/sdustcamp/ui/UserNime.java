package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserNime extends AppCompatActivity {

    private EditText et_userNime;
    private Button btn_exitUserNime;
    private TextView tv_title;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_nime);

        //find
        et_userNime = (EditText)findViewById(R.id.et_userNime);
        btn_exitUserNime = (Button)findViewById(R.id.btn_exitUserNime);
        tv_title = (TextView)findViewById(R.id.tv_userInfo);
        user = BmobUser.getCurrentUser(User.class);

        //setListener
        tv_title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getX() <= tv_title.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(UserNime.this, UserInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        btn_exitUserNime.setOnClickListener(new ButtonClickListener());

        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_title.setCompoundDrawables(drawable,null,null,null);
    }

    public class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_exitUserNime:
                    user.setNick(et_userNime.getText().toString());
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null) {
                                Toast.makeText(UserNime.this,"修改成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserNime.this,UserInfoActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(UserNime.this,"未知错误",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserNime.this,UserInfoActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(UserNime.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
