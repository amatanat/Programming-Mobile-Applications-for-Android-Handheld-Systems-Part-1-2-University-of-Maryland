package notification.com.example.notificationareanotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private static final int NOTIFICATION_ID = 1;

    private final CharSequence mcontentText = "This is a content text";
    private final CharSequence mcontentTitle = "Content title";
    private final CharSequence mtickerText = "Ticker text";

    private Button mButton;

    private Intent mIntent ;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.show_notification);


        mIntent = new Intent(getApplicationContext(),NotificationActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPendingIntent = PendingIntent.getActivity(getApplicationContext(),0, mIntent, 0);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"mButton click");
                Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                        .setAutoCancel(true)
                        .setTicker(mtickerText)
                        .setContentTitle(mcontentTitle)
                        .setContentText(mcontentText)
                        .setContentIntent(mPendingIntent);
                Log.i(TAG,"notificationBuilder done");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Log.i(TAG,"notificationManager created");
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                Log.i(TAG,"notificationManager notify");
            }
        });
    }
}
