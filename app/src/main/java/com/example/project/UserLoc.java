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

public class UserLoc extends AppCompatActivity {

    /**
     * Default constructor
     */
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;

    private Place user_place;
    private Integer mode;

    // 1: 사용자 위치정보 허용 상태, 2: 사용자 위치 정보 허용하지 않은 상태
    public UserLoc() {
    }

    public Place getPlace() {
        return this.user_place;
    }

    public Integer getMode() {
        return mode;
    }

    public void setUser_place(Place p) {
        this.user_place = p;
    }

    public void setMode(Integer m) {
        this.mode = m;
    }

    public void Loc_by_Search() {
        // TODO implement here
    }

    //권한 확인후 권한 요청
    public void Pemission() {
        int mode = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION); // 권한 확인
        if (mode == PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "사용자 위치정보 동의 거부시 사용이 제한되는 부분이 있을수 있습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Toast.makeText(this, "사용자 위치정보 동의 거부시 사용이 제한되는 부분이 있을수 있습니다.", Toast.LENGTH_LONG).show();
        }
    }
    public void Locby_gps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            this.user_place.set_x(location.getLatitude());//경도
            this.user_place.set_y(location.getLongitude());//위도
            this.user_place.get_address(location.getProvider());//해당 위치정보
        }// 위치정보 제공 동의가 되어있을때
        else{
            this.user_place.set_x(37.55746);//위도
            this.user_place.set_y(127.00059);//경도
            this.user_place.get_address("동국대학교");//해당 위치정보
        }//제공 동의가 안되어 있때 유저위치를 동국대학교로 디폴트 값 생성
    }

}