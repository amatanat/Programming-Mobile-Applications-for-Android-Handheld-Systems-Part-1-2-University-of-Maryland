package threading.com.example.threadasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
                new LoadIcon().execute(R.drawable.android_icon);
            }
        });
    }

    class LoadIcon extends AsyncTask<Integer,Integer, Bitmap>{

        @Override
        protected void onPreExecute(){
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Integer... resID){
            Bitmap result = BitmapFactory.decodeResource(getResources(), resID[0]);

            for (int i = 1; i < 11; i++){
                sleep();
                publishProgress(i * 10);
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mImageView.setImageBitmap(bitmap);
        }

        private void sleep(){
            try{
                Thread.sleep(mSleep);
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }

        }
    }
}
