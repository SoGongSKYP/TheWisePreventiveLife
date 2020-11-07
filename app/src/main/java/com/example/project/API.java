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
    	String parsingUrl="";
    	
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=0eMMHHcbnpAK1eXmexxzB4pMr9lfDCq4Tl6P4wh2DrYWPkvQfiB0u9Vr5mMh39H6x63xk%2FesCnLgUfMbHBQV8g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode("20200310", "UTF-8")); /*검색할 생성일 범위의 시작*/
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
				System.out.println("누적 확진률 : "+getTagValue("accDefRate",eElement));
				System.out.println("누적 검사 수"+getTagValue("accExamCnt",eElement));
				System.out.println("누적 검사 완료 수"+getTagValue("accExamCompCnt",eElement));
				System.out.println("치료중 환자 수"+getTagValue("careCnt",eElement));
				System.out.println("격리해제 수"+getTagValue("clearCnt",eElement));
				System.out.println("등록일시분초"+getTagValue("createDt",eElement));
				System.out.println("사망자 수"+getTagValue("deathCnt",eElement));
				System.out.println("확진자 수"+getTagValue("decideCnt",eElement));
				System.out.println("검사진행 수"+getTagValue("examCnt",eElement));
				System.out.println("결과 음성 수"+getTagValue("resutlNegCnt",eElement));
				System.out.println("게시글 번호"+getTagValue("seq",eElement));
				System.out.println("기준일"+getTagValue("stateDt",eElement));
				System.out.println("기준시간"+getTagValue("stateTime",eElement));
				System.out.println("수정일시분초"+getTagValue("updateDt",eElement));
				System.out.println();
			}
		}
        
        
    }
    public void local_API() throws IOException, ParserConfigurationException, SAXException {
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
		
		for(int i=0; i<nList.getLength(); i++) {
			Node nNode=nList.item(i);
			if(nNode.getNodeType()==Node.ELEMENT_NODE) {
				Element eElement=(Element) nNode;
				System.out.println("등록일시분초"+getTagValue("createDt",eElement));
				System.out.println("사망자 수"+getTagValue("deathCnt",eElement));
				System.out.println("확진자 수"+getTagValue("defCnt",eElement));
				System.out.println("시도명(한글)"+getTagValue("gubun",eElement));
				System.out.println("시도명(영어)"+getTagValue("gubunEn",eElement));
				System.out.println("전일대비 증감수"+getTagValue("incDec",eElement));
				System.out.println("격리 해제 수"+getTagValue("isolClearCnt",eElement));
				System.out.println("격리중 환자 수"+getTagValue("isolIngCnt",eElement));
				System.out.println("지역발생 수"+getTagValue("localOccCnt",eElement));
				System.out.println("해외유입 수"+getTagValue("overFlowCnt",eElement));
				System.out.println("10만명당 발생률"+getTagValue("qurRate",eElement));
				System.out.println("게시글번호"+getTagValue("seq",eElement));
				System.out.println("기준일시"+getTagValue("stdDay",eElement));
				System.out.println("수정일시분초"+getTagValue("updateDt",eElement));
				System.out.println();
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
		a.nation_API();
		a.local_API();
	}

}
