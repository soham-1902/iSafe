package com.example.isafe;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.isafe.dashboard.SosModule;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {


    public static String WIDGET_BUTTON = "com.example.isafe.IB_CLICKED";

    Intent i;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Toast.makeText(context, "Inside onUpdate", Toast.LENGTH_SHORT).show();

        for (int appWidgetId : appWidgetIds) {
            Toast.makeText(context, "Inside onUpdate", Toast.LENGTH_SHORT).show();

            i = new Intent(WIDGET_BUTTON);
            i.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent p = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            remoteViews.setOnClickPendingIntent(R.id.widgetSosIb, p);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Inside onReceive", Toast.LENGTH_SHORT).show();
        super.onReceive(context, intent);
        if (WIDGET_BUTTON.equals(intent.getAction())) {
            Intent intn = new Intent (context, SosModule.class);
            intn.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity (intn);
        }

    }
}
