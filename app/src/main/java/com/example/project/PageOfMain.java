package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.*;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_DENIED;

/**
 *
 */
public class PageOfMain extends Fragment implements OnMapReadyCallback {

    public GoogleMap mMap;

    private Marker userPoint;
    private ArrayList<Marker> nearMaker;

    private LatLng myLatLng;
    private MapView mapView;

    private ArrayList<Patient> patient;
    private ArrayList<VisitPlace> nearPlaces;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;


    public PageOfMain() {
        this.nearPlaces = new ArrayList<VisitPlace>();
        this.patient = new ArrayList<Patient>();
        this.nearMaker = new ArrayList<Marker>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);
        /*맵 컴포넌트 연결*/
        mapView = v.findViewById(R.id.user_main_Map);
        mapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.myLatLng = new LatLng(UserLoc.getUserPlace().get_placeX(), UserLoc.getUserPlace().get_placeY());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(this.myLatLng);
        markerOptions.title("사용자");
        markerOptions.snippet("현재 위치 GPS");
        this.userPoint = this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.myLatLng, 15));
        GPSListener gpsListener = new GPSListener();
        UserLoc.LocBy_gps(getContext(),gpsListener);
    } // 유저 현위치에 마커 추가
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
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

    public void RefreshMarker() {
        this.userPoint.remove();
        this.myLatLng = new LatLng(UserLoc.getUserPlace().get_placeX(), UserLoc.getUserPlace().get_placeY());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(this.myLatLng);
        markerOptions.title("사용자");
        markerOptions.snippet("현재 위치 GPS");
        this.userPoint = this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.myLatLng, 15));
    }

    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RefreshMarker();
                        calNearPlace();
                        addNearPlaceMaker();
                    }
                });
            }
        }
        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    public void calNearPlace() {
        this.nearPlaces.clear();
        for (int a = 0; a < this.patient.size(); a++) {
            for (int b = 0; b < this.patient.get(a).getVisitPlaceList().size(); b++) {
                if (this.patient.get(a).getVisitPlaceList().get(b).
                        Distance(UserLoc.getUserPlace().get_placeX(),
                                UserLoc.getUserPlace().get_placeX(), "kilometer") <= 1) {
                    this.nearPlaces.add(this.patient.get(a).getVisitPlaceList().get(b));
                }
            }
        }
    }//반경 1km이내 확진자 동선

    public void addNearPlaceMaker() {
        for (int a = 0; a < this.nearMaker.size(); a++) {
            this.nearMaker.get(a).remove();
        }
        if (this.nearMaker.size() != 0) {
            this.nearMaker.clear();
        }
        for (int a = 0; a < this.nearPlaces.size(); a++) {
            LatLng nearLatlng = new LatLng(this.nearPlaces.get(a).getVisitPlace().get_placeX(), this.nearPlaces.get(a).getVisitPlace().get_placeY());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(nearLatlng);
            markerOptions.title("확진자");
            SimpleDateFormat transFormat = new SimpleDateFormat("MM월dd일");
            markerOptions.snippet(transFormat.format(this.nearPlaces.get(a).getVisitDate()) + this.nearPlaces.get(a).getVisitPlace().get_placeAddress());
            this.nearMaker.add(this.mMap.addMarker(markerOptions));
        }
    } //주변 확진자 마커 추가
}

