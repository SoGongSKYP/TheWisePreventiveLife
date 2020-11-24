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

public class CalRoute {
    private Place startPlace;
    private Place endPlace;
    private ArrayList<SearchPath> searchResultPath;
    private Context thisContext;

    CalRoute(Context context,Place startPlace, Place endPlace){
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.searchResultPath = new ArrayList<SearchPath>();
        this.thisContext=context;
    }
    public void calRoute1() throws IOException, ParserConfigurationException, SAXException {
        ODsayService oDsayService=ODsayService.init(thisContext,"cDSdUY9qLmrLpcqsJL3zPvgpx3IgkOf4sLsbkzSOZ2Y");
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
                                            if(!subPath.isNull("startExitNo")){
                                                sp.setStartExitNo(new Place(subPath.getString("startExitNo")
                                                        ,subPath.getDouble("startExitY")
                                                        ,subPath.getDouble("startExitX")));
                                            }
                                            if(!subPath.isNull("endExitNo")){
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
                                searchResultPath.add(new SearchPath(path.getInt("pathType"),t,temp));
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
        oDsayService.requestSearchPubTransPath(Double.toString(startPlace.get_placeY()),Double.toString(startPlace.get_placeX()),Double.toString(endPlace.get_placeY())
                ,Double.toString(endPlace.get_placeX()),"0","0","0",onResultCallbackListener);
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
