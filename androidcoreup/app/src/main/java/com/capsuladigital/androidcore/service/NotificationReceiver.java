package com.capsuladigital.androidcore.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Luciano on 11/06/2018.
 */

public class NotificationReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // cancel any further alarms
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.cancel(alarmIntent);
        completeWakefulIntent(intent);

        // start the GcmTaskService
        MyAppFirebaseMessagingService.showNotification(context);
    }
}