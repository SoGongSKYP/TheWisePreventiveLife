package com.example.project;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UserLoc extends AppCompatActivity {

    /**
     * Default constructor
     */
    //private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;
    private Place userPlace;

    // 1: 사용자 위치정보 허용 상태, 2: 사용자 위치 정보 허용하지 않은 상태
    public UserLoc() {
        this.userPlace = new Place("동국대학교 정보문화관", 37.559562, 126.998557);
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
    public void Loc_by_Search(String searchText) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl = "";

        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/"); /*URL*/
        urlBuilder.append("xml?" + URLEncoder.encode("input", "UTF-8") + "=" + URLEncoder.encode(searchText, "UTF-8")); /*장소 text*/
        urlBuilder.append("&" + URLEncoder.encode("inputtype", "UTF-8") + "=" + URLEncoder.encode("textquery", "UTF-8")); /*입력 형식 text로 설정*/
        urlBuilder.append("&" + URLEncoder.encode("language", "UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("fields", "UTF-8") + "=" + URLEncoder.encode("formatted_address,name,opening_hours,geometry", "UTF-8")); /*반환 받을 값들*/
        urlBuilder.append("&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/

        URL url = new URL(urlBuilder.toString());
        parsingUrl = url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("candidates"); //장소 전체 노드

        NodeList geoList = null;//doc.getElementsByTagName("geometry"); // 지역 노드
        NodeList locList = null;//doc.getElementsByTagName("location"); // 지역 노드

        NodeList openTimeList = null;//doc.getElementsByTagName("opening_hours"); // 지역 노드
        //System.out.println("파싱할 리스트 수 : "+nList.getLength());

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                System.out.println("장소 이름: " + getTagValue("name", eElement));
                System.out.println("장소 주소" + getTagValue("formatted_address", eElement));

                Node timeNode = openTimeList.item(i);
                if (timeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element timeElement = (Element) timeNode;
                    System.out.println("열려있는지 여부: " + getTagValue("open_now", timeElement));
                }
                geoList = eElement.getElementsByTagName("geometry");
                for (int g = 0; g < geoList.getLength(); g++) {
                    Node geoNode = geoList.item(g);
                    if (geoNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element geoElement = (Element) geoNode;
                        locList = geoElement.getElementsByTagName("location");
                        Node locNode = locList.item(0);
                        if (locNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element locElement = (Element) locNode;
                            System.out.println("장소 경도: " + getTagValue("lat", locElement));
                            System.out.println("장소 위도: " + getTagValue("lng", locElement));
                        }
                    }
                }
            }
        }
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null) return null;
        return nValue.getNodeValue();
    }


    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1/2;


    public void LocBy_gps(Context context) {
        GPSListener gpsListener = new GPSListener();
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Location location = null;
            if (!isGPSEnabled && !isNetworkEnabled) {
            } else {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) gpsListener);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                this.userPlace.set_placeX(location.getLatitude());
                                this.userPlace.set_placeY(location.getLongitude());//위도
                            }
                        }
                    }
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) gpsListener);
                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    this.userPlace.set_placeX(location.getLatitude());
                                    this.userPlace.set_placeY(location.getLongitude());//위도
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("@@@", "" + e.toString());
        }
    }
    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}
