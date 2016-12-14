package broadcastreceiver.com.example.staticbroadcastreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG= "MainActivity";

    private final String CUSTOM_INTENT = "broadcastreceiver.com.example.show_toast";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.send_intent);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent(CUSTOM_INTENT));
                Log.i(TAG, "Broadcast send");
            }
        });
    }
}
