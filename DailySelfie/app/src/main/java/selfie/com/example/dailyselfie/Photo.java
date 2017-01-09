package selfie.com.example.dailyselfie;

import android.graphics.Bitmap;
import android.widget.ImageView;


public class Photo {
    Bitmap mBitmap;
    String mName;
    ImageView mImageView;
    String mImagePath;

    public Photo(String name, String imagePath, Bitmap bitmap){
        this.mBitmap = bitmap;
        this.mImagePath = imagePath;
        this.mName = name;
    }

    public String getImagePath(){
        return mImagePath;
    }

    public String getImageName(){
        return mName;
    }

    public ImageView getImageView(){
        return mImageView;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    @Override
    public String toString(){
        return "Name: " + mName + " ImagePath: " + mImagePath;

    }

}
