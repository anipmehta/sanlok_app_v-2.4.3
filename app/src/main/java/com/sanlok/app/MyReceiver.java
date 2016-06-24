package com.sanlok.app;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Anip Mehta on 10/5/2015.
 */
public class MyReceiver extends BroadcastReceiver
{
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(Context context, Intent intent)
    {
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        //Notification notification = intent.getParcelableExtra(NOTIFICATION);
        Intent service1 = new Intent(context, MyAlarmService.class);
        service1.putExtra(MyAlarmService.NOTIFICATION_ID,id);
        service1.putExtra(MyAlarmService.NOTIFICATION,notification);
        context.startService(service1);

    }
}