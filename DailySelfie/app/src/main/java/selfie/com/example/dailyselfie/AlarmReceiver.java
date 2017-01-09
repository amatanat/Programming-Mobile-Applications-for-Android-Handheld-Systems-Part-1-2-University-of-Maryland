package selfie.com.example.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {

    Intent mIntent;
    PendingIntent mPendingIntent;
    NotificationManager mNotificationManager;

    private final int MY_NOTIFICATION_ID = 1;


    public void onReceive(Context context, Intent intent){

        mIntent = new Intent(context, MainActivity.class);
        mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentText("Take a photo")
                .setContentTitle("Daily Selfie")
                .setContentIntent(mPendingIntent)
                .setSmallIcon(
                        android.R.drawable.stat_sys_warning)
                .setContentIntent(mPendingIntent);

         mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
    }
}
