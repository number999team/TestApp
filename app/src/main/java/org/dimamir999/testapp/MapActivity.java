package org.dimamir999.testapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                String path = marker.getSnippet();
                View snippetView = getLayoutInflater().inflate(R.layout.marker_snippet,null,false);
                return snippetView;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        Log.e("999", "map ready");
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng test1 = new LatLng(-34, 150);
        LatLng test2 = new LatLng(-33, 150);

        map.addMarker(new MarkerOptions().position(new LatLng(10, 10)).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        map.addMarker(new MarkerOptions().position(sydney).title("sydney"));
        map.addMarker(new MarkerOptions().position(test1).title("test1"));
        map.addMarker(new MarkerOptions().position(test2).title("test2"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
