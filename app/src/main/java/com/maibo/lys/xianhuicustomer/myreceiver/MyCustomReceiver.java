package com.maibo.lys.xianhuicustomer.myreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.maibo.lys.xianhuicustomer.R;
import com.maibo.lys.xianhuicustomer.myactivity.BindAgentActivity;

import org.json.JSONObject;


public class MyCustomReceiver extends BroadcastReceiver {
    private static final String TAG = "MyCustomReceiver";
    //SharedPreferences sp;
    final int mNotificationId = 1314;
    @Override
    public void onReceive(Context context, Intent intent) {
//        sp=context.getSharedPreferences("baseDate",context.MODE_PRIVATE);
            try {
                String action = intent.getAction();
                String channel = intent.getExtras().getString("com.avos.avoscloud.Channel");
                //获取消息内容
                JSONObject json = new JSONObject(intent.getExtras().getString("com.avos.avoscloud.Data"));
                //1、解析消息体
                String jsonBody=intent.getExtras().getString("com.avos.avoscloud.Data");
                Log.e("jsonBody:",jsonBody);
                String notice_type="";
                String alert="";
                String notice_id="";
                String notice_time="";
                String agent_id="";
                if (!json.getString("notice_type").isEmpty())
                    notice_type=json.getString("notice_type");
                if (!json.getString("alert").isEmpty())
                    alert=json.getString("alert");
                if (!json.getString("notice_id").isEmpty())
                    notice_id=json.getString("notice_id");
                if (!json.getString("notice_time").isEmpty())
                    notice_time=json.getString("notice_time");
                if (!json.getString("agent_id").isEmpty())
                    agent_id=json.getString("agent_id");

                //根据不同的notice_type，发送不同通知
                if (notice_type.equals("register")){
                    //日报表
                    sendNotifacation(context, alert, notice_type,agent_id, BindAgentActivity.class);
                }

            } catch (Exception e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }

    private void sendNotifacation(Context context, String alert, String notice_type, String agent_id, Class clazz) {
        Intent resultIntent = new Intent(context, clazz);
        resultIntent.putExtra("agent_id",agent_id);
        resultIntent.putExtra("notice_type",notice_type);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("闲惠")
                        .setContentText(alert)
                        .setTicker("您有一条新消息!")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        NotificationManager mNotifyMgr = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(0, mBuilder.build());
    }
}
