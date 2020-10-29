package com.example.project;
import java.util.*;

/**
 * 장소 관련 정보 저장 class
 * x가 경도 y가 위도
 */
public class Place {

    private String place_address;
    private double place_x;
    private double place_y;

    //Constructor
    public Place(String address, double x,double y ) {
        this.place_address=address;
        this.place_x=x;
        this.place_y=y;
    }

    // get -------------------------
    public double get_x(){
        return this.place_x;
    }
    public double get_y(){
        return this.place_y;
    }
    public String get_address(String provider){
        return this.place_address;
    }
    //----------------------------

    // set -------------------------
    public void set_x(double x){
        this.place_x = x;
    }
    public void set_y(double y){
        this.place_y = y;
    }
    public void set_address(String address){
        this.place_address = address;
    }
    //--------------------------------
}