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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class LoginChangePassword extends AppCompatActivity {

    private TextView tv_findPasswordInTitle;
    private EditText et_newPassword;
    private Button btn_modifyChange;
    private String PHONE;
    private String OBJECTID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_changepassword);

        //find
        tv_findPasswordInTitle = (TextView)findViewById(R.id.tv_findPasswordInTitle);
        et_newPassword = (EditText)findViewById(R.id.et_newPassword);
        btn_modifyChange = (Button)findViewById(R.id.btn_modifyChange);


        //setListener
        tv_findPasswordInTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getX() <= tv_findPasswordInTitle.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(LoginChangePassword.this, LoginFindPassword.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        btn_modifyChange.setOnClickListener(new ButtonClickListener());

        PHONE = getIntent().getStringExtra("phone").trim();
        OBJECTID = getIntent().getStringExtra("id").trim();

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_findPasswordInTitle.setCompoundDrawables(drawable,null,null,null);
    }

    public class ButtonClickListener implements View.OnClickListener {

        Intent intent = null;
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_modifyChange:
                    if(et_newPassword.getText().toString().length() < 8 || et_newPassword.getText().toString().length() > 15) {
                        Toast.makeText(LoginChangePassword.this, "密码应不少于8位",Toast.LENGTH_SHORT).show();
                        et_newPassword.setText("");
                    } else {
                        User t = new User();
                        t.setPassword(et_newPassword.getText().toString().trim());
                        t.update(OBJECTID, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null) {
                                    Toast.makeText(LoginChangePassword.this,"密码修改成功!",Toast.LENGTH_SHORT).show();
                                    intent = new Intent(LoginChangePassword.this, LoginInActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginChangePassword.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    et_newPassword.setText("");
                                }
                            }
                        });
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginChangePassword.this, LoginFindPassword.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
