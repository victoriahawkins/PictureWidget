package org.boxtree.victoria.picturewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * A Widget Provider for updating pictures
 */

public class ChangeWidgetProvider extends AppWidgetProvider {

    private static int[] imageArray = new int[]{R.mipmap.smiley_default, R.mipmap.smiley_star, R.mipmap.smiley_frown, R.mipmap.smiley_calm};

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // pick a random image
        remoteViews.setImageViewResource(R.id.imageView, getRandomImage());

        // create a pending intent with onclick event for button to call back to provider to update widget
        Intent intent = new Intent(context, ChangeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.changeButton, pendingIntent);

        // update the remote widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    public static int getRandomImage() {
        int rnd = new Random().nextInt(imageArray.length);
        return imageArray[rnd];
    }

    /* this is called once the broadcast is sent to update the image */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // pick a random image
        remoteViews.setImageViewResource(R.id.imageView, getRandomImage());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), ChangeWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        for (int widgetid : appWidgetIds) {
            appWidgetManager.updateAppWidget(widgetid, remoteViews);
        }
    }
}
