package com.example.project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.ParserConfigurationException;

import static android.content.Context.LOCATION_SERVICE;

/**
 *
 */
public class PageOfMyDanger extends Fragment implements OnMapReadyCallback {

    Button startButton, finishButton;
    Button findRouteButton;
    DialogOfSearch dialog;
    private Place startPlace, finishPlace;  // 검색한 결과 Place 객체
    //private UserLoc userLoc;
    private ArrayList<Patient> patient;
    private ArrayList<VisitPlace> nearPlaces; // 경로 주변 확진자
    private ArrayList<Place> visitPlaceList; // 출력 결과 경로 경유지
    private ArrayList<Place> routeList; // 입력받은 출발 도착
    private int danger; // 위험도

    private ArrayList<SearchPath> searchResultPath;

    public GoogleMap mMap;

    private Marker userPoint;
    private ArrayList<Marker> nearMaker;

    private LatLng myLatLng;
    private MapView mapView;
    private CalRoute cl;
    private ResultCallbackListener listener;

    public PageOfMyDanger() throws InterruptedException {
        //this.userLoc=new UserLoc();
        this.patient =new ArrayList<Patient>();
        this.nearPlaces =new ArrayList<VisitPlace>();
        this.searchResultPath = new ArrayList<SearchPath>();
        this.visitPlaceList = new ArrayList<Place>();
        this.routeList =new ArrayList<Place>();
        this.danger=0;
        this.startPlace = new Place(null,0,0);
        this.finishPlace= new Place(null,0,0);
        this.searchResultPath =new ArrayList<SearchPath>();
        this.cl=new CalRoute(getContext(),null,null);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_danger, container, false);
        startButton = v.findViewById(R.id.starting_point_Button);
        finishButton = v.findViewById(R.id.finish_point_Button);
        findRouteButton = v.findViewById(R.id.search_route_Button);

