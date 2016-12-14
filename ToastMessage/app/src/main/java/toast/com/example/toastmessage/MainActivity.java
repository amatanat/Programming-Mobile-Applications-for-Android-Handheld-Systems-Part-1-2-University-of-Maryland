package toast.com.example.toastmessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.showMessage);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());

                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.setDuration(Toast.LENGTH_SHORT);

                toast.setView(getLayoutInflater().inflate(R.layout.toast_view,
                        (ViewGroup) findViewById(R.id.custom_toast)));
                toast.show();

            }
        });

    }
}
