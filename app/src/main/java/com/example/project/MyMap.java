package com.example.project;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.*;

/**
 * 
 */
public class MyMap extends Map  {

    /**
     * Default constructor
     */
    public MyMap(ArrayList<Place> nearPlaces) {
        this.userLocation.LocPermission();
        this.userLocation.LocBy_gps();
        this.nearPlaces=nearPlaces;
    }
    private GoogleMap mMap;
    private ArrayList<Place> nearPlaces;
    private Place centerPlace;
    private UserLoc userLocation;
    private Void user_markers;

    public void print_marker() {
        // TODO implement here
    }

    public void input_marker() {
        // TODO implement here
    }

    @Override
    public ArrayList<Place> search_nearPlaces(ArrayList<Patient> patientsList) {
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng userPoint = new LatLng(this.userLocation.getUserPlace().get_placeX(), this.userLocation.getUserPlace().get_placeY());
        mMap.addMarker(new MarkerOptions().position(userPoint)
                .title("현 위치"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPoint));
    } // 유저 현위치에 마커 추가

    /**
     * 
     */
}
