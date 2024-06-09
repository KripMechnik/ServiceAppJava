package com.example.serviceappjava.ui.reminder.reminder_tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.serviceappjava.data.ReminderItem;

public class AndroidReminderScheduler implements ReminderScheduler {
    private Context context;
    private AlarmManager alarmManager;

    public AndroidReminderScheduler(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void schedule(ReminderItem item) {
        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                item.getTime().getTimeInMillis(),
                null
        );
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("title", item.getTitle());
        alarmManager.setAlarmClock(
                alarmClockInfo,
                PendingIntent.getBroadcast(
                        context,
                        item.getId(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                )
        );
    }

    @Override
    public void cancel(ReminderItem item) {
        alarmManager.cancel(
                PendingIntent.getBroadcast(
                        context,
                        item.getId(),
                        new Intent(context, ReminderReceiver.class),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                )
        );
    }
}
