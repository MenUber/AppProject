package com.capsuladigital.androidcore.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.media.ExifInterface;

/**
 * Created by Luciano on 23/07/2018.
 */

public class ImageUtil {

    public static Bitmap fixOrientation(Bitmap bitmap, float orientation){
        Bitmap rotatedBitmap;
        switch ((int)orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;

    }

    public static float getOrientationInDegrees (float orientation){
        float degree;
        switch ((int)orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                degree=90.0f;
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                degree=180.0f;
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                degree=270.0f;
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                degree=0.0f;
        }
        return degree;

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}

