package com.example.project;
import java.util.*;

/**
 * 
 */
public class SelectedClinic {

    /**
     * Default constructor
     */
    public SelectedClinic( String name,Place place , Date date, char type ,String code, String phoneNum) {
        this.name =name;
        this.place=place;
        this.phoneNum=phoneNum;
        this.date=date;
        this.code=code;
        this.type=type;
    }

    private String name; //기관이름 넣기
    private Place place;//주소명에 api에서 받은 시도명 시군구명
    private Date date; // 운영가능일자

    private char type;
    /*선정유형A: 일반 호흡기 환자 진료를 위한 호흡기 전용 외래 설치 운영 병원)
    B: 호흡기 환자 전용 외래입원 진료가 가능한 선별진료소 운영 병원*/

    private String code;
    /*
    구분코드A0: 국민안심병원
    97: 코로나 검사 실시기관
    99: 코로나 선별진료소 운영기관
    */

    private String phoneNum;//전화번호



    public void setName(String name) {
        this.name = name;
    }

    public void setType(char type) {
        this.type = type;
    }
    public void setPlace(Place place) {
        this.place = place;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public Place getPlace() {
        return place;
    }
    public Date getDate() {
        return date;
    }
    public char getType() {
        return type;
    }

    /*직선거리 구하기 위한 함수*/
    public double Distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
