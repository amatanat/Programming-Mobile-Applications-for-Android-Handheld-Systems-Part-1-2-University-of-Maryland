package sensors.com.example.sensoraccelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView mTextView1, mTextView2, mTextView3,
                     mLowPass1, mLowPass2,mLowPass3,
                     mHighPass1, mHighPass2, mHighPass3;


    private float[] mGravity;
    private float[] mAccel;

    SensorManager mSensorManager;
    Sensor mSensor;

    private final float mAlpha = 0.8f;
    private static final int THRESHOLD = 400;
    private long mPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView1 = (TextView) findViewById(R.id.x_axis);
        mTextView2 = (TextView) findViewById(R.id.y_axis);
        mTextView3 = (TextView) findViewById(R.id.z_axis);

        mLowPass1 = (TextView) findViewById(R.id.low_pass_x);
        mLowPass2 = (TextView) findViewById(R.id.low_pass_y);
        mLowPass3 = (TextView) findViewById(R.id.low_pass_z);

        mHighPass1 = (TextView) findViewById(R.id.high_pass_x);
        mHighPass2 = (TextView) findViewById(R.id.high_pass_y);
        mHighPass3 = (TextView) findViewById(R.id.high_pass_z);

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

                mGravity = new float[3];
                mAccel = new float[3];

                mGravity[0] = lowPass(xAxis, mGravity[0]);
                mGravity[1] = lowPass(yAxis, mGravity[1]);
                mGravity[2] = lowPass(zAxis, mGravity[2]);

                mAccel[0] = highPass(xAxis, mGravity[0]);
                mAccel[1] = highPass(yAxis, mGravity[1]);
                mAccel[2] = highPass(zAxis, mGravity[2]);

                mTextView1.setText(String.valueOf(xAxis));
                mTextView2.setText(String.valueOf(yAxis));
                mTextView3.setText(String.valueOf(zAxis));

                mLowPass1.setText(String.valueOf(mGravity[0]));
                mLowPass2.setText(String.valueOf(mGravity[1]));
                mLowPass3.setText(String.valueOf(mGravity[2]));

                mHighPass1.setText(String.valueOf(mAccel[0]));
                mHighPass2.setText(String.valueOf(mAccel[1]));
                mHighPass3.setText(String.valueOf(mAccel[2]));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int type){
        //
    }

    private float lowPass(float current, float gravity){
        return gravity * mAlpha + current * (1 - mAlpha);
    }

    private float highPass(float current, float gravity){
        return current - gravity;
    }
}
