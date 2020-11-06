package com.example.project;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class PageOfMyDanger extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_danger, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfMyDanger() {
        this.userLoc.LocPermission();
        this.userLoc.LocBy_gps();//사용자 위치 받아옴
        this.searchResultPath =null;
    }

    /**
     * 
     */
    private UserLoc userLoc;
    private ArrayList<Place> visitPlaceList;
    private ArrayList<Place> routeList;
    private int danger;
    private ArrayList<SearchPath> searchResultPath;
    /**
     * 
     */

    public void printUI() {
        // TODO implement here
    }

    /**
     * 
     */
    /*시내 교통 서치 루트*/
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }

    public Place searchPlace(String startPoint) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl="";
        Place searchLoc=null;

        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/"); /*URL*/
        urlBuilder.append("xml?" + URLEncoder.encode("input","UTF-8") + "="+URLEncoder.encode(startPoint, "UTF-8")); /*장소 text*/
        urlBuilder.append("&" + URLEncoder.encode("inputtype","UTF-8") + "="+ URLEncoder.encode("textquery", "UTF-8")); /*입력 형식 text로 설정*/
        urlBuilder.append("&" + URLEncoder.encode("language","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*리턴 정보 한국어로 리턴*/
        urlBuilder.append("&" + URLEncoder.encode("fields","UTF-8") + "=" + URLEncoder.encode("formatted_address,name,geometry", "UTF-8")); /*반환 받을 값들*/
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
                searchLoc.set_placeAddress(getTagValue("name",eElement));
                System.out.println("장소 주소"+getTagValue("formatted_address",eElement));

                geoList = eElement.getElementsByTagName("geometry");
                for(int g =0; g<geoList.getLength();g++){
                    Node geoNode=geoList.item(g);
                    if(geoNode.getNodeType()==Node.ELEMENT_NODE){
                        Element geoElement=(Element) geoNode;
                        locList = geoElement.getElementsByTagName("location");
                        Node locNode=locList.item(0);
                        if(locNode.getNodeType()==Node.ELEMENT_NODE){
                            Element locElement=(Element) locNode;
                            searchLoc.set_placeX(Double.parseDouble(getTagValue("lat",locElement)));//경도
                            searchLoc.set_placeY(Double.parseDouble(getTagValue("lng",locElement)));//위도
                        }
                    }
                }
            }
        }
        return searchLoc;
    }

    private ArrayList<SearchPath> calRoute(Place startPoint,Place desPoint ) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl="";
        ArrayList<SearchPath> resultSearchPath=null;
        StringBuilder urlBuilder = new StringBuilder("https://api.odsay.com/v1/api/searchPubTransPath"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("output","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*출력형태*/
        urlBuilder.append("&" + URLEncoder.encode("SX","UTF-8") + "=" + URLEncoder.encode(Double.toString(startPoint.get_placeX()), "UTF-8")); /*출발지 경도좌표*/
        urlBuilder.append("&" + URLEncoder.encode("SY","UTF-8") + "=" + URLEncoder.encode(Double.toString(startPoint.get_placeY()), "UTF-8")); /*출발지 위도좌표*/
        urlBuilder.append("&" + URLEncoder.encode("EX","UTF-8") + "=" + URLEncoder.encode(Double.toString(desPoint.get_placeX()), "UTF-8")); /*도착지 경도좌표*/
        urlBuilder.append("&" + URLEncoder.encode("EY","UTF-8") + "=" + URLEncoder.encode(Double.toString(desPoint.get_placeY()), "UTF-8")); /*도착지 위도*/
        urlBuilder.append("&" + URLEncoder.encode("SearchType","UTF-8") + "=" + URLEncoder.encode("0", "UTF-8")); /*도시내 이동/도시간 이동을 구분검색한다. 0 입력시 도시내검색 (도시내검색에서 도시간검색결과 있을경우 활용)*/
        urlBuilder.append("&" + URLEncoder.encode("apiKey","UTF-8") + "=" + URLEncoder.encode("cDSdUY9qLmrLpcqsJL3zPvgpx3IgkOf4sLsbkzSOZ2Y", "UTF-8")); /*키값*/
        URL url = new URL(urlBuilder.toString());

        parsingUrl=url.toString();
        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();

        /*시내*/
        NodeList pathList=doc.getElementsByTagName("path"); /*1-9*/

        NodeList infoList=null; /*1-9-2 요약 정보 확장 노드*/

        NodeList subPathList=null;/*1-9-3 이동 교통 수단 정보 확장 노드*/

        NodeList laneList=null;/*1-9-3-5 교통 수단 정보 확장 노드*/

        NodeList passStopList=null; /*1-9-3-23 경로 상세구간 정보 확장 노드(국문에 한하여 제공)*/
        NodeList stationsList=null;/*1-9-3-23-1 정류장 정보 그룹노드*/

        for(int i=0; i<pathList.getLength(); i++){
            Node pathNode=pathList.item(i);/*1-9 결과 리스트 확장 노드*/
            if(pathNode.getNodeType()==Node.ELEMENT_NODE){
                Element pathElement=(Element) pathNode;
                SearchPath temp =null;
                temp.setPathType(Integer.parseInt(getTagValue("pathType",pathElement)));; //결과 종류 1-지하철, 2-버스, 3-버스+지하철
                ExtendNode eTemp =null;
                infoList = pathElement.getElementsByTagName("info"); /*1-9-2 요약 정보 확장 노드*/
                Node infoNode=infoList.item(0);
                if(infoNode.getNodeType()==Node.ELEMENT_NODE){
                    Element infoElement=(Element) infoNode;
                    eTemp.setTrafficDistance(Double.parseDouble(getTagValue("trafficDistance",infoElement)));//도보를 제외한 총 이동 거리
                    eTemp.setTotalWalk(Integer.parseInt(getTagValue("totalWalk",infoElement)));//총 도보 이동 거리
                    eTemp.setTotalTime(Integer.parseInt(getTagValue("totalTime",infoElement)));//총 소요 시간
                    eTemp.setPayment(Integer.parseInt(getTagValue("payment",infoElement)));//총 요금
                    eTemp.setBusTransitCount(Integer.parseInt(getTagValue("busTransitCount",infoElement)));//버스 환승 카운트
                    eTemp.setSubwayTransitCount(Integer.parseInt(getTagValue("subwayTransitCount",infoElement)));//지하철 환승 카운트
                    eTemp.setMapObj(getTagValue("mapObj",infoElement));//보간점 API를 호출하기 위한 파라미터 값
                    eTemp.setFirstStartStation(getTagValue("firstStartStation",infoElement));//최초 출발역/정류장
                    eTemp.setLastEndStation(getTagValue("lastEndStation",infoElement));//최종 도착역/정류장
                    eTemp.setTotalStationCount(Integer.parseInt(getTagValue("totalStationCount",infoElement)));//총 정류장 합
                    eTemp.setTotalDistance(Double.parseDouble(getTagValue("totalDistance",infoElement)));//총거리
                }
                temp.setInfo(eTemp);

                ArrayList<SubPath> subPathsList=null;
                SubPath sTemp=null;
                ArrayList<SubwayLane> subwayLanes=null;
                ArrayList<BusLane> busLanes=null;
                SubwayLane swlTemp=null;
                BusLane blTemp =null;

                subPathList = pathElement.getElementsByTagName("subPath");/*1-9-3 이동 교통 수단 정보 확장 노드*/
                for(int s=0; s<subPathList.getLength(); s++){
                    Node subPathNode=subPathList.item(s);
                    if(subPathNode.getNodeType()==Node.ELEMENT_NODE){
                        Element subPathElement=(Element) subPathNode;

                        /*1-9-3-1 이동 수단 종류 (도보, 버스, 지하철)1-지하철, 2-버스, 3-도보*/
                        sTemp.setTrafficType(Integer.parseInt(getTagValue("trafficType",subPathElement)));
                        sTemp.setDistance(Double.parseDouble(getTagValue("distance",subPathElement)));/*1-9-3-2 이동 거리*/
                        sTemp.setSectionTime(Integer.parseInt(getTagValue("sectionTime",subPathElement)));/*1-9-3-3 이동 소요 시간*/

                        /*1-9-3-5 교통 수단 정보 확장 노드*/
                        laneList = subPathElement.getElementsByTagName("lane");
                        for(int l =0; l<laneList.getLength();i++){
                            Node laneNode=laneList.item(l);/*1-9-3-5 교통 수단 정보 확장 노드*/
                            if(laneNode.getNodeType()==Node.ELEMENT_NODE){
                                Element laneElement=(Element) laneNode;
                                if(getTagValue("trafficType",subPathElement)!="1"){ //지하철일때
                                    swlTemp.setIndex(l);
                                    swlTemp.setName(getTagValue("name",laneElement));/*1-9-3-5-1 지하철 노선명 (지하철인 경우에만 필수)*/
                                    swlTemp.setSubwayCode(Integer.parseInt(getTagValue("subwayCode",laneElement)));/*1-9-3-5-5 지하철 노선 번호 (지하철인 경우에만 필수)*/
                                    swlTemp.setSubwayCityCode(Integer.parseInt(getTagValue("subwayCityCode",laneElement)));/*1-9-3-5-6 지하철 도시코드 (지하철인 경우에만 필수)*/
                                    subwayLanes.add(swlTemp);
                                }
                                else if(getTagValue("trafficType",subPathElement)!="2")// 버스 일때
                                {
                                    blTemp.setIndex(l);
                                    blTemp.setBusID(Integer.parseInt(getTagValue("busID",laneElement)));/*1-9-3-5-4 버스 코드 (버스인 경우에만 필수)*/
                                    blTemp.setType(Integer.parseInt(getTagValue("type",laneElement)));/*1-9-3-5-3 버스 타입 (버스인 경우에만 필수,최하단 참조)*/
                                    blTemp.setBusNo(getTagValue("busNo",laneElement));/*1-9-3-5-2 버스 번호 (버스인 경우에만 필수)*/
                                    busLanes.add(blTemp);
                                }
                            }
                        }
                        sTemp.setBusLanes(busLanes);
                        sTemp.setSubwayLanes(subwayLanes);

                        sTemp.setStationCount(Integer.parseInt(getTagValue("stationCount",subPathElement)));/*1-9-3-4 이동 정거장 수(지하철, 버스 경우만 필수)*/

                        sTemp.setStartStation(new Place(getTagValue("startName",subPathElement)
                                ,Double.parseDouble(getTagValue("startX",subPathElement))
                                ,Double.parseDouble(getTagValue("startY",subPathElement))));
                        /*1-9-3-6 승차 정류장/역명 ,1-9-3-7 승차 정류장/역 X 좌표,1-9-3-8 승차 정류장/역 Y 좌표*/
                        sTemp.setEndStation(new Place(getTagValue("endName",subPathElement)
                                ,Double.parseDouble(getTagValue("endX",subPathElement))
                                ,Double.parseDouble(getTagValue("endY",subPathElement))));
                        /*1-9-3-9 하차 정류장/역명,1-9-3-10 하차 정류장/역 X 죄표,1-9-3-11 하차 정류장/역 Y 좌표*/

                        sTemp.setWay(getTagValue("way",subPathElement));/*1-9-3-12 방면 정보 (지하철인 경우에만 필수)*/
                        sTemp.setWayCode(Integer.parseInt(getTagValue("wayCode",subPathElement)));/*1-9-3-13 방면 정보 코드(지하철의 첫번째 경로에만 필수)1 : 상행, 2: 하행*/
                        sTemp.setDoor(getTagValue("door",subPathElement)); /*1-9-3-14 지하철 빠른 환승 위치 (지하철인 경우에만 필수)*/

                        sTemp.setStartID(Integer.parseInt(getTagValue("startID",subPathElement)));/*1-9-3-15 출발 정류장/역 코드*/
                        sTemp.setEndID(Integer.parseInt(getTagValue("endID",subPathElement)));/*1-9-3-16 도착 정류장/역 코드*/

                        sTemp.setStartExitNo(new Place(getTagValue("startExitNo",subPathElement)
                                ,Double.parseDouble(getTagValue("startExitX",subPathElement))
                                ,Double.parseDouble(getTagValue("startExitY",subPathElement))));
                        /*1-9-3-17 지하철 들어가는 출구 번호(지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)
                        1-9-3-18 지하철 들어가는 출구 X좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)
                        1-9-3-19 지하철 들어가는 출구 Y좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)*/

                        sTemp.setEndExitNo(new Place(getTagValue("endExitNo",subPathElement)
                                ,Double.parseDouble(getTagValue("endExitX",subPathElement))
                                ,Double.parseDouble(getTagValue("endExitY",subPathElement))));
                        /*1-9-3-17 지하철 나가는 출구 번호(지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)
                        1-9-3-21 지하철 나가는 출구 X좌표(지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)
                        1-9-3-21 지하철 나가는 출구 Y좌표(지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)*/

                        /*1-9-3-23 경로 상세구간 정보 확장 노드(국문에 한하여 제공)*/
                        passStopList=subPathElement.getElementsByTagName("passStopList");
                        Node passStopListNode=passStopList.item(0);
                        Element passStopElement=(Element) passStopListNode;
                        Stations stTemp=null;
                        ArrayList<Stations> sList = null;
                        /*1-9-3-23-1 정류장 정보 그룹노드*/
                        stationsList = passStopElement.getElementsByTagName("stations");
                        for(int sl=0;sl<stationsList.getLength();sl++){
                            Node stationsNode=stationsList.item(sl);/*1-9-3-23-1 정류장 정보 그룹노드*/
                            if(stationsNode.getNodeType()==Node.ELEMENT_NODE){
                                Element stationsElement=(Element) stationsNode;
                                stTemp.setIndex(Integer.parseInt(getTagValue("index",subPathElement)));
                                Place p = null;
                                p.set_placeAddress(getTagValue("station",subPathElement));
                                p.set_placeY(Double.parseDouble(getTagValue("station",subPathElement)));
                                p.set_placeX(Double.parseDouble(getTagValue("station",subPathElement)));
                                stTemp.setStation(p);
                                stTemp.setStationID(Integer.parseInt(getTagValue("stationID",subPathElement)));
                                sList.add(stTemp);
                            }
                        }
                        sTemp.setStations(sList);
                        subPathsList.add(sTemp);
                    }
                }
                temp.setSubPaths(subPathsList);
                resultSearchPath.add(temp);
            }
        }
        return resultSearchPath;
    }

    public void printPath(int i,Place startPlace,Place desPlace) throws ParserConfigurationException, SAXException, IOException {
        String startPoint = null;
        String desPoint  =null;
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
                for(int sub = 0; sub < subPath.getSubwayLanes().size();sub++){
                    SubwayLane temp = subPath.getSubwayLanes().get(sub);
                    System.out.println("지하철 노선: "+temp.getName());
                }
            }
            else if(subPath.getTrafficType()==2){
                System.out.println("이동 수단: 버스");
                System.out.println("이동 정거장 수: "+subPath.getStationCount());
                System.out.println("승차 정류장: "+subPath.getStartStation().get_placeAddress());
                System.out.println("하차 정류장: "+subPath.getEndStation().get_placeAddress());
                for(int sub = 0; sub < subPath.getBusLanes().size();sub++){
                    BusLane temp = subPath.getBusLanes().get(sub);
                    System.out.println("버스 번호: "+temp.getBusNo());
                    if(temp.getType() == 1){
                        System.out.println("일반");
                    }
                    else if(temp.getType() == 2){
                        System.out.println("좌석");
                    }
                    else if(temp.getType() == 3){
                        System.out.println("마을 버스");
                    }
                    else if(temp.getType() == 4){
                        System.out.println("직행 좌석");
                    }
                    else if(temp.getType() == 5){
                        System.out.println("공항 버스");
                    }
                    else if(temp.getType() == 6){
                        System.out.println("간선 급행");
                    }
                    else if(temp.getType() == 10){
                        System.out.println("외곽 ");
                    }
                    else if(temp.getType() == 11){
                        System.out.println("간선");
                    }
                    else if(temp.getType() == 12){
                        System.out.println("지선");
                    }
                    else if(temp.getType() == 13){
                        System.out.println("순환");
                    }
                    else if(temp.getType() == 14){
                        System.out.println("광역");
                    }
                    else if(temp.getType() == 15){
                        System.out.println("급행");
                    }
                    else if(temp.getType() == 20){
                        System.out.println("농어촌 버스");
                    }
                    else if(temp.getType() == 21){
                        System.out.println("제주도 시외형 버스");
                    }
                    else if(temp.getType() == 22){
                        System.out.println("경기도 시외형 버스");
                    }
                    else if(temp.getType() == 26){
                        System.out.println("급행 간선");
                    }
                }
            }
            else if(subPath.getTrafficType()==3){
                System.out.println("이동 수단: 도보");
            }

        }
    }// 여러개의 path중 하나의 path를 출력할 함수

    public void printRoute() throws ParserConfigurationException, SAXException, IOException {
        String startPoint = null;
        String desPoint  =null;
        Place startPlace =searchPlace(startPoint);
        Place desPlace =searchPlace(desPoint);

        this.searchResultPath=calRoute(startPlace,desPlace);//경로 검색

    }//여러개의 path 모두다 출력

    /**
     * @return
     */
    public int printDanger(ArrayList<SearchPath> searchPath) {

        for(int i=0; i < searchPath.size();i++){
            for (int j=0;j<searchPath.get(i).getSubPaths().size();j++){
                this.visitPlaceList.add(searchPath.get(i).getSubPaths().get(j).getStartStation());
                this.visitPlaceList.add(searchPath.get(i).getSubPaths().get(j).getEndStation());
            }
        } // 경로 상 모든 들리는 장소를 경유지 리스트에 넣어줌
        return 0;
    }

    /**
     * @return
     */


}
