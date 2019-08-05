package com.example.boris.simplewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String CLOCK_WIDGET_UPDATE = "com.example.boris.simplewidget.MyAppWidgetProvider.CLOCK_WIDGET_UPDATE";

    private PendingIntent createUpdateIntent(Context context){
        Intent intent = new Intent(CLOCK_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds){
            context.startService(new Intent(context, UpdateService.class));
        }

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createUpdateIntent(context));
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND,1);
        //update clock widget int 1 sec
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 500, createUpdateIntent(context));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //start service if clock widget is received
        if (CLOCK_WIDGET_UPDATE.equals(intent.getAction())){
            context.startService(new Intent(context, UpdateService.class));
        }


    }
}
