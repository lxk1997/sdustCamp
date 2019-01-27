package com.clxk.h.sdustcamp.ui;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.Constans;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Chook_lxk
 *
 * 服务-话费流量
 */
public class MobileFeeActivity extends MyBaseActivity implements View.OnClickListener {

    private ImageButton ib_header_back;
    private TextView tv_mobile_fee_balance;
    private TextView tv_mobile_data;
    private TextView tv_mobile_update_time;
    private Button btn_mobile_fee_query;

    private TelephonyManager myPhone;
    private SmsObserver smsObserver;

    private Uri SMS_INBOX;

    private String phoneString, smsStringFee, smsStringData;

    protected static final int MSG_INBOX = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_state);

        checkMobilePermission();

        initView();

        initEvent();

        startMobileQuery();
        checkSmsSend();
        Toast.makeText(MobileFeeActivity.this,myPhone.getSimOperatorName()+":"+myPhone.getLine1Number(),Toast.LENGTH_SHORT).show();

    }

    /**
     * 检测是否有发送短信和查看手机状态的权限
     */
    private void checkMobilePermission() {

        if(hasPermission(Manifest.permission.SEND_SMS) == false) {
            requestPermission(Constans.SMS_CODE,Manifest.permission.SEND_SMS);
        }
        if(hasPermission(Manifest.permission.CALL_PHONE) == false) {
            requestPermission(Constans.CALL_PHONE_CODE,Manifest.permission.CALL_PHONE);
        }
    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_header_back.setOnClickListener(this);
        btn_mobile_fee_query.setOnClickListener(this);
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_header_back = findViewById(R.id.ib_market_header_back);
        tv_mobile_data = findViewById(R.id.tv_mobile_data);
        tv_mobile_fee_balance = findViewById(R.id.tv_mobile_fee_balance);
        tv_mobile_update_time = findViewById(R.id.tv_mobile_update_time);
        btn_mobile_fee_query = findViewById(R.id.btn_mobile_fee_query);

        SMS_INBOX = Uri.parse("content://sms/inbox");

        myPhone = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        smsObserver = new SmsObserver(this, smsHandle);

        SharedPreferences sharedPreferences = getSharedPreferences("MobileFee",MODE_PRIVATE);
        tv_mobile_fee_balance.setText(sharedPreferences.getString("fee",""));
        tv_mobile_data.setText(sharedPreferences.getString("data",""));
        tv_mobile_update_time.setText(sharedPreferences.getString("date",""));


    }

    private Handler smsHandle = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INBOX:
                    getMessageFromPhone();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.btn_mobile_fee_query:
                sendMessage();
                break;
            case R.id.ib_market_header_back:
                intent = new Intent(MobileFeeActivity.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                finish();
                break;
        }

    }


    /**
     * 发送查询短信
     */
    private void startMobileQuery() {

        switch(myPhone.getSimOperatorName().toLowerCase()) {
            case "cmcc":
                phoneString = "10086";
                smsStringFee="ye";
                smsStringData="cxll";
                break;
            case "cucc":
                phoneString="10010";
                smsStringFee="101";
                smsStringData="2082";
                break;
            case "ctcc":
                phoneString="10000";
                smsStringFee="102";
                smsStringData="108";
                break;
        }
    }

    /**
     * 发送消息
     */
    private void sendMessage() {

        SmsManager smsManager = SmsManager.getDefault();
        //发送话费查询
        List<String> divideContents = smsManager.divideMessage(smsStringFee);
        for(String s:divideContents) {
            smsManager.sendTextMessage(phoneString,null,s,null,null);
        }
        //发送流量查询
        divideContents = smsManager.divideMessage(smsStringData);
        for(String s:divideContents) {
            smsManager.sendTextMessage(phoneString,null,s,null,null);
        }
    }

    /**
     * 从手机中获取短信
     */
    private void getMessageFromPhone() {

        String fee="";
        double data = 0;

        ContentResolver cr = getContentResolver();
        String[] projection = new String[] {"body","address"};
        String where = " date >  "
                + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");

        if(cur == null) {
            return;
        }
        if(cur.moveToFirst()) {
            String number = cur.getString(cur.getColumnIndex("address"));
            String body = cur.getString(cur.getColumnIndex("body"));

            if(number.equals(phoneString)) {
                SharedPreferences sharedPreferences = getSharedPreferences("MobileFee",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(body.indexOf("余额") != -1) {
                    int st = body.indexOf("可用余额为"), en = body.indexOf("元");
                    fee = body.substring(st+5, en);
                    editor.putString("fee",fee);
                    tv_mobile_fee_balance.setText(fee);
                }
                else if(body.indexOf("剩余流量") != -1) {
                    data = 0;
                    for(int i = 0; i < body.length()-12; i++) {
                        if(body.substring(i,i+6).equals("剩余流量还有")) {
                            for(int j = i+6;;j++) {
                                if(body.charAt(j) == 'M') {
                                    data += Double.valueOf(body.substring(i+6, j));
                                    break;
                                }
                            }
                        }
                    }
                    tv_mobile_data.setText("本月可用 "+Math.ceil(data)+"M");
                    editor.putString("data","本月可用 "+Math.ceil(data)+"M");
                }
                long time = System.currentTimeMillis();
                SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm, EE",new Locale("zh"));
                String sDate = shortDateFormat.format(new Date(time));
                tv_mobile_update_time.setText(sDate);
                editor.putString("date",sDate);
                editor.commit();
            }
        }
    }

    /**
     * 处理短信发送通知
     */
    private void checkSmsSend() {

        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,0);
        //注册发送信息的广播接收者
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(MobileFeeActivity.this, "正在查询，请稍后...", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE://普通错误
                        Toast.makeText(MobileFeeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF: //无线广播被明确地关闭
                        Toast.makeText(MobileFeeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU://没有提供pdu
                        Toast.makeText(MobileFeeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:  //服务当前不可用
                        Toast.makeText(MobileFeeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        }, new IntentFilter(SENT_SMS_ACTION));
    }

    /**
     * 观察者模式监听短信
     */
    class SmsObserver extends ContentObserver {

        private static final int MSG_INBOX = 1;
        private Context mContext;
        private Handler mHandler;
        public SmsObserver(Context context,Handler handler) {
            super(handler);
            this.mContext = context;
            this.mHandler = handler;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            mHandler.obtainMessage(MSG_INBOX, "收到了短信！！！").sendToTarget();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (smsObserver != null) {
            getContentResolver().registerContentObserver(
                    Uri.parse("content://sms/inbox"), true, smsObserver);// 注册监听短信数据库的变化
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);// 取消监听短信数据库的变化
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(MobileFeeActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_service);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
