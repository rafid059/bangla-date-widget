package io.sonnet.bangladatewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import java.util.Calendar;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static int[] ids;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        // There may be multiple widgets active, so update all of them
        ids = appWidgetIds;

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,  AlarmBroadcastReceiver.class);
        intent.putExtra("ids", ids);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar current = Calendar.getInstance();
        Calendar nexMin = (Calendar) current.clone();
        nexMin.set(Calendar.MINUTE, nexMin.get(Calendar.MINUTE)+1);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            // bannerAd alarm
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nexMin.getTimeInMillis()-current.getTimeInMillis(), 60000, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, nexMin.getTimeInMillis() - current.getTimeInMillis(), pendingIntent);
        }


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, Calendar.getInstance().get(Calendar.MINUTE)+"");


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

