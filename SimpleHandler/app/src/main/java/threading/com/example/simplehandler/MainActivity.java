package threading.com.example.simplehandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG ="MainActivity";

    Button mLoadIconButton;
    Button mOtherButton;
    ImageView mImageView;
    ProgressBar mProgressBar;
    private Bitmap mBitmap;
    private final Handler mHandler = new Handler();
    private int mSleep = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadIconButton = (Button) findViewById(R.id.load_icon);
        mOtherButton = (Button) findViewById(R.id.other);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mOtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "I'm here.", Toast.LENGTH_SHORT).show();
            }
        });

        mLoadIconButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new LoadIcon(R.drawable.android_icon)).start();
            }
        });
    }

    private class LoadIcon implements Runnable{
        int resID;

        LoadIcon(int resID){
            this.resID = resID;
        }

        public void run(){

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            mBitmap = BitmapFactory.decodeResource(getResources(), resID);

            for(int i = 1; i < 11; i++){
                sleep();
                final int step = i;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(step * 10);
                    }
                });
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(mBitmap);
                }
            });

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            });
        }
    }

    private void sleep(){
        try{
            Thread.sleep(mSleep);
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }
}
