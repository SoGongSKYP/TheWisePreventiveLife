package com.example.project;
import java.sql.Connection;
import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DB {
    private ArrayList<Patient> patients;
    private  URL url;
    private HttpURLConnection conn;

    public DB() throws MalformedURLException {
        url = new URL("http://192.168.1.196:8080/WLP/androidDB.jsp");
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

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
    public boolean check_user_idpw(String id, String pw){
        return true;
    }
}