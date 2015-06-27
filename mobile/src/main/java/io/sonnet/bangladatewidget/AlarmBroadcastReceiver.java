package io.sonnet.bangladatewidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sonnet on 6/27/15.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int []ids = intent.getIntArrayExtra("ids");
        // There may be multiple widgets active, so update all of them

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);


        for (int id : ids) {
            NewAppWidget.updateAppWidget(context, appWidgetManager, id);
        }

    }
}
