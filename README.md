# PictureWidget
Android widget sample project to update a picture


## Create a widget layout
In this case, a button and ImageView


## Create the Widget provider class and Extend AppWidgetProvider
Implement onUpdate which is called when the user adds the app widget. Do essential setup there.
```java
public class ChangeWidgetProvider extends AppWidgetProvider {

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // create a pending intent with onclick event for button to call back to provider to update widget
        Intent intent = new Intent(context, ChangeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.changeButton, pendingIntent);

        // update the remote widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

```

## Register the appwidget-provider
Create the appwidget-provider referencing the widget layout in a new xml file 

```xml
<?xml version="1.0" encoding="utf-8"?>
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:initialLayout="@layout/widget_layout"
    android:minHeight="210dp"
    android:minWidth="110dp"
    android:updatePeriodMillis="1000000"
    android:widgetCategory="home_screen">
</appwidget-provider>
```

and register it in the android manifest

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
        <receiver android:name="org.boxtree.victoria.picturewidget.ChangeWidgetProvider" >
            <intent-filter>
                <action android:name="android.appwidget.action.APPWIDGET_UPDATE"/>
            </intent-filter>
            <meta-data
                android:name="android.appwidget.provider"
                android:resource="@xml/new_app_widget_provider" />
        </receiver>
</manifest>
```

## Create the Broadcast Intent Receiver
Add the onReceive implementation to receive the pending intent from the button click
```java
public class ChangeWidgetProvider extends AppWidgetProvider {
...
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
````
