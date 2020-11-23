package com.example.project;

import android.content.Context;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CalRoute implements Runnable{
    private Place startPlace;
    private Place endPlace;
    private String startPoint;
    private String desPoint;
    private ArrayList<SearchPath> searchResultPath;
    private final Lock lock;
    private Context thisContext;

    CalRoute(Context context,String startPoint, String desPoint, Lock lock){
        this.startPoint=startPoint;
        this.desPoint=desPoint;
        this.lock = lock;
        this.searchResultPath = new ArrayList<SearchPath>();
        this.thisContext=context;
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }

    public Place searchPlace(String startPoint) throws IOException, ParserConfigurationException, SAXException {
        String parsingUrl="";
        String key= "340FCCC5-C1C9-31D4-B7D8-56BC7558298A";
        ArrayList<Place> searchLocList =new ArrayList<Place>();
        Place searchLoc=new Place("",0.0,0.0);
        StringBuilder urlBuilder = new StringBuilder("http://api.vworld.kr/req/search?"); /*URL*/
        urlBuilder.append(URLEncoder.encode("service","UTF-8") + "="+URLEncoder.encode("search", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("request","UTF-8") + "="+ URLEncoder.encode("search", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("version","UTF-8") + "=" + URLEncoder.encode("2.0", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("query","UTF-8") + "=" + URLEncoder.encode(startPoint, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("place", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("errorformat","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode(key, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        parsingUrl=url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);
        doc.getDocumentElement().normalize();

        NodeList nList=doc.getElementsByTagName("item"); //장소 전체 노드

        for(int i=0; i<nList.getLength(); i++) {
            Node nNode=nList.item(i);
            if(nNode.getNodeType()==Node.ELEMENT_NODE) {
                Element eElement=(Element) nNode;
                searchLoc.set_placeAddress(getTagValue("road",eElement));
                searchLoc.set_placeX(Double.parseDouble(getTagValue("y",eElement)));
                searchLoc.set_placeY(Double.parseDouble(getTagValue("x",eElement)));
                searchLocList.add(searchLoc);
            }
        }
        findPlace(searchLocList);
        return searchLocList.get(0);
    }
    private void findPlace(ArrayList<Place> searchLocList){
        for(int i =0 ; i <searchLocList.size();i++ ){
            System.out.println(searchLocList.get(i).get_placeAddress());
        }
    }
    private ArrayList<SearchPath> calRoute1(Context context, Place startPoint, Place desPoint ) throws IOException, ParserConfigurationException, SAXException {
        ODsayService oDsayService=ODsayService.init(context,"cDSdUY9qLmrLpcqsJL3zPvgpx3IgkOf4sLsbkzSOZ2Y");
        final ArrayList<SearchPath> resultSearchPath=new ArrayList<SearchPath>();
        final ArrayList<SubPath> temp =new ArrayList<SubPath>();

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                SubPath sp=null;
                try{
                    if(api==API.SEARCH_PUB_TRANS_PATH){
                        int localSearch = oDsayData.getJson().getJSONObject("result").getInt("outTrafficCheck");
                        if(localSearch==1){
                            int totalCount = oDsayData.getJson().getJSONObject("result").getInt("busCount")
                                    + oDsayData.getJson().getJSONObject("result").getInt("subwayCount")
                                    + oDsayData.getJson().getJSONObject("result").getInt("subwayBusCount");//총 경로 결과 개수
                            for(int i =0; i < totalCount;i++){
                                JSONObject path = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(i);
                                ExtendNode t = new ExtendNode(path.getJSONObject("info").getDouble("trafficDistance")
                                        ,path.getJSONObject("info").getInt("totalWalk")
                                        ,path.getJSONObject("info").getInt("totalTime")
                                        ,path.getJSONObject("info").getInt("payment")
                                        ,path.getJSONObject("info").getInt("busTransitCount")
                                        ,path.getJSONObject("info").getInt("subwayTransitCount")
                                        ,path.getJSONObject("info").getString("mapObj")
                                        ,path.getJSONObject("info").getString("firstStartStation")
                                        ,path.getJSONObject("info").getString("lastEndStation")
                                        ,path.getJSONObject("info").getInt("totalStationCount")
                                        ,path.getJSONObject("info").getInt("totalDistance"));
                                JSONArray subPathList = path.getJSONArray("subPath");

                                for(int s =0 ; s<subPathList.length();s++){
                                    JSONObject subPath = subPathList.getJSONObject(s);
                                    int trafficType = subPath.getInt("trafficType");
                                    sp = new SubPath();
                                    sp.setTrafficType(trafficType);
                                    sp.setDistance(subPath.getDouble("distance"));
                                    sp.setSectionTime(subPath.getInt("sectionTime"));
                                    if(trafficType != 3){
                                        JSONArray laneList = subPath.getJSONArray("lane");
                                        for(int l =0 ; l < laneList.length();l++){
                                            JSONObject lane = laneList.getJSONObject(l);
                                            if(trafficType==2){
                                                Lane busLane =new Lane(lane.getString("busNo")
                                                        ,lane.getInt("type")
                                                        ,lane.getInt("busID"));
                                                sp.addList(busLane);
                                            } // 버스
                                            else if(trafficType==1){
                                                Lane subwayLane = new Lane(lane.getString("name")
                                                        ,lane.getInt("subwayCode")
                                                        ,lane.getInt("subwayCityCode"));
                                                sp.addList(subwayLane);
                                            }//지하철
                                        }
                                        sp.setStationCount(subPath.getInt("stationCount"));
                                        if(trafficType==1){
                                            sp.setWay(subPath.getString("way"));
                                            sp.setWayCode(subPath.getInt("wayCode"));
                                            sp.setDoor(subPath.getString("door"));
                                            if(subPath.getString("startExitNo") != null){
                                                sp.setStartExitNo(new Place(subPath.getString("startExitNo")
                                                        ,subPath.getDouble("startExitY")
                                                        ,subPath.getDouble("startExitX")));
                                            }
                                            if(subPath.getString("endExitNo")!=null){
                                                sp.setStartExitNo(new Place(subPath.getString("endExitNo")
                                                        ,subPath.getDouble("endExitY")
                                                        ,subPath.getDouble("endExitX")));
                                            }
                                        }
                                        sp.setStartStation(new Place(subPath.getString("startName")
                                                ,subPath.getDouble("startY")
                                                ,subPath.getDouble("startX")));
                                        sp.setEndStation(new Place(subPath.getString("endName")
                                                ,subPath.getDouble("endY")
                                                ,subPath.getDouble("endX")));
                                    }
                                    temp.add(sp);
                                }
                                resultSearchPath.add(new SearchPath(path.getInt("pathType"),t,temp));
                            }
                        }// 경로 탐색 결과 있음 X좌표 Y좌표 바뀌어있으니 조심
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(int i, String s, API api) {
                if(api==API.SEARCH_PUB_TRANS_PATH){
                }
            }
        };
        oDsayService.requestSearchPubTransPath(Double.toString(startPoint.get_placeY()),Double.toString(startPoint.get_placeX()),Double.toString(desPoint.get_placeY())
                ,Double.toString(desPoint.get_placeX()),"0","0","0",onResultCallbackListener);
        return resultSearchPath;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            this.startPlace =searchPlace(startPoint);
            this.endPlace =searchPlace(desPoint);
            this.searchResultPath=calRoute1(thisContext,this.startPlace,this.endPlace);//경로 검색
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Place getEndPlace() {
        return endPlace;
    }
    public Place getStartPlace() {
        return startPlace;
    }
    public ArrayList<SearchPath> getSearchResultPath() {
        return searchResultPath;
    }
}
