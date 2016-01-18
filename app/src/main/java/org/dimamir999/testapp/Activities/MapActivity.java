package org.dimamir999.testapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.dimamir999.testapp.R;
import org.dimamir999.testapp.Services.LocationControlService;
import org.dimamir999.testapp.Services.PhotoLoadService;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private PhotoLoadService loadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        loadService = new PhotoLoadService();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        startService(new Intent(this, LocationControlService.class));
        Log.v("dimamir999", "MapActivity created");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//        for(PhotoWithGeoTag userPhoto : loadService.getUserPhotos()){
//
//        }

        //get my geo location
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }

        //add marker my location
        map.addMarker(new MarkerOptions().position(new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude())).title("my location"));

        LatLng sydney = new LatLng(-34, 151);
        LatLng test1 = new LatLng(-34, 150);
        LatLng test2 = new LatLng(-33, 150);


        map.addMarker(new MarkerOptions().position(sydney).title("sydney"));
        map.addMarker(new MarkerOptions().position(test1).title("test1"));
        map.addMarker(new MarkerOptions().position(test2).title("test2"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
