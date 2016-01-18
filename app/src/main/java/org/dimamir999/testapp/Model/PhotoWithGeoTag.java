package org.dimamir999.testapp.Model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.dimamir999.testapp.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dimamir999 on 17.01.16.
 */
public class PhotoWithGeoTag {

    private Bitmap photo;
    private double longitude;
    private double latitude;
    private Date date;

    public PhotoWithGeoTag(Bitmap photo, double longitude, double latitude, Date date) {
        this.photo = photo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public MarkerOptions makeMarkerOption(Activity activity){
        ImageView imageView = (ImageView) activity.findViewById(R.id.photo);
        imageView.setImageBitmap(photo);
        return new MarkerOptions().position(new LatLng(latitude, longitude)).icon(
                BitmapDescriptorFactory.fromBitmap(photo));
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
