package com.example.project;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ProtocolException;
import java.sql.Connection;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DB  extends AsyncTask<String, Void, String> {
    /*원래 설했던 필드들*/
     private ArrayList<Patient> patients;
    // public void connect_DB() { }
     public ArrayList<Patient> bring_patient_infoNroute() { return null; }
     public boolean update_DB(ArrayList<Patient> patientslist) { return true; }
     public boolean check_user_idpw(String id, String pw) throws IOException { return true;}

    //서버로 보낼 메세지, 받을 메세
    String sendMsg, receiveMsg;
    // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
    String serverip="http://172.20.10.7:8080/WLP/androidDB.jsp";

    //클래스 생성자
    DB(String sendMsg){
        this.sendMsg=sendMsg;
    }
    //
    @Override
    protected String doInBackground(String... strings) {

        try {
            String str;
            URL url = new URL(serverip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            /**
             *  전송할 데이터. POST 방식으로 작성---sendMsg에따라서 디비에 요청하는 메세지가 달라
             * if(sendMsg.equals("vision_write")){
             *                 sendMsg = "vision_write="+strings[0]+"&type="+strings[1];
             *             }else if(sendMsg.equals("vision_list")){
             *                 sendMsg = "&type="+strings[0];
             *             }
             **/

            if(sendMsg.equals("test_write")){
            sendMsg = "id=" + strings[0] + "&pw=" + strings[1]+"&type="+"test_write";}

            //안드로이드에서 jsp 서버로 통신값 보내
            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                // jsp에서 보낸 값을 받는 부분
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            } else {
                // 통신 실패
                Log.i("통신 결과", conn.getResponseCode()+"에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return receiveMsg;
    }
}