package com.example.project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class SelectedClinic {

    /**
     * Default constructor
     */
    public SelectedClinic( String name, Place place, String code, String phoneNum) {
        this.name =name;
        this.place=place;
        this.phoneNum=phoneNum;
        this.code=code;
    }

    private String name; //기관이름 넣기
    private Place place;//주소명에 api에서 받은 시도명 시군구명

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

    public void setPlace(Place place) {
        this.place = place;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    /*직선거리 구하기 위한 함수*/
    public double Distance(double lat1, double lon1, String unit) {

        double theta = lon1 - place.get_placeY();
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(place.get_placeX())) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(place.get_placeX())) * Math.cos(deg2rad(theta));

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

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }

    public Place calXY(String name) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl="";
        Place searchLoc=new Place("",0.0,0.0);

        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/"); /*URL*/
        urlBuilder.append("xml?" + URLEncoder.encode("input","UTF-8") + "="+URLEncoder.encode(name, "UTF-8")); /*장소 text*/
        urlBuilder.append("&" + URLEncoder.encode("inputtype","UTF-8") + "="+ URLEncoder.encode("textquery", "UTF-8")); /*입력 형식 text로 설정*/
        urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("fields","UTF-8") + "=" + URLEncoder.encode("formatted_address,geometry", "UTF-8")); /*반환 받을 값들*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/
        URL url = new URL(urlBuilder.toString());
        parsingUrl=url.toString();
        System.out.println("url:"+parsingUrl);
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);
        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList nList=doc.getElementsByTagName("candidates"); //장소 전체 노드

        NodeList geoList=null;//doc.getElementsByTagName("geometry"); // 지역 노드
        NodeList locList=null;//doc.getElementsByTagName("location"); // 지역 노드

        for(int i=0; i<nList.getLength(); i++) {
            Node nNode=nList.item(i);
            if(nNode.getNodeType()==Node.ELEMENT_NODE) {
                Element eElement=(Element) nNode;
                String tm = getTagValue("formatted_address",eElement);
                searchLoc.set_placeAddress(tm);

                geoList = eElement.getElementsByTagName("geometry");
                for(int g =0; g<geoList.getLength();g++){
                    Node geoNode=geoList.item(g);
                    if(geoNode.getNodeType()==Node.ELEMENT_NODE){
                        Element geoElement=(Element) geoNode;
                        locList = geoElement.getElementsByTagName("location");
                        for(int l =0 ;l<locList.getLength();l++){
                            Node locNode=locList.item(l);
                            if(locNode.getNodeType()==Node.ELEMENT_NODE){
                                Element locElement=(Element) locNode;
                                searchLoc.set_placeX(Double.parseDouble(getTagValue("lat",locElement)));//경도
                                searchLoc.set_placeY(Double.parseDouble(getTagValue("lng",locElement)));//위도
                            }
                        }
                    }
                }
            }
        }
        return searchLoc;
    }

}
