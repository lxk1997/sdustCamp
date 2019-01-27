package com.clxk.h.sdustcamp.receive;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.clxk.h.sdustcamp.utils.NotificationUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

import static android.content.Context.NOTIFICATION_SERVICE;

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class MyPushMessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            //收到消息进行一些处理：发送一个通知
            String jsonStr = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            String title = null;
            String content = null;
            //处理Json的数据，将其转化为正常string类型
            try {
                JSONObject jsonObject=new JSONObject(jsonStr);
                content=jsonObject.getString("alert");
                title=jsonObject.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            NotificationUtil notificationUtils = new NotificationUtil(context);
            notificationUtils.sendNotification(title, content);
//            NotificationManager manager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//            Notification notification= new Notification.Builder(context).
//                    setSmallIcon(R.drawable.ic_logo).//设置推送消息的图标
//                    setContentTitle(title) .//设置推送标题
//                    setContentText(content).build();//设置推送内容
//            manager.notify(1,notification);//设置通知栏
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
        }
    }

}