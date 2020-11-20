package com.example.project;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.text.ParseException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class PageOfStatistics extends Fragment {
    ImageButton btn_seoul, btn_busan, btn_daegu, btn_incheon, btn_gwangju, btn_daejeon, btn_ulsan, btn_sejong,
            btn_gyeonggido, btn_gangwondo, btn_chungbuk, btn_chungnam, btn_jeonbuk, btn_jeonnam, btn1_gyeongbuk,
            btn2_gyeongbuk, btn1_gyeongnam, btn2_gyeongnam, btn_jeju;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_statistics, container, false);

        btn_gyeonggido = v.findViewById(R.id.gyunggi_Button);
        btn_gangwondo = v.findViewById(R.id.gangwondo_Button);
        btn_chungbuk = v.findViewById(R.id.chungBuk_Button);
        btn_chungnam = v.findViewById(R.id.chungNam_Button);
        btn_jeonbuk = v.findViewById(R.id.jeonBuk_Button);
        btn_jeonnam = v.findViewById(R.id.jeonNam_Button);
        btn1_gyeongbuk = v.findViewById(R.id.gyeongBuk1_Button);
        btn2_gyeongbuk = v.findViewById(R.id.gyeongBuk2_Button);
        btn1_gyeongnam = v.findViewById(R.id.gyeongNam1_Button);
        btn2_gyeongnam = v.findViewById(R.id.gyeongNam2_Button);
        btn_jeju = v.findViewById(R.id.jeju_Button);

        btn_seoul = v.findViewById(R.id.seoul_Button);
        btn_busan = v.findViewById(R.id.busan_Button);
        btn_daegu = v.findViewById(R.id.daegu_Button);
        btn_incheon = v.findViewById(R.id.incheon_Button);
        btn_gwangju = v.findViewById(R.id.gwangju_Button);
        btn_daejeon = v.findViewById(R.id.dajeon_Button);
        btn_ulsan = v.findViewById(R.id.ulsan_Button);
        btn_sejong = v.findViewById(R.id.sejong_Button);

        return v;
    }


    public PageOfStatistics() throws ParserConfigurationException, SAXException, ParseException, IOException {
        this.api = new API();
        this.userPlace=new UserLoc();
    }

    private UserLoc userPlace;
    private NationStatistics nationStatistic;
    private API api;

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }

    private void connApi() throws ParserConfigurationException, SAXException, ParseException, IOException {
        //this.nationStatistic=this.api.nationAPI();
    }

    private void findLoc() throws IOException, ParserConfigurationException, SAXException, ParseException {
        this.connApi();
        String parsingUrl="";
        Place searchLoc=null;
        String state=null;
        String shortLocName=null;
        String longLocName=null;

        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/xml"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("latlng","UTF-8") + "="
                +URLEncoder.encode(Double.toString(this.userPlace.getUserPlace().get_placeY()), "UTF-8")+","
                +URLEncoder.encode(Double.toString(this.userPlace.getUserPlace().get_placeX()), "UTF-8") ); /*여기는 위도 경도 y,x순*/

        urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/
        URL url = new URL(urlBuilder.toString());
        parsingUrl=url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList geoList=doc.getElementsByTagName("GeocodeResponse"); //장소 전체 노드

        NodeList status=null;//doc.getElementsByTagName("status"); // 지역 노드
        NodeList resultList=null;//doc.getElementsByTagName("result"); // 지역 노드

        NodeList ComponentList=null;//doc.getElementsByTagName("address_component"); // 지역 노드

        for(int i=0; i<geoList.getLength(); i++) {
            Node geoNode=geoList.item(i);
            if(geoNode.getNodeType()==Node.ELEMENT_NODE) {
                Element geoElement=(Element) geoNode;
                state= getTagValue("status",geoElement); // 상태(오류)
                resultList = geoElement.getElementsByTagName("result");
                Node resultNode=resultList.item(i);
                if(resultNode.getNodeType()==Node.ELEMENT_NODE){
                    Element resultElement=(Element) resultNode;
                    ComponentList =resultElement.getElementsByTagName("address_component");
                    for(int c =0; c<ComponentList.getLength();c++){
                        Node ComponentNode=ComponentList.item(c);
                        if(ComponentNode.getNodeType()==Node.ELEMENT_NODE){
                            Element ComponentElement=(Element) ComponentNode;
                            if(getTagValue("type",ComponentElement)=="administrative_area_level_1"){
                                shortLocName=getTagValue("short_name",ComponentElement);
                                break;
                            }
                        }
                    }
                }
            }
        }
        this.userPlace.getUserPlace().set_placeAddress(shortLocName); // 사용자가 있는 지역 이름 받음
    } //역 지오 코딩


    public void print_nationStatistics() {
        // TODO implement here
    }

    public void print_localStatistics() {
        // TODO implement here
    }

}
