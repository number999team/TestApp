package org.dimamir999.testapp.services;

import android.graphics.Bitmap;

/**
 * Created by dimamir999 on 20.01.16.
 */
public class PhotoScaler {

    public Bitmap scale(Bitmap src, int maxPixelLength){
        int height = src.getHeight(), width = src.getWidth();
        int max = Math.max(height, width);
        if(max > maxPixelLength){
            double coef = (double)maxPixelLength / max;
            return Bitmap.createScaledBitmap(src, (int)(width * coef), (int)(height * coef), true);
        }else {
            return src;
        }
    }
}
