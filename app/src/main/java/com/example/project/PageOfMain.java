package com.example.project;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.*;

/**
 * 
 */
public class PageOfMain extends Fragment implements OnMapReadyCallback {
    private MapView googleMap;
    private Void my_place;
    private ArrayList<Place> near_places;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);
        googleMap = v.findViewById(R.id.user_main_Map);
        googleMap.getMapAsync(this);
        return v;
    }

    /**
     * Default constructor
     */
    public PageOfMain() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(googleMap != null){
            googleMap.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //LatLng userPoint = new LatLng(this.userLocation.getUserPlace().get_placeX(), this.userLocation.getUserPlace().get_placeY());
        //googleMap.addMarker(new MarkerOptions().position(userPoint).title("현 위치"));
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    } // 유저 현위치에 마커 추가

    @Override
    public void onStart(){
        super.onStart();
        googleMap.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        googleMap.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        googleMap.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
        googleMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleMap.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMap.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMap.onLowMemory();
    }



    public void print_UI() {
        // TODO implement here
    }

    /**
     *
     */
    public void print_map() {
        // TODO implement here
    }
}