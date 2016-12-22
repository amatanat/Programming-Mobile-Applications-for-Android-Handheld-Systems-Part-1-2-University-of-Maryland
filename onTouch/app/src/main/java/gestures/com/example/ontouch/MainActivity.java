package gestures.com.example.ontouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MIN_DXDY = 2;
    final private static int MAX_TOUCHES = 10;

    final private static LinkedList<MarkerView> mInactiveMarkers = new LinkedList<>();
    final private static Map<Integer, MarkerView> mActiveMarkers = new HashMap<>();

    protected static final String TAG = "MainActivity";

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = (FrameLayout) findViewById(R.id.frame);

        initViews();

        mFrameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:{
                        int pointerIndex = event.getActionIndex();
                        int pointerID = event.getPointerId(pointerIndex);

                       MarkerView markerView = mInactiveMarkers.remove();

                        if (null != markerView){
                            mActiveMarkers.put(pointerID, markerView);

                            markerView.setXcoor(event.getX(pointerIndex));
                            markerView.setYcoor(event.getY(pointerIndex));

                            updateTouches(mActiveMarkers.size());

                            mFrameLayout.addView(markerView);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:{

                        int pointerIndex = event.getActionIndex();
                        int pointerID = event.getPointerId(pointerIndex);

                        MarkerView markerView = mActiveMarkers.remove(pointerID);

                        if (null != markerView){
                            mInactiveMarkers.add(markerView);
                            updateTouches(mActiveMarkers.size());

                            mFrameLayout.removeView(markerView);
                        }
                        break;
                    }

                    default:

                        Log.i(TAG, "unhandled action");
                }
                return true;
            }

            private void updateTouches(int numActive) {
                for (MarkerView marker : mActiveMarkers.values()) {
                    marker.setTouches(numActive);
                }
            }

        });

    }

    private void initViews() {
        for (int idx = 0; idx < MAX_TOUCHES; idx++) {
            mInactiveMarkers.add(new MarkerView(this, -1, -1));
        }
    }

    private class MarkerView extends View{
        private float mX, mY;
        final static private int MAX_SIZE = 400;
        private int mTouches = 0;
        final private Paint mPaint = new Paint();

        public MarkerView(Context context,float X, float Y){
            super(context);
            mX = X;
            mY = Y;
            mPaint.setStyle(Paint.Style.FILL);
            Random random = new Random();
            mPaint.setARGB(255, random.nextInt(256),random.nextInt(256), random.nextInt(256));

        }

        float getXcoor(){
            return mX;
        }

        float getYcoor(){
            return mY;
        }

        void setXcoor(float X){
            mX = X;
        }

        void setYcoor(float Y){
            mY = Y;
        }

        void setTouches(int touches){
            mTouches = touches;
        }

        @Override
        protected  void onDraw(Canvas canvas){
            canvas.drawCircle(mX, mY, MAX_SIZE/mTouches, mPaint);
        }
    }
}
