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
import java.text.SimpleDateFormat;
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

   public void nation_API() throws IOException, ParserConfigurationException, SAXException{
        Date StaticsDate;
        Integer patientNum;
        Integer deadNum;
        Integer healerNum;
        Integer testNum;
        ArrayList<LocalStatistics> localStatistics;
        Integer careNum;
        Integer testNeg;
        Integer testCnt;
        Integer testCntComplete;
        double accDefRate;

    	String parsingUrl="";
    	
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 종료*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
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
                //string을 date로 변환
                String stateDt=getTagValue("stateDt",eElement);
                SimpleDateFormat transFormat=new SimpleDateFormat("yyyy-MM-dd");
                staticsDate=transFormat.parse(stateDt);

                patientNum=Integer.parseInt(getTagValue("decideCnt",eElement));
                deadNum=Integer.parseInt(getTagValue("deathCnt",eElement));
                healerNum=Integer.parseInt(getTagValue("clearCnt",eElement));
                testNum=Integer.parseInt(getTagValue("examCnt",eElement));
                //localstatics,,>
                careNum=Integer.parseInt(getTagValue("careCnt",eElement));
                testNeg=Integer.parseInt(getTagValue("resutlNegCnt",eElement));
                testCnt=Integer.parseInt(getTagValue("accExamCnt",eElement));
                testCntComplete=Integer.parseInt(getTagValue("accExamCompCnt",eElement));
                accDefRate=Double.parseDouble(getTagValue("accDefRate",eElement));

            }
		}

		//전국통계 객체 생성
       NationStatics nation=new NationStatics(NationStatistics(staticsDate,patientNum, deadNum, healerNum,testNum,
                                            localStatistics,careNum,testNeg, testCnt,testCntComplete,accDefRate));

   }
    public void local_API() throws IOException, ParserConfigurationException, SAXException {
        Date staticsDate;
        Integer patientNum;
        Integer deadNum;
        Integer healerNum;
        String localName;
        Place localPosition;
        Integer increaseDecrease;

    	String parsingUrl="";

        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode("20200410", "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(this.currDate, "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
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

        ArrayList<LocalStatistics> localStatistics;

		for(int i=0; i<nList.getLength(); i++) {
			Node nNode=nList.item(i);
			if(nNode.getNodeType()==Node.ELEMENT_NODE) {
				Element eElement=(Element) nNode;
                String stateDt=getTagValue("stdDay",eElement);
                SimpleDateFormat transFormat=new SimpleDateFormat("yyyy-MM-dd");
                staticsDate=transFormat.parse(stateDt);

                patientNum=Integer.parseInt(getTagValue("defCnt",eElement));
                deadNum=Integer.parseInt(getTagValue("deathCnt",eElement));
                healerNum=Integer.parseInt(getTagValue("isolClearCnt",eElement));
                localName=getTagValue("gubun",eElement);
                //localPosition;
                increaseDecrease=Integer.parseInt(getTagValue("incDec",eElement));
                //지역통계
                localStatistics[i]=new LocalStatistics(Date staticsDate,Integer patientNum,Integer deadNum,Integer healerNum,
                        String localName,Place localPosition, Integer increaseDecrease)

			}
		}
    }

    public void clinic_API() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("spclAdmTyCd","UTF-8") + "=" + URLEncoder.encode("A0", "UTF-8")); /*A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		API a=new API();

	}
}
