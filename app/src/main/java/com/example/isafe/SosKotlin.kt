package com.example.isafe

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.isafe.dashboard.SosModule

/**
 * Implementation of App Widget functionality.
 */
class SosKotlin : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {



    // Create a pending Intent for Activity 1

    val i1 : PendingIntent = Intent(context,SosModule::class.java).let { intent ->

        PendingIntent.getActivity(context, 0, intent, 0)  }

    val views = RemoteViews(context.packageName, R.layout.sos_kotlin)

        // Button 1 onClick Function

        .apply{setOnClickPendingIntent(R.id.sos_widget_button,i1)}



    // Instruct the widget manager to update the widget

    appWidgetManager.updateAppWidget(appWidgetId, views)
}