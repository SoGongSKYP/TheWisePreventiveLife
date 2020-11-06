package com.example.project;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
    public void Loc_by_Search(String searchText) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl="";

        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/"); /*URL*/
        urlBuilder.append("xml?" + URLEncoder.encode("input","UTF-8") + "="+URLEncoder.encode(searchText, "UTF-8")); /*장소 text*/
        urlBuilder.append("&" + URLEncoder.encode("inputtype","UTF-8") + "="+ URLEncoder.encode("textquery", "UTF-8")); /*입력 형식 text로 설정*/
        urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("fields","UTF-8") + "=" + URLEncoder.encode("formatted_address,name,opening_hours,geometry", "UTF-8")); /*반환 받을 값들*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/

        URL url = new URL(urlBuilder.toString());
        parsingUrl=url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList nList=doc.getElementsByTagName("candidates"); //장소 전체 노드

        NodeList geoList=null;//doc.getElementsByTagName("geometry"); // 지역 노드
        NodeList locList=null;//doc.getElementsByTagName("location"); // 지역 노드

        NodeList openTimeList=null;//doc.getElementsByTagName("opening_hours"); // 지역 노드
        //System.out.println("파싱할 리스트 수 : "+nList.getLength());

        for(int i=0; i<nList.getLength(); i++) {
            Node nNode=nList.item(i);
            if(nNode.getNodeType()==Node.ELEMENT_NODE) {
                Element eElement=(Element) nNode;

                System.out.println("장소 이름: "+getTagValue("name",eElement));
                System.out.println("장소 주소"+getTagValue("formatted_address",eElement));

                Node timeNode=openTimeList.item(i);
                if(timeNode.getNodeType()==Node.ELEMENT_NODE){
                    Element timeElement=(Element) timeNode;
                    System.out.println("열려있는지 여부: "+getTagValue("open_now",timeElement));
                }
                geoList = eElement.getElementsByTagName("geometry");
                for(int g =0; g<geoList.getLength();g++){
                    Node geoNode=geoList.item(g);
                    if(geoNode.getNodeType()==Node.ELEMENT_NODE){
                        Element geoElement=(Element) geoNode;
                        locList = geoElement.getElementsByTagName("location");
                        Node locNode=locList.item(0);
                        if(locNode.getNodeType()==Node.ELEMENT_NODE){
                            Element locElement=(Element) locNode;
                            System.out.println("장소 경도: "+getTagValue("lat",locElement));
                            System.out.println("장소 위도: "+getTagValue("lng",locElement));
                        }
                    }
                }
            }
        }
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
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
