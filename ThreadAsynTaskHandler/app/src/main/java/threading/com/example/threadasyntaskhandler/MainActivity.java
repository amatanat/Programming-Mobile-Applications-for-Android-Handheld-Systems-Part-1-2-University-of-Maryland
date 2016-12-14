package threading.com.example.threadasyntaskhandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final String TAG ="MainActivity";

    Bitmap mBitmap;
    ImageView mImageView1;
    ImageView mImageView2;
    ImageView mImageView3;
    Button mButton1;
    Button mButton2;
    Button mButton3;

    private final int mSleep = 500;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton1 = (Button) findViewById(R.id.arrowLeft);
        mButton2 = (Button) findViewById(R.id.arrowBack);
        mButton3 = (Button) findViewById(R.id.iconMan);

        mImageView1 =(ImageView) findViewById(R.id.imageViewFirst);
        mImageView2 =(ImageView) findViewById(R.id.imageViewSecond);
        mImageView3=(ImageView) findViewById(R.id.imageViewThird);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Loading 1..", Toast.LENGTH_SHORT).show();
                mButton1.setText("Clicked");
                 showImage();
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Loading 2..", Toast.LENGTH_SHORT).show();
                 mButton2.setText("Clicked");
                 new Thread(new showImage2(R.drawable.arrow_back)).start();
            }
        });


        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Loading 3..", Toast.LENGTH_SHORT).show();
                mButton3.setText("Clicked");
                new showImage3().execute(R.drawable.icon_man);
            }
        });
    }

    private void showImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep();
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow_right);
                mImageView1.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageView1.setImageBitmap(mBitmap);
                        Log.i(TAG,"inside post()");
                    }
                });
            }
        }).start();
    }

    private class showImage2 implements Runnable{
        int id;

        showImage2(int id){
            this.id = id;
        }


        public void run(){

            sleep();
            mBitmap = BitmapFactory.decodeResource(getResources(), id);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mImageView2.setImageBitmap(mBitmap);
                }
            });
        }
    }

    class showImage3 extends AsyncTask<Integer,Integer, Bitmap> {


        @Override
        protected Bitmap doInBackground(Integer... resID){
            Bitmap result = BitmapFactory.decodeResource(getResources(), resID[0]);
            return result;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap){
            sleep();
            mImageView3.setImageBitmap(bitmap);
        }

    }


    private void sleep(){
        try{
            Thread.sleep(mSleep);
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
}
