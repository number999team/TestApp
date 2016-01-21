package org.dimamir999.testapp.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dimamir999.testapp.model.PhotoWithGeoTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by dimamir999 on 17.01.16.
 */
public class PhotoSaver {

    private final static int COMPRESSION_CONST = 85;

    private Context context;

    public PhotoSaver(Context context) {
        this.context = context;
    }


    public String savePhoto(Bitmap bitmap){
        String path = null;
        try {
            path = context.getExternalCacheDir().getPath() + "/" + System.currentTimeMillis() + ".jpg";
            File photoFile = new File(path);
            FileOutputStream writeStream = new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_CONST, writeStream);
            writeStream.flush();
            writeStream.close();
            Log.v("dimamir999", "photo saved");
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("dimamir999", "photo is not saved");
        }
        return path;
    }
//    public ArrayList<PhotoWithGeoTag> getUserPhotos(){
//        ArrayList<PhotoWithGeoTag> userPhotos = new ArrayList<PhotoWithGeoTag>();
//        //get photos from db
//        return  userPhotos;
//    }
//
//    public ArrayList<MarkerOptions> makeMarkerOptionsFromList(ArrayList<PhotoWithGeoTag> userPhotos){
//        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
//        for(PhotoWithGeoTag userPhoto : userPhotos){
//            markers.add(new MarkerOptions().position(new LatLng(userPhoto.getLatitude(), userPhoto.getLongitude())).icon(
//                    BitmapDescriptorFactory.fromBitmap(userPhoto.getPhoto())));
//        }
//        return markers;
//    }

}
