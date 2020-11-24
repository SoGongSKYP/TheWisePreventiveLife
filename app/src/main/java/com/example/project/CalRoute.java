package com.example.project;

import android.content.Context;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class CalRoute {
    private Place startPlace;
    private Place endPlace;
    private ArrayList<SearchPath> searchResultPath;
    private Context thisContext;
    private Object lock = new Object();
    public CalRoute(Context context,Place startPlace, Place endPlace){
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.searchResultPath = new ArrayList<SearchPath>();
        this.thisContext=context;
    }
    public ArrayList<SearchPath> calRoute1() throws InterruptedException {
        final ODsayService oDsayService=ODsayService.init(thisContext,"cDSdUY9qLmrLpcqsJL3zPvgpx3IgkOf4sLsbkzSOZ2Y");
        // 서버 연결 제한 시간(단위(초), default : 5초)
        oDsayService.setReadTimeout(5000);
        // 데이터 획득 제한 시간(단위(초), default : 5초)
        oDsayService.setConnectionTimeout(5000);

        /*
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                //ArrayList<SearchPath> ResultPath = new ArrayList<SearchPath>();
                    searchResultPath = new ArrayList<SearchPath>();
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
                                    JSONArray subPathList = path.getJSONArray("subPath");
                                    ArrayList<SubPath> temp =new ArrayList<SubPath>();
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
                                    searchResultPath.add(new SearchPath(path.getInt("pathType"),new ExtendNode(path.getJSONObject("info").getDouble("trafficDistance")
                                            ,path.getJSONObject("info").getInt("totalWalk")
                                            ,path.getJSONObject("info").getInt("totalTime")
                                            ,path.getJSONObject("info").getInt("payment")
                                            ,path.getJSONObject("info").getInt("busTransitCount")
                                            ,path.getJSONObject("info").getInt("subwayTransitCount")
                                            ,path.getJSONObject("info").getString("mapObj")
                                            ,path.getJSONObject("info").getString("firstStartStation")
                                            ,path.getJSONObject("info").getString("lastEndStation")
                                            ,path.getJSONObject("info").getInt("totalStationCount")
                                            ,path.getJSONObject("info").getInt("totalDistance")),temp));
                                }
                            }// 경로 탐색 결과 있음 X좌표 Y좌표 바뀌어있으니 조심
                            System.out.println("안쪽에서의 사이즈1:"+ searchResultPath.size());
                            //System.out.println("안쪽에서의 사이즈2:"+ ResultPath.size());
                        }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                    System.out.println("드디어 끝났다");
                    System.out.println("중간쪽에서의 사이즈:"+ searchResultPath.size());}

            @Override
            public void onError(int i, String s, API api) {
                if(api==API.SEARCH_PUB_TRANS_PATH){
                }
            }
        };
    */
        final ResultCallbackListener resultCallbackListener = new ResultCallbackListener(lock);

        oDsayService.requestSearchPubTransPath(Double.toString(startPlace.get_placeY()),Double.toString(startPlace.get_placeX()),Double.toString(endPlace.get_placeY())
                ,Double.toString(endPlace.get_placeX()),"0","0","0",resultCallbackListener);
        Thread.sleep(1000);
        searchResultPath=resultCallbackListener.getResultPath();
        System.out.println("바깥쪽에서의 사이즈:"+ searchResultPath.size());
        return searchResultPath;

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

    class ResultCallbackListener implements OnResultCallbackListener{
        private  ArrayList<SearchPath> ResultPath;
        private Object lock = new Object();
        public ResultCallbackListener(Object lock){
            this.ResultPath = new ArrayList<SearchPath>();
            this.lock=lock;
        }
        public ArrayList<SearchPath> getResultPath() {
                return ResultPath;

        }
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
                                JSONArray subPathList = path.getJSONArray("subPath");
                                ArrayList<SubPath> temp =new ArrayList<SubPath>();
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
                                ResultPath.add(new SearchPath(path.getInt("pathType"),new ExtendNode(path.getJSONObject("info").getDouble("trafficDistance")
                                        ,path.getJSONObject("info").getInt("totalWalk")
                                        ,path.getJSONObject("info").getInt("totalTime")
                                        ,path.getJSONObject("info").getInt("payment")
                                        ,path.getJSONObject("info").getInt("busTransitCount")
                                        ,path.getJSONObject("info").getInt("subwayTransitCount")
                                        ,path.getJSONObject("info").getString("mapObj")
                                        ,path.getJSONObject("info").getString("firstStartStation")
                                        ,path.getJSONObject("info").getString("lastEndStation")
                                        ,path.getJSONObject("info").getInt("totalStationCount")
                                        ,path.getJSONObject("info").getInt("totalDistance")),temp));
                            }
                        }// 경로 탐색 결과 있음 X좌표 Y좌표 바뀌어있으니 조심
                        System.out.println("안쪽에서의 사이즈1:"+ ResultPath.size());
                        //System.out.println("안쪽에서의 사이즈2:"+ ResultPath.size());
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }
                System.out.println("드디어 끝났다");
                System.out.println("중간쪽에서의 사이즈:"+ ResultPath.size());

        }

        @Override
        public void onError(int i, String s, API api) {

        }
    }
}