        // 출발지를 누르면 다이얼로그가 생성됨
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new DialogOfSearch(getContext());
                dialog.setSearchDialogListener(new DialogOfSearch.SearchDialogListener() {
                    @Override
                    public void onOKCliked(Place place) {
                        // 다이얼로그에서 결과 Place 객체 받아서 저장 (DialogOfSearch)
                        if(place !=null){
                            startPlace = place;
                            startButton.setText(startPlace.get_placeAddress());
                        }
                        else{
                            Toast.makeText(getContext(), "장소가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        // 도착지를 누르면 다이얼로그가 생성됨
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new DialogOfSearch(getContext());
                dialog.setSearchDialogListener(new DialogOfSearch.SearchDialogListener() {
                    @Override
                    public void onOKCliked(Place place) {
                        // 다이얼로그에서 결과 Place 객체 받아서 저장 (DialogOfSearch)
                        if(place !=null){
                            finishPlace = place;
                            finishButton.setText(finishPlace.get_placeAddress());
                            if(startPlace != null && finishPlace != null){
                                //동선 검색 기능
                                try {
                                    cl = new CalRoute(getContext(),startPlace,finishPlace);
                                    listener=cl.calRoute1();
                                    searchResultPath=listener.getResultPath();
                                    System.out.println("버튼 안에서"+searchResultPath.size());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{
                            Toast.makeText(getContext(), "장소가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        try {
            SearchRouteButtonAction();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapView = v.findViewById(R.id.danger_MapView);
        mapView.getMapAsync(this);
        return v;
    }
    /*경로 검색 버튼 누르면 실행되는 함수*/
    void SearchRouteButtonAction() throws InterruptedException {
        findRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startPlace.get_placeAddress() != null && finishPlace.get_placeAddress() != null){
                    try {
                        listener=cl.calRoute1();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        printPath(startPlace,finishPlace);
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), "출발지와 도착지를 입력했는지 확인하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        //LocPermission(getActivity(),getContext());
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

    public void RefreshMarker() {
        System.out.print("5");
        this.userPoint.remove();
        this.myLatLng = new LatLng(UserLoc.getUserPlace().get_placeX(), UserLoc.getUserPlace().get_placeY());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(this.myLatLng);
        markerOptions.title("사용자");
        markerOptions.snippet("현재 위치 GPS");
        this.userPoint = this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.myLatLng, 15));
    }

    public void calNearPlace() {
        this.nearPlaces.clear();
        for (int a = 0; a < this.patient.size(); a++) {
            for (int b = 0; b < this.patient.get(a).getVisitPlaceList().size(); b++) {
                for (int v = 0;  v<this.visitPlaceList.size();v++) {
                    if (this.patient.get(a).getVisitPlaceList().get(b).
                            Distance(this.visitPlaceList.get(v).get_placeX(),
                                    this.visitPlaceList.get(v).get_placeY(), "kilometer") <= 1) {
                        this.nearPlaces.add(this.patient.get(a).getVisitPlaceList().get(b));
                    }
                }
            }
        }
    }//반경 1km이내 확진자 동선

    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            RefreshMarker();
            calNearPlace();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

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


    public void printPath(Place startPlace,Place desPlace) throws ParserConfigurationException, SAXException, IOException {
        try {
            searchResultPath=cl.calRoute1().getResultPath();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("사이즈: "+searchResultPath.size());
        for(int i =0; i < searchResultPath.size();i++){
            SearchPath path = this.searchResultPath.get(i);
            ExtendNode exNode = path.getInfo();
            SubPath subPath=null;
            if (path.getPathType()==1){
                System.out.println("지하철만 이용");
            }
            else if(path.getPathType()==2)
            {
                System.out.println("버스만 이용");
            }
            else if(path.getPathType()==3){
                System.out.println("지하철+버스 이용");
            }

            System.out.println("총 요금: "+exNode.getPayment());
            System.out.println("총 이동 거리: "+exNode.getTotalDistance());
            System.out.println("출발 지점: "+startPlace.get_placeAddress());
            System.out.println("도착 지점: "+desPlace.get_placeAddress());
            System.out.println("최초 출발 역/정류장: "+exNode.getFirstStartStation());
            System.out.println("최종 도착 역/정류장: "+exNode.getLastEndStation());

            for(int a =0; a<path.getSubPaths().size();a++){
                subPath=path.getSubPaths().get(a);
                System.out.println("이동 거리: "+subPath.getDistance()+"km");
                System.out.println("이동 시간: "+subPath.getSectionTime());
                if(subPath.getTrafficType()==1){
                    System.out.println("이동 수단: 지하철");
                    System.out.println("이동 정거장 수: "+subPath.getStationCount());
                    System.out.println("승차 정류장: "+subPath.getStartStation().get_placeAddress());
                    System.out.println("하차 정류장: "+subPath.getEndStation().get_placeAddress());
                    System.out.println("이동 방면: "+subPath.getWay());
                    if(subPath.getWayCode() ==1 ){
                        System.out.println("상행");
                    }
                    else{
                        System.out.println("하행");
                    }
                    System.out.println("지하철 환승 위치: "+subPath.getDoor());
                    if(subPath.getStartExitNo()!=null){
                        System.out.println("지하철 입구: "+subPath.getStartExitNo().get_placeAddress());
                    }
                    if(subPath.getEndExitNo()!=null){
                        System.out.println("지하철 출구: "+subPath.getEndExitNo().get_placeAddress());
                    }
                    for(int sub = 0; sub < subPath.getLaneList().size();sub++){
                        Lane temp = subPath.getLaneList().get(sub);
                        System.out.println("지하철 노선: "+temp.getName());
                    }
                }
                else if(subPath.getTrafficType()==2){
                    System.out.println("이동 수단: 버스");
                    System.out.println("이동 정거장 수: "+subPath.getStationCount());
                    System.out.println("승차 정류장: "+subPath.getStartStation().get_placeAddress());
                    System.out.println("하차 정류장: "+subPath.getEndStation().get_placeAddress());
                    for(int sub = 0; sub < subPath.getLaneList().size();sub++){
                        Lane temp = subPath.getLaneList().get(sub);
                        System.out.println("버스 번호: "+temp.getName());
                        if(temp.getSubwayCodeORtype() == 1){
                            System.out.println("일반");
                        }
                        else if(temp.getSubwayCodeORtype() == 2){
                            System.out.println("좌석");
                        }
                        else if(temp.getSubwayCodeORtype() == 3){
                            System.out.println("마을 버스");
                        }
                        else if(temp.getSubwayCodeORtype() == 4){
                            System.out.println("직행 좌석");
                        }
                        else if(temp.getSubwayCodeORtype() == 5){
                            System.out.println("공항 버스");
                        }
                        else if(temp.getSubwayCodeORtype() == 6){
                            System.out.println("간선 급행");
                        }
                        else if(temp.getSubwayCodeORtype() == 10){
                            System.out.println("외곽 ");
                        }
                        else if(temp.getSubwayCodeORtype() == 11){
                            System.out.println("간선");
                        }
                        else if(temp.getSubwayCodeORtype() == 12){
                            System.out.println("지선");
                        }
                        else if(temp.getSubwayCodeORtype() == 13){
                            System.out.println("순환");
                        }
                        else if(temp.getSubwayCodeORtype() == 14){
                            System.out.println("광역");
                        }
                        else if(temp.getSubwayCodeORtype() == 15){
                            System.out.println("급행");
                        }
                        else if(temp.getSubwayCodeORtype() == 20){
                            System.out.println("농어촌 버스");
                        }
                        else if(temp.getSubwayCodeORtype() == 21){
                            System.out.println("제주도 시외형 버스");
                        }
                        else if(temp.getSubwayCodeORtype() == 22){
                            System.out.println("경기도 시외형 버스");
                        }
                        else if(temp.getSubwayCodeORtype() == 26){
                            System.out.println("급행 간선");
                        }
                    }
                }
                else if(subPath.getTrafficType()==3){
                    System.out.println("이동 수단: 도보");
                }

            }
        }

    }// 여러개의 path중 하나의 path를 출력할 함수

    public void calDanger(){
        if (this.nearPlaces.size() >=50){
            this.danger=5;
        }//초고도 위험
        else if(this.nearPlaces.size() >=25){
            this.danger=4;
        }// 고위험
        else if(this.nearPlaces.size() >=10){
            this.danger=3;
        }// 위험
        else if(this.nearPlaces.size() >=1){
            this.danger=2;
        }// 주의
        else{
            this.danger=1;
        }//안전
    }
    public int printDanger(ArrayList<SearchPath> searchPath) {
        for(int i=0; i < searchPath.size();i++){
            for (int j=0;j<searchPath.get(i).getSubPaths().size();j++){
                this.visitPlaceList.add(searchPath.get(i).getSubPaths().get(j).getStartStation());
                this.visitPlaceList.add(searchPath.get(i).getSubPaths().get(j).getEndStation());
            }
        } // 경로 상 모든 들리는 장소를 경유지 리스트에 넣어줌
        return 0;
    }

}
