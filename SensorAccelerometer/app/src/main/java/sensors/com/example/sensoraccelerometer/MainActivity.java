package sensors.com.example.sensoraccelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView mTextView1;
    TextView mTextView2;
    TextView mTextView3;
    SensorManager mSensorManager;
    Sensor mSensor;

    private static final int THRESHOLD = 400;
    private long mPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = (TextView) findViewById(R.id.x_axis);
        mTextView2 = (TextView) findViewById(R.id.y_axis);
        mTextView3 = (TextView) findViewById(R.id.z_axis);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mSensor == null){
            finish();
        }

    }


    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        mPrevious = System.currentTimeMillis();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long mActual = System.currentTimeMillis();
            if (mActual - mPrevious > THRESHOLD){
                mPrevious = mActual;

                float xAxis = sensorEvent.values[0];
                float yAxis = sensorEvent.values[1];
                float zAxis = sensorEvent.values[2];

                mTextView1.setText(String.valueOf(xAxis));
                mTextView2.setText(String.valueOf(yAxis));
                mTextView3.setText(String.valueOf(zAxis));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int type){
        //
    }
}
