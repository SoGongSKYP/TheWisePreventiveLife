package com.example.project;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * version1
 */
public class API {

    /**
     * Default constructor
     */
    private SimpleDateFormat mFormat;
    private Date currentDate;
    private String currDate;

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList=eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue=(Node)nlList.item(0);
        if(nValue==null) return null;
        return nValue.getNodeValue();
    }

    public API() {
        this.mFormat = new SimpleDateFormat("yyyyMMdd"); // 검색할 생성일 범위를 지정하기위한 현재 날짜 받아오기 위한 형식지정
        this.currentDate = Calendar.getInstance().getTime();
        this.currDate = this.mFormat.format(this.currentDate); // 현재 날짜 저장
    }

    public NationStatistics nationAPI(ArrayList<LocalStatistics> localList) throws IOException, ParserConfigurationException, SAXException, ParseException {
        NationStatistics nation =null;
        String parsingUrl="";

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 종료*/

        URL url = new URL(urlBuilder.toString());
        //System.out.println(sb.toString());

        parsingUrl=url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList nList=doc.getElementsByTagName("item");
        //System.out.println("파싱할 리스트 수 : "+nList.getLength());

        for(int i=0; i<nList.getLength(); i++) {
            Node nNode=nList.item(i);
            if(nNode.getNodeType()==Node.ELEMENT_NODE) {
                Element eElement=(Element) nNode;

                String from = getTagValue("stateDt",eElement) + getTagValue("stateTime",eElement);
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHH:mm");
                Date to = transFormat.parse(from);
                SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 기준");
                String stateDate = transFormat2.format(to);

                nation=new NationStatistics(stateDate,Integer.parseInt(getTagValue("decideCnt",eElement))
                        ,Integer.parseInt(getTagValue("deathCnt",eElement)),Integer.parseInt(getTagValue("clearCnt",eElement)),
                        Integer.parseInt(getTagValue("examCnt",eElement)),localList,Integer.parseInt(getTagValue("careCnt",eElement)),
                        Integer.parseInt(getTagValue("resutlNegCnt",eElement)), Integer.parseInt(getTagValue("accExamCnt",eElement))
                        , Integer.parseInt(getTagValue("accExamCompCnt",eElement)), Double.parseDouble(getTagValue("accDefRate",eElement)));
            }
        }
        //전국통계 객체 생성
        return nation;
    }
    public  ArrayList<LocalStatistics> localAPI() throws IOException, ParserConfigurationException, SAXException, ParseException {
        ArrayList<LocalStatistics> localList = new ArrayList<LocalStatistics>();
        String parsingUrl="";

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("19", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        //System.out.println(sb.toString());

        parsingUrl=url.toString();
        //System.out.println(parsingUrl);

        DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
        Document doc=dBuilder.parse(parsingUrl);

        doc.getDocumentElement().normalize();
        //System.out.println("Root element : "+doc.getDocumentElement().getNodeName());

        NodeList nList=doc.getElementsByTagName("item");
        //System.out.println("파싱할 리스트 수 : "+nList.getLength());

        for(int i=0; i<nList.getLength(); i++) {
            Node nNode=nList.item(i);
            if(nNode.getNodeType()==Node.ELEMENT_NODE) {
                Element eElement=(Element) nNode;
                if(getTagValue("gubun",eElement)=="검역"){
                    localList.add(new LocalStatistics(getTagValue("stdDay",eElement),Integer.parseInt(getTagValue("isolIngCnt",eElement))
                            ,Integer.parseInt(getTagValue("deathCnt",eElement)),Integer.parseInt(getTagValue("isolClearCnt",eElement))
                            ,getTagValue("gubun",eElement),Integer.parseInt(getTagValue("incDec",eElement))
                            ,Integer.parseInt(getTagValue("localOccCnt",eElement)),Integer.parseInt(getTagValue("overFlowCnt",eElement))
                            ,0.0));
                }
                else{
                    localList.add(new LocalStatistics(getTagValue("stdDay",eElement),Integer.parseInt(getTagValue("isolIngCnt",eElement))
                            ,Integer.parseInt(getTagValue("deathCnt",eElement)),Integer.parseInt(getTagValue("isolClearCnt",eElement))
                            ,getTagValue("gubun",eElement),Integer.parseInt(getTagValue("incDec",eElement))
                            ,Integer.parseInt(getTagValue("localOccCnt",eElement)),Integer.parseInt(getTagValue("overFlowCnt",eElement))
                            ,Double.parseDouble(getTagValue("qurRate",eElement))));
                }

            }
        }
        return localList;
    }
}
