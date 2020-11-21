package com.example.project;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    int localNum = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_statistics, container, false);
        btn_gyeonggido = v.findViewById(R.id.gyeonggi_Button);
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
        btn_daejeon = v.findViewById(R.id.daejeon_Button);
        btn_ulsan = v.findViewById(R.id.ulsan_Button);
        btn_sejong = v.findViewById(R.id.sejong_Button);

        LocalButtonAction();
        return v;
    }



    public PageOfStatistics() throws ParserConfigurationException, SAXException, ParseException, IOException {
        this.userPlace=new UserLoc();
        final Lock lock = new ReentrantLock(); // lock instance
        this.nationStatistic = APIEntity.getNation();
        geoThread geo = new geoThread(this,lock);
        Thread thread = new Thread(geo);
        thread.start();
        if(userPlace.getUserPlace().get_placeAddress() == "제주"){
            localNum=1;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "경남"){
            localNum=2;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "경북"){
            localNum=3;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "전남"){
            localNum=4;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "전북"){
            localNum=5;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "충남"){
            localNum=6;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "충북"){
            localNum=7;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "강원"){
            localNum=8;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "경기"){
            localNum=9;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "세종"){
            localNum=10;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "울산"){
            localNum=11;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "대전"){
            localNum=12;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "광주"){
            localNum=13;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "인천"){
            localNum=14;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "대구"){
            localNum=15;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "부산"){
            localNum=16;
        }
        else if(userPlace.getUserPlace().get_placeAddress() == "서울"){
            localNum=17;
        }
    }

    private UserLoc userPlace;
    private NationStatistics nationStatistic;

    public void nationStatistics() {
        System.out.println("전국");
        System.out.println("기준 일시: "+this.nationStatistic.getStaticsDate());

        System.out.println("누적 검사 횟수 : "+this.nationStatistic.getTestCnt());
        System.out.println("누적 검사 완료수: "+this.nationStatistic.getTestCntComplete());
        System.out.println("결과 음성: "+this.nationStatistic.getTestNeg());
        System.out.println("검사 중: "+this.nationStatistic.getTestNum());
        Double confirmRate = ((double) this.nationStatistic.getPatientNum()/(double)this.nationStatistic.getTestCntComplete())*100;
        System.out.println("확진률: "+confirmRate+"%");

        System.out.println("누적 확진자: "+this.nationStatistic.getPatientNum());
        System.out.println("오늘 확진자: "+this.nationStatistic.getLocalStatistics().get(this.nationStatistic.getLocalStatistics().size()-1).getIncreaseDecrease());
        System.out.println("지역 유입: "+this.nationStatistic.getLocalStatistics().get(this.nationStatistic.getLocalStatistics().size()-1).getLocConfirm());
        System.out.println("해외 유입: "+this.nationStatistic.getLocalStatistics().get(this.nationStatistic.getLocalStatistics().size()-1).getBroadConfirm());

        System.out.println("격리 중 : "+this.nationStatistic.getCareNum());
        System.out.println("누적 격리 해제: "+this.nationStatistic.getHealerNum());
        System.out.println("사망자: "+this.nationStatistic.getDeadNum());
        System.out.println("10만명당 확진률: "+this.nationStatistic.getLocalStatistics().get(this.nationStatistic.getLocalStatistics().size()-1).getQurRate()+"%");
    }

    public void localStatistics() {
        System.out.println("지역: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getLocalName());
        System.out.println("기준 일시: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getStaticsDate());

        System.out.println("누적 확진자: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getAccumulatePatient());
        System.out.println("오늘 확진자: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getTodayConfirm());
        System.out.println("지역 유입: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getLocConfirm());
        System.out.println("해외 유입: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getBroadConfirm());

        System.out.println("격리 중 : "+this.nationStatistic.getLocalStatistics().get(this.localNum).getPatientNum());
        System.out.println("누적 격리 해제: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getHealerNum());
        System.out.println("사망자: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getDeadNum());
        System.out.println("10만명당 확진률: "+this.nationStatistic.getLocalStatistics().get(this.localNum).getAccumulatePatient()+"%");
    }

    Button.OnClickListener localClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.seoul_Button:
                    Toast.makeText(getContext(), "서울 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 17;
                    break;
                case R.id.busan_Button:
                    Toast.makeText(getContext(), "부산 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 16;
                    break;
                case R.id.daegu_Button:
                    Toast.makeText(getContext(), "대구 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 15;
                    break;
                case R.id.incheon_Button:
                    Toast.makeText(getContext(), "인천 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 14;
                    break;
                case R.id.gwangju_Button:
                    Toast.makeText(getContext(), "광주 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 13;
                    break;
                case R.id.daejeon_Button:
                    Toast.makeText(getContext(), "대전 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 12;
                    break;
                case R.id.ulsan_Button:
                    Toast.makeText(getContext(), "울산 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 11;
                    break;
                case R.id.sejong_Button:
                    Toast.makeText(getContext(), "세종 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 10;
                    break;
                case R.id.gyeonggi_Button:
                    Toast.makeText(getContext(), "경기 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 9;
                    break;
                case R.id.gangwondo_Button:
                    Toast.makeText(getContext(), "강원 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 8;
                    break;
                case R.id.chungBuk_Button:
                    Toast.makeText(getContext(), "충북 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 7;
                    break;
                case R.id.chungNam_Button:
                    Toast.makeText(getContext(), "충남 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 6;
                    break;
                case R.id.jeonBuk_Button:
                    Toast.makeText(getContext(), "전북 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 5;
                    break;
                case R.id.jeonNam_Button:
                    Toast.makeText(getContext(), "전남 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 4;
                    break;
                case R.id.gyeongBuk1_Button:
                case R.id.gyeongBuk2_Button:
                    Toast.makeText(getContext(), "경북 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 3;
                    break;
                case R.id.gyeongNam1_Button:
                case R.id.gyeongNam2_Button:
                    Toast.makeText(getContext(), "경남 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 2;
                    break;
                case R.id.jeju_Button:
                    Toast.makeText(getContext(), "제주 클릭", Toast.LENGTH_SHORT).show();
                    localNum = 1;
                    break;
            }
        }
    };

    private void LocalButtonAction(){
        btn_gyeonggido.setOnClickListener(localClickListener);
        btn_gangwondo.setOnClickListener(localClickListener);
        btn_chungbuk.setOnClickListener(localClickListener);
        btn_chungnam.setOnClickListener(localClickListener);
        btn_jeonbuk.setOnClickListener(localClickListener);
        btn_jeonnam.setOnClickListener(localClickListener);
        btn1_gyeongbuk.setOnClickListener(localClickListener);
        btn2_gyeongbuk.setOnClickListener(localClickListener);
        btn1_gyeongnam.setOnClickListener(localClickListener);
        btn2_gyeongnam.setOnClickListener(localClickListener);
        btn_jeju.setOnClickListener(localClickListener);

        btn_seoul.setOnClickListener(localClickListener);
        btn_busan.setOnClickListener(localClickListener);
        btn_daegu.setOnClickListener(localClickListener);
        btn_incheon.setOnClickListener(localClickListener);
        btn_gwangju.setOnClickListener(localClickListener);
        btn_daejeon.setOnClickListener(localClickListener);
        btn_ulsan.setOnClickListener(localClickListener);
        btn_sejong.setOnClickListener(localClickListener);
    }
    public UserLoc getUserPlace() {
        return userPlace;
    }
}
class geoThread implements Runnable{
    PageOfStatistics page;
    private final Lock lock;
    geoThread(PageOfStatistics page, Lock lock){
        this.page=page;
        this.lock = lock;
    }
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }
    @Override
    public void run() {
        String parsingUrl="";
        Place searchLoc=null;
        String state=null;
        String shortLocName=null;
        lock.lock();
        try{
            StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/xml"); /*URL*/
            try {
                urlBuilder.append("?" + URLEncoder.encode("latlng","UTF-8") + "="
                        +URLEncoder.encode(Double.toString(page.getUserPlace().getUserPlace().get_placeY()), "UTF-8")+","
                        +URLEncoder.encode(Double.toString(page.getUserPlace().getUserPlace().get_placeX()), "UTF-8") ); /*여기는 위도 경도 y,x순*/
                urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
                urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode("AIzaSyCjdZL_BjLqCcj0PBKGcUP6kteb5tV2syE", "UTF-8")); /*키 값*/
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            URL url = null;
            try {
                url = new URL(urlBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            assert url != null;
            parsingUrl=url.toString();
            //System.out.println(parsingUrl);

            DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder= null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc= null;
            try {
                assert dBuilder != null;
                doc = dBuilder.parse(parsingUrl);
            } catch (IOException | SAXException e) {
                e.printStackTrace();
            }

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
            page.getUserPlace().getUserPlace().set_placeAddress(shortLocName); // 사용자가 있는 지역 이름 받음
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
