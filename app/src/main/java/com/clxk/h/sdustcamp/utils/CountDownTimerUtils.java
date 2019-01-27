package com.clxk.h.sdustcamp.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;


public class CountDownTimerUtils extends CountDownTimer {
    private Button btn;
    private long millisInFuture;

    public CountDownTimerUtils(Button btn, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.millisInFuture = millisInFuture;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false); //设置不可点击
        btn.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
        btn.setBackgroundColor(Color.parseColor("#cccccc")); //设置按钮为灰色，这时是不能点击的
    }

    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setClickable(true);//重新获得点击
        btn.setBackgroundColor(Color.parseColor("#c1c1c1"));  //还原背景色
    }
}