package selfie.com.example.dailyselfie;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ListActivity {

    CustomAdapter mCustomAdapter;

    AlarmManager mAlarmManager;
    Intent mIntent;
    PendingIntent mPendingIntent;

    ArrayList<Photo> mPhotos = new ArrayList<Photo>();

    private final long ONE_MIN = 60 * 1000L;

    private static final String PHOTO_TIME_FORMAT = "yyyyMMdd_HHmmss";

    private static final File PHOTO_STORAGE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


    private final String TAG = "MainActivity";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlarm();
        loadSelfies(mPhotos);
        final ListView mListView = getListView();
       // setContentView(R.layout.activity_main);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.i(TAG, "Selfie at position " + position + " clicked");
                Intent fullPhotoIntent = new Intent(Intent.ACTION_VIEW);
                Photo selected = (Photo) mCustomAdapter.getItem(position);
                fullPhotoIntent.setDataAndType(Uri.parse("file://" + selected.getImagePath()), "image/*");
                Log.i(TAG, "Intent data set to " + selected.getImageName());
                startActivity(fullPhotoIntent);
            }});
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Photo selfie = mPhotos.get(i);
                File selfieFile = new File(selfie.getImageName());
                Log.i(TAG, "selfieFile is: " + selfie);

                if (selfieFile.exists()) {
                    String toastMsg = selfieFile.delete() ? "Successfully deleted " + selfie.getImageName() : "Delete failed";
                    Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
                    mPhotos.remove(selfie);
                    return true;
                }
                return false;
            }});

        mCustomAdapter = new CustomAdapter(this);
        for (Photo photo : mPhotos) {
            mCustomAdapter.add(photo);
        }
       setListAdapter(mCustomAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                Log.i(TAG,"Camera clicked");
                takePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadSelfies(mPhotos);
        Log.i(TAG, "Reloaded selfies: " + mPhotos);
        mCustomAdapter.setList(mPhotos);
        mCustomAdapter.notifyDataSetChanged();
    }
    private void takePhoto() {
        Log.i(TAG, "Taking a new selfie");
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;

            try {
                imageFile = createImageFile();
                Log.i(TAG, "New selfie image file is " + imageFile.getAbsoluteFile());
            } catch (IOException e) {
                Log.e(TAG, "Error when capturing selfie", e);
            }

            if (imageFile != null) {
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

   /* private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(PHOTO_TIME_FORMAT).format(new Date());
        Log.i(TAG, "createImageFile: timeStamp: " + timeStamp);
        String imageName = "SELFIE_"  + timeStamp + "_";
        File image = File.createTempFile(imageName, ".jpg", PHOTO_STORAGE);
        Log.i(TAG, "Image file created: " + image);
        return image;
    }

    private class SelfiePhotoFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return file.getName().contains("SELFIE_");
        }
    }


    private Bitmap getThumbnail(String imagePath) {
        Log.i(TAG, "Getting selfie thumbnails");
        // Get the dimensions of the View
        int targetW = 100;
        int targetH = 100;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }
    private void loadSelfies(ArrayList<Photo> selfies){
        Log.i(TAG, "Loading selfies....");
        selfies.clear();
        SelfiePhotoFilter selfiePhotoFilter = new SelfiePhotoFilter();

        if (PHOTO_STORAGE.exists()) {
            File[] imageFiles = PHOTO_STORAGE.listFiles(selfiePhotoFilter);
            if (imageFiles != null) {
                for (File file : PHOTO_STORAGE.listFiles(selfiePhotoFilter)) {
                    selfies.add(new Photo(file.getName(), file.getAbsolutePath(),
                            getThumbnail(file.getAbsolutePath())));
                }
            }
        }
        Log.i(TAG, "Selfies are: " + selfies);
    }


    private void createAlarm(){
        Log.i(TAG, "createAlarm()");
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        mIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, mIntent, 0);

        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ONE_MIN,
                ONE_MIN,
                mPendingIntent);

    }
}
