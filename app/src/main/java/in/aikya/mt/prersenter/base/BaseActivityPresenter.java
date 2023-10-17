package in.aikya.mt.prersenter.base;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import in.aikya.mt.view.base.BaseActivityView;


public abstract class BaseActivityPresenter<AVIEW extends BaseActivityView> {

    private static final String ACTIVITY_BUNDLE_EXTRA = "ACTIVITY_BUNDLE_EXTRA";
    public static final String KEY_MOBILE_NUMBER = "KEY_MOBILE_NUMBER";

    private Context mContext;

    private AVIEW mView;

    /**
     * Attaches a view to its presenter class
     * @param view - View to be attached
     */
    public void attachView(AVIEW view){
        this.mView = view;
        this.mContext = view.getActivity();
        onAttached();
    }

    /**
     * Does nothing. other presenters extending it can {@link Override} this to listen {@link #attachView(BaseActivityView)} completion
     */
    protected abstract void onAttached();

    /**
     *
     * @return the current view attached to the presenter
     */
    public AVIEW getView(){
        return mView;
    }

    /**
     * A shortcut method to use {@link Activity} directly than VIEW.getActiviy()
     * @return {@link AppCompatActivity} - reference to current {@link Activity}
     */
    protected AppCompatActivity getActivity(){
        return mView.getActivity();
    }

    /**
     * A shortcut method to use {@link Context} directly than calling VIEW.getContext()
     * @return {@link Context}
     */
    public Context getContext(){
        return mContext;
    }

    /**
     * Method to show next scree/activity
     * @param activity - Class to be displayed - shoule extend activity
     * @param activityExtra - bunble to be passed through the intent
     */
    protected  <T extends Activity> void showNextScreenByFinish(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    protected  <T extends Activity> void showNextScreen(Class<T> activity, Bundle activityExtra){
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
    }

    /**
     *
     * @param activity
     * @param activityExtra
     * @param a1 - entering new activity animation
     * @param a2 - exiting old activity  animation
     * @param <T>
     */
    protected  <T extends Activity> void showAnimatedNextScreen(Class<T> activity, Bundle activityExtra,int a1,int a2){
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(ACTIVITY_BUNDLE_EXTRA, activityExtra);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(a1,a2);
    }

    /**
     * @return {@link Bundle} - which is passed through intent to current {@link Activity}
     */
    protected Bundle getBundle(){
        return getActivity().getIntent().getBundleExtra(ACTIVITY_BUNDLE_EXTRA);
    }

    /**
     * Shows general errors and warnings
     * @param errorMsgResId - String resource id of error message to be shown
     */
    protected void showError(int errorMsgResId){
        Toast.makeText(mContext, errorMsgResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows general errors and warnings
     * @param errorMsgResId - String error message to be shown
     */
    protected void showError(String errorMsgResId){
        Toast.makeText(mContext, errorMsgResId, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    protected abstract void showPopUp();

    /**
     * Changing the fragment from an Activity
     * @param activity
     * @param containerId
     * @param fragment
     * @param <T>
     */
    public <T extends FragmentActivity>   void replaceContentFragment(final T activity, final int containerId, final Fragment fragment) {
        if (activity != null && !activity.isFinishing() && fragment != null) {
            FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
           // beginTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
           // beginTransaction.replace(containerId, fragment).commitAllowingStateLoss();
        }
    }

    public String compressImage(Context mContext,String imageUri) {

        String filePath = getRealPathFromURI(mContext,imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(Context mContext,String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

}
