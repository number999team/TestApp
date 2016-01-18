package org.dimamir999.testapp.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationControlService extends Service {

    private MyLocationListener locationListener;
    private LocationManager locationManager;
    private Location lastLocation;

    public LocationControlService() {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        //try to check location every 2 min and notify if location changed if dleta more than 100 meters
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120 * 1000, 50,
                this.locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 1, 10,
                this.locationListener);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000 * 10, 50,
                this.locationListener);
        Log.v("dimamir999", "LocationControlService started");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
            Log.v("dimamir999", "Current location " + location.getLatitude() + " " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.v("dimamir999", "Provider " + provider + " change status to " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.v("dimamir999", "Provider " + provider + " enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.v("dimamir999", "Provider " + provider + " disabled");
        }
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("dimamir999", "LocationControlService stoped");
        locationManager.removeUpdates(this.locationListener);
    }

    public Location getLastLocation() {
        return lastLocation;
    }
}
