package org.dimamir999.testapp.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dimamir999.testapp.model.PhotoWithGeoTag;
import org.dimamir999.testapp.db.PhotoWithGeoTagDAO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dimamir999 on 17.01.16.
 */
public class PhotoLoadService {

    private PhotoWithGeoTagDAO dao;

    public PhotoLoadService(Context context) {
        dao = new PhotoWithGeoTagDAO(context);
    }

    public ArrayList<PhotoWithGeoTag> getUserPhotos(){
        ArrayList<PhotoWithGeoTag> userPhotos = new ArrayList<PhotoWithGeoTag>();
        //get photos from db
        return  userPhotos;
    }

    public ArrayList<MarkerOptions> makeMarkerOptionsFromList(ArrayList<PhotoWithGeoTag> userPhotos){
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        for(PhotoWithGeoTag userPhoto : userPhotos){
            markers.add(new MarkerOptions().position(new LatLng(userPhoto.getLatitude(), userPhoto.getLongitude())).icon(
                    BitmapDescriptorFactory.fromBitmap(userPhoto.getPhoto())));
        }
        return markers;
    }

    public void addNewPhoto(Bitmap photo, GoogleMap map){
        Location location = map.getMyLocation();
        Date currentDate = new Date(System.currentTimeMillis());
        PhotoWithGeoTag userPhoto = new PhotoWithGeoTag(photo, location.getLongitude(), location.getLatitude(),
                currentDate);
        //insert userPhoto to db
    }
}
