package com.example.boris.simplewidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        RemoteViews updateViews = buildUpdate(this);
        ComponentName widget = new ComponentName(this, MyAppWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(widget, updateViews);
    }

    private RemoteViews buildUpdate(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
        views.setImageViewBitmap(R.id.imgTime, BuildUpdate( format1.format(calendar.getTime()), 100, context));
        views.setImageViewBitmap(R.id.imgDate, BuildUpdate(format2.format(calendar.getTime()), 25, context));

        return views;
    }

    public static Bitmap BuildUpdate(String txtTime, int size, Context context){
        Paint paint = new Paint();
        paint.setTextSize(size);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Light.ttf" );
        paint.setTypeface(typeface);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);

        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(txtTime) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(image);
        canvas.drawText(txtTime, 0, baseline, paint);

        return image;
    }


}
