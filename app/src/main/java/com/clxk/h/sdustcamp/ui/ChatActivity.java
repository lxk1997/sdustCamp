package com.clxk.h.sdustcamp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.clxk.h.sdustcamp.MyApplication;
import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.base.MyBaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Chook_lxk on 19/1/14
 *
 * 消息-聊天
 */
public class ChatActivity extends MyBaseActivity implements View.OnClickListener,MessageListHandler {

    private ImageButton ib_header_back;
    public static TextView tv_chat_text;
    private Button btn_send;
    private EditText et_chat_msg;
    private BmobIMConversation conversationEntrace;
    private BmobIMConversation conversationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();

        initEvent();

    }

    private void initEvent() {

        ib_header_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    private void initView() {

        ib_header_back = findViewById(R.id.ib_market_header_back);
        tv_chat_text = findViewById(R.id.tv_chat_text);
        btn_send = findViewById(R.id.btn_send);
        et_chat_msg = findViewById(R.id.et_chat_msg);

        MyApplication.getInstance().context = this;

        conversationEntrace = (BmobIMConversation)getIntent().getBundleExtra("c").getSerializable("c");
        conversationManager = BmobIMConversation.obtain(BmobIMClient.getInstance(),conversationEntrace);

    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.ib_market_header_back:
                intent = new Intent(ChatActivity.this, MainActivity.class);
                intent.putExtra("frId",R.id.ll_news);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_send:
                sendMessage();
                break;
        }
    }

    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onStart(BmobIMMessage bmobIMMessage) {
            super.onStart(bmobIMMessage);
            tv_chat_text.append(et_chat_msg.getText()+"\n");
            et_chat_msg.setText("");
        }

        @Override
        public void done(BmobIMMessage bmobIMMessage, BmobException e) {
            et_chat_msg.setText("");

        }
    };

    /**
     * 消息发送
     */
    private void sendMessage() {

        String text = et_chat_msg.getText().toString();
        if(TextUtils.isEmpty(text.trim())) {
            Toast.makeText(ChatActivity.this,"输入内容为空！",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(text.trim());
        msg.setExtra("OK");
        conversationManager.sendMessage(msg,listener);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            intent.putExtra("frId",R.id.ll_news);
            startActivity(intent);
            finish();
        }
        return false;
    }

    /**
     * 自定义消息接收器
     * @param list
     */
    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        Log.i("ChatActivityl","qwe");
        for(int i = 0; i < list.size(); i++) {
            addMsg2Chat(list.get(i));
        }
    }

    /**
     * 消息添加到聊天界面
     * @param messageEvent
     */
    private void addMsg2Chat(MessageEvent messageEvent) {

        BmobIMMessage msg = messageEvent.getMessage();
        if(conversationManager != null && messageEvent != null && conversationManager.getConversationId().equals(messageEvent.getMessage().getConversationId())
                && !msg.isTransient()) {
            tv_chat_text.append(msg.getContent());
            conversationManager.updateReceiveStatus(msg);
        }
    }

    @Override
    protected void onResume() {
        BmobIM.getInstance().addMessageListHandler(this);
        super.onResume();
    }
}
