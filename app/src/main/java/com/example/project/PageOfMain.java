package com.example.project;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
    private MapView mapView;
    private ArrayList<Place> near_places;
    private UserLoc userLoc;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);
        // 구글 맵 연결

        /*UserLoc 클래스와 연결*/
        userLoc = new UserLoc();            // 디폴트 좌표
        userLoc.LocBy_gps(getContext());    // gps 좌표; 새로고침을 해야 gps 좌표가 뜸

        /*맵 컴포넌트 연결*/
        mapView = v.findViewById(R.id.user_main_Map);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(mapView != null){
            mapView.onCreate(savedInstanceState);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng userPoint = new LatLng(this.userLoc.getUserPlace().get_placeX(), this.userLoc.getUserPlace().get_placeY());
        googleMap.addMarker(new MarkerOptions().position(userPoint).title("현 위치"));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userPoint);
        markerOptions.title("사용자");
        markerOptions.snippet("현재 위치 GPS");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPoint,15));
    } // 유저 현위치에 마커 추가

    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }


    @Override
    public void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
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