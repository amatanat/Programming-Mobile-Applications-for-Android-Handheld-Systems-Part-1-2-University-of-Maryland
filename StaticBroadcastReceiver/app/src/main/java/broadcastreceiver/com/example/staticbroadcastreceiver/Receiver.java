package broadcastreceiver.com.example.staticbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    private final String TAG  = "Receiver";

    @Override
    public void onReceive(Context context, Intent intent){

        Log.i(TAG,"BroadcastReceived");
        Toast.makeText(context, "INTENT RECEIVED by Receiver", Toast.LENGTH_LONG).show();
    }
}
