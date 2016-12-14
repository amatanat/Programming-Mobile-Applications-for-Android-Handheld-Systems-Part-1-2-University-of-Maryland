package threading.com.example.simplethreading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG ="MainActivity";

    Button mLoadIconButton;
    Button mOtherButton;
    ImageView mImageView;
    private Bitmap mBitmap;
    private int mSleep = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadIconButton = (Button) findViewById(R.id.load_icon);
        mOtherButton = (Button) findViewById(R.id.other);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mOtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "I'm here.", Toast.LENGTH_SHORT).show();
            }
        });

        mLoadIconButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               loadIcon();
            }
        });
    }

    private void loadIcon(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(mSleep);
                }catch (Exception e){
                    Log.e(TAG,e.toString());
                }

                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_icon);
                mImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(mBitmap);
                        Log.i(TAG,"inside post()");
                    }
                });
            }
        }).start();
    }
}
