package com.example.project;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;


public class PageOfSelectedClinic extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView googleMap;

    private LatLng myLatLng;
    private Marker userPoint;

    private ArrayList<SelectedClinic> clinics;
    private ArrayList<Marker> clinicsMarker;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_clinics, container, false);
        googleMap = v.findViewById(R.id.user_clinic_Map);
        googleMap.getMapAsync(this);
        return v;
    }
    public PageOfSelectedClinic() {
        this.clinicsMarker=new ArrayList<Marker>();
        this.clinics = new ArrayList<SelectedClinic>();
        this.clinics = clinicAPIEntity.getClinicsList();
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
        GPSListener gpsListener =new GPSListener(this,mMap);
        UserLoc.LocBy_gps(getContext(),gpsListener);
    } // 유저 현위치에 마커 추가

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (googleMap != null) {
            googleMap.onCreate(savedInstanceState);
        }
    }

    public ArrayList<SelectedClinic> findClinic(){
        java.util.Map<Double, SelectedClinic> nearClinics = new LinkedHashMap<>();
        ArrayList<SelectedClinic> fiveNearClinics = new ArrayList<SelectedClinic>();
        for (int i = 0; i < this.clinics.size(); i++) {
            double dis = this.clinics.get(i).Distance(UserLoc.getUserPlace().get_placeX(),
                    UserLoc.getUserPlace().get_placeY(), "kilometer"); // 현재 위치와 병원과의 직선 거리
            nearClinics.put(dis,clinics.get(i));
        }
        Object[] mapKey = nearClinics.keySet().toArray();
        Arrays.sort(mapKey);
        for (Object nKey : mapKey)
        {
            fiveNearClinics.add(nearClinics.get(nKey));
            if(fiveNearClinics.size() >= 5){
                return fiveNearClinics;
            }
        }
        return fiveNearClinics;
    }

    public void addMarker(GoogleMap googleMap) throws IOException, SAXException, ParserConfigurationException {
        ArrayList<SelectedClinic> nearClinics = findClinic();
        for(int i =0; i <clinicsMarker.size();i++ ){
            this.clinicsMarker.get(i).remove();
        }
        for (int i = 0; i < nearClinics.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(nearClinics.get(i).getPlace().get_placeX(),
                    nearClinics.get(i).getPlace().get_placeY()));
            markerOptions.title(nearClinics.get(i).getName());
            markerOptions.snippet("주소: " + nearClinics.get(i).getPlace().get_placeAddress() +
                    "전화번호: " + nearClinics.get(i).getPhoneNum());
            clinicsMarker.add(googleMap.addMarker(markerOptions));
        }
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

    @Override
    public void onStart() {
        super.onStart();
        googleMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleMap.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        googleMap.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
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

    private class GPSListener implements LocationListener {

        PageOfSelectedClinic page;
        GoogleMap mMap;

        public GPSListener(PageOfSelectedClinic page,GoogleMap mMap){
            this.page=page;
            this.mMap=mMap;
        }

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    page.RefreshMarker();
                    try {
                        page.addMarker(mMap);
                    } catch (IOException | ParserConfigurationException | SAXException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}
