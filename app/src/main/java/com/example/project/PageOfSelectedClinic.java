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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;


import java.util.*;

class Pair implements Comparable<Pair> {
    double first;
    SelectedClinic second;

    Pair(double f, SelectedClinic s) {
        this.first = f;
        this.second = s;
    }


    public int compareTo(Pair p) {
        if (this.first < p.first) {
            return -1; // 오름차순
        }
        return 1; // 이미 this.first가 더 큰 것이 됐으므로, 1로 해준다. -1로
        // -1로 하면 결과가 이상하게 출력됨.
    }
}
/**
 * 
 */
public class PageOfSelectedClinic extends Fragment implements OnMapReadyCallback {

    private MapView googleMap;
    private ArrayList<SelectedClinic> clinics;
    private UserLoc userPlace;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_clinics, container, false);
        googleMap = v.findViewById(R.id.user_main_Map);
        googleMap.getMapAsync(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (googleMap != null) {
            googleMap.onCreate(savedInstanceState);
        }
    }

    public PriorityQueue<Pair> find_clinic() {
        ArrayList<SelectedClinic> near_clinics = null;
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
        for (int i = 0; i < this.clinics.size(); i++) {
            double dis = clinics.get(i).Distance(this.userPlace.getUserPlace().get_placeX(),
                    this.userPlace.getUserPlace().get_placeY(), "kilometer"); // 현재 위치와 병원과의 직선 거리
            pq.add(new Pair(dis,clinics.get(i)));
        }
        return pq;
    }// 가까운 거리의 클리닉 찾기

    public void addMarker(GoogleMap googleMap) {
        ArrayList<SelectedClinic> nearClinics =null;
        ArrayList<Double> nearDistance =null;
        PriorityQueue<Pair> pq = find_clinic();
        for(int t =0; t<5;t++){
            nearClinics.add(pq.poll().second);
            nearDistance.add(pq.poll().first);
        }
        for (int i = 0; i < nearClinics.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(nearClinics.get(i).getPlace().get_placeX(),
                    nearClinics.get(i).getPlace().get_placeY()));
            markerOptions.title(nearClinics.get(i).getName());
            markerOptions.snippet("주소: " + nearClinics.get(i).getPlace().get_placeAddress() +
                    "전화번호: " + nearClinics.get(i).getPhoneNum()+
                    "거리: " + nearDistance.get(i).toString());
            googleMap.addMarker(markerOptions);
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 13));
    } // 유저 현위치에 마커 추가

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

    /**
     * Default constructor
     */
    public PageOfSelectedClinic() {
    }

    /**
     *
     */


    public void print_UI() {
        // TODO implement here
    }

    /**
     * @return
     */

}
