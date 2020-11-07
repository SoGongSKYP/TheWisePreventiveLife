package com.example.project;
import android.os.AsyncTask;

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
   // private ArrayList<Patient> patients;
   // private  URL url;
    //private HttpURLConnection conn;
    String sendMsg, receiveMsg;


    public void connect_DB() {
    }

    /**
     * @return
     */
    public ArrayList<Patient> bring_patient_infoNroute() {
        // TODO implement here
        return null;
    }
    public boolean update_DB(ArrayList<Patient> patientslist) {
        // TODO implement here
        return true;
    }
    public boolean check_user_idpw(String id, String pw) throws IOException {

return true;
}

    @Override
    protected String doInBackground(String... strings) {

        try {
            String str;

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://192.168.1.196:8080/WLP/androidDB.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            sendMsg = "id=" + strings[0] + "&pw=" + strings[1];

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