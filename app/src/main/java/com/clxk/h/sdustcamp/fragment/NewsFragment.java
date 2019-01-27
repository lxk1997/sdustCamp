package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.adapter.NewsMsgAdapter;
import com.clxk.h.sdustcamp.ui.ImMineFriendActivity;
import com.clxk.h.sdustcamp.ui.MainActivity;
import com.clxk.h.sdustcamp.ui.SearchUserActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobConversationType;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.v3.BmobUser;

/**
 * Created by Chook_lxk on 19/1/1
 *
 * 消息
 */
public class NewsFragment extends Fragment implements View.OnClickListener,MessageListHandler {

    private View currentView;

    private ImageButton ib_add_friend;
    private ImageButton ib_my_friend_list;

    private ListView lv_news_msg;
    public static NewsMsgAdapter msgAdapter;
    private List<BmobIMMessage> source;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_news,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * View事件
     */
    private void initEvent() {

        ib_my_friend_list.setOnClickListener(this);
        ib_add_friend.setOnClickListener(this);

        if(!isLogin()) return;
        getSource();
        msgAdapter = new NewsMsgAdapter(currentView.getContext(),R.id.lv_news_msg,source);
        lv_news_msg.setAdapter(msgAdapter);
    }

    /**
     * 获取所有消息内容
     */
    private void getSource() {
        List<BmobIMConversation> allMsg;
        allMsg = BmobIM.getInstance().loadAllConversation();
        if (allMsg == null) return;
        for(BmobIMConversation msg : allMsg) {
            BmobIMMessage textMsg = null;
            for(int i = 0; i < msg.getMessages().size(); i++) {
                Log.i("wulala","dsa"+msg.getMessages().get(i).getBmobIMUserInfo().getUserId());
                if(!msg.getMessages().get(i).getBmobIMUserInfo().getUserId().equals(BmobUser.getCurrentUser().getObjectId())) {
                    textMsg = msg.getMessages().get(i);
                    break;
                }
            }
            source.add(textMsg);
        }
    }

    /**
     * View初始化
     */
    private void initView() {

        ib_add_friend = currentView.findViewById(R.id.ib_add_friend);
        ib_my_friend_list = currentView.findViewById(R.id.ib_friend);

        lv_news_msg = currentView.findViewById(R.id.lv_news_msg);

        source = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.ib_add_friend:
                if(!isLogin()) break;
                intent = new Intent(currentView.getContext(), SearchUserActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
            case R.id.ib_friend:
                if(!isLogin()) break;
                intent = new Intent(currentView.getContext(), ImMineFriendActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
            case R.id.ib_market_header_back:
                intent = new Intent(currentView.getContext(), MainActivity.class);
                intent.putExtra("frId",R.id.ll_news);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
        }
    }

    public boolean isLogin() {
        if(BmobUser.getCurrentUser()== null || BmobUser.getCurrentUser().getUsername() == null) {
            return false;
        }
        return true;
    }

    /**
     * 自定义消息接收器
     * @param list
     */
    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        for(MessageEvent event: list) {
            addMsg2News(event);
        }
    }

    private void addMsg2News(MessageEvent event) {

        BmobIMMessage msg = event.getMessage();
        if(!msg.isTransient()) {
            if(msgAdapter.findMsg(msg.getConversationId()) < 0) {
                msgAdapter.add(msg);
                msgAdapter.notifyDataSetChanged();
            }
            else {
                getSource();
                msgAdapter = new NewsMsgAdapter(currentView.getContext(),R.id.lv_news_msg,source);
                lv_news_msg.setAdapter(msgAdapter);
            }
        }
    }

    @Override
    public void onResume() {
        BmobIM.getInstance().addMessageListHandler(this);
        super.onResume();
    }
}
