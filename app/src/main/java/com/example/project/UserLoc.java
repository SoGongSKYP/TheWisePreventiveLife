package com.example.project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserLoc extends AppCompatActivity {

    /**
     * Default constructor
     */
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;
    private Place userPlace;

    // 1: 사용자 위치정보 허용 상태, 2: 사용자 위치 정보 허용하지 않은 상태
    public UserLoc() {
        this.LocPermission();
        this.LocBy_gps();
    }

    public Place getUserPlace() {
        return this.userPlace;
    }

    public Integer getMode() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void setUser_place(Place place) {
        this.userPlace = place;
    }

    /*장소 검색*/
    public void Loc_by_Search(String searchText) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/"); /*URL*/
        urlBuilder.append("json?" + URLEncoder.encode("input","UTF-8") + "="+URLEncoder.encode(searchText, "UTF-8")); /*장소 text*/
        urlBuilder.append("&" + URLEncoder.encode("inputtype","UTF-8") + "="+ URLEncoder.encode("textquery", "UTF-8")); /*입력 형식 text로 설정*/
        urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("fields","UTF-8") + "=" + URLEncoder.encode("business_status,photos,formatted_address,name,rating,opening_hours,geometry", "UTF-8")); /*반환 받을 값들*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
    }

    //권한 확인후 권한 요청
    public void LocPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            Toast.makeText(this, "사용자 위치정보 동의 거부시 사용이 제한되는 부분이 있을수 있습니다.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } // 권한확인후 위치정보 제공 동의가 안 되어 있을때 위치 정보 제공 동의받기
    }

    public void LocBy_gps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            this.userPlace.set_placeX(location.getLatitude());//경도
            this.userPlace.set_placeY(location.getLongitude());//위도
            this.userPlace.set_placeAddress(location.getProvider());//해당 위치정보
        }// 위치정보 제공 동의가 되어있을때 정보를 받아옴
        else{
            this.userPlace.set_placeX(37.558560);//위도
            this.userPlace.set_placeY(126.998935);//경도
            this.userPlace.set_placeAddress("동국대학교 원흥관");//해당 위치정보
        }//제공 동의가 안되어 있때 유저위치를 동국대학교 원흥관으로 디폴트 값 생성
    }
}
