package com.clxk.h.sdustcamp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.bean.User;
import com.clxk.h.sdustcamp.utils.UserUtils;

public class LoginInActivity extends AppCompatActivity {

    private TextView tv_title;
    private Button btn_regist;
    private Button btn_forgetKey;
    private EditText et_id;
    private EditText et_key;

    private Button btn_loginIn;

    private boolean is_registed;
    private String phoneNum;
    private String phoneKey;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //find
        tv_title = findViewById(R.id.tv_title);
        btn_regist = findViewById(R.id.btn_regist);
        et_id = findViewById(R.id.et_id);
        et_key = findViewById(R.id.et_key);
        btn_loginIn = findViewById(R.id.btn_loginIn);
        btn_forgetKey = findViewById(R.id.btn_forgetKey);


        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.arrowleft);
        drawable.setBounds(0,0,60,60);
        tv_title.setCompoundDrawables(drawable, null, null, null);

        //setOnClick
        btn_regist.setOnClickListener(new ButtonClickListener());
        btn_loginIn.setOnClickListener(new ButtonClickListener());
        btn_forgetKey.setOnClickListener(new ButtonClickListener());
        tv_title.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                if(event.getX() <= tv_title.getCompoundDrawables()[0].getBounds().width() * 4) {
                    Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
                    intent.putExtra("frId",R.id.ll_mine);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);
        if(sharedPreferences != null) {
            et_id.setText(sharedPreferences.getString("account",null));
        } else {
            et_id.setText(null);
            et_key.setText(null);
        }
    }

    //OnClickListener
    class ButtonClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent;
            switch (v.getId()) {
                case R.id.btn_regist:
                    intent = new Intent(LoginInActivity.this, RegistActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btn_loginIn:
                    phoneNum = et_id.getText().toString().trim();
                    phoneKey = et_key.getText().toString().trim();
                    User user = new User();
                    user.setUsername(phoneNum);
                    user.setPassword(phoneKey);
                    SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("account", user.getUsername());
                    editor.putString("password",phoneKey);
                    editor.putBoolean("loginbool",false);
                    editor.commit();
                    UserUtils.signIn(LoginInActivity.this, user);
                    break;
                case R.id.btn_forgetKey:
                    intent = new Intent(LoginInActivity.this, LoginFindPassword.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_mine);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
