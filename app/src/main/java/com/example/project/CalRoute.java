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
            }
        }
        return searchLoc;
    }
    private ArrayList<SearchPath> calRoute1(Context context, Place startPoint, Place desPoint ) throws IOException, ParserConfigurationException, SAXException {
        ODsayService oDsayService=ODsayService.init(context,"cDSdUY9qLmrLpcqsJL3zPvgpx3IgkOf4sLsbkzSOZ2Y");
        final ArrayList<SearchPath> resultSearchPath=new ArrayList<SearchPath>();
        final ArrayList<SubPath> temp =new ArrayList<SubPath>();

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                try{
                    if(api==API.SEARCH_PUB_TRANS_PATH){
                        int localSearch = oDsayData.getJson().getJSONObject("result").getInt("localSearch");
                        if(localSearch==1){
                            int totalCount = oDsayData.getJson().getJSONObject("result").getInt("totalCount");//총 경로 결과 개수
                            for(int i =0; i < totalCount;i++){
                                JSONObject path = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(i);
                                ExtendNode t = new ExtendNode(path.getJSONObject("Info").getDouble("trafficDistance")
                                        ,path.getJSONObject("Info").getInt("totalWalk")
                                        ,path.getJSONObject("Info").getInt("totalTime")
                                        ,path.getJSONObject("Info").getInt("payment")
                                        ,path.getJSONObject("Info").getInt("busTransitCount")
                                        ,path.getJSONObject("Info").getInt("subwayTransitCount")
                                        ,path.getJSONObject("Info").getString("mapObj")
                                        ,path.getJSONObject("Info").getString("firstStartStation")
                                        ,path.getJSONObject("Info").getString("lastEndStation")
                                        ,path.getJSONObject("Info").getInt("totalStationCount")
                                        ,path.getJSONObject("Info").getInt("totalDistance"));
                                JSONArray subPathList = path.getJSONArray("subPath");
                                for(int s =0 ; s<subPathList.length();s++){
                                    JSONObject subPath = subPathList.getJSONObject(s);
                                    int trafficType = subPath.getInt("trafficType");
                                    double distance= subPath.getDouble("distance");
                                    int sectionTime=subPath.getInt("sectionTime");
                                    if(trafficType==3){
                                        SubPath sp = new SubPath();
                                        sp.setTrafficType(trafficType);
                                        sp.setDistance(distance);
                                        sp.setSectionTime(sectionTime);
                                        temp.add(sp);
                                    }
                                    else if(trafficType==2){
                                        SubPath sp = new SubPath();
                                    }
                                    else if(trafficType==1){

                                    }
                                }
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
        oDsayService.requestSearchPubTransPath(Double.toString(startPoint.get_placeX()),Double.toString(startPoint.get_placeY()),Double.toString(desPoint.get_placeX())
        ,Double.toString(desPoint.get_placeY()),"0","0","0",onResultCallbackListener);

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
