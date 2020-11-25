package com.example.project;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBEntity{
    private static ArrayList<Patient> patientList;

    private DB task;
    private String result="success";//일단 임시 success
    private String sendmsg;

    public DBEntity(){ }
    public void setPatientList(ArrayList<Patient> patientList){
        this.patientList = patientList;
    }
    public static ArrayList<Patient> getPatientList(){
        return patientList;
    }

    /*인트로 때, 딱 한 번 불리는 함*/
    public ArrayList<Patient>  patient_info(){
        task= new DB("patients_info");
        return patientList;
    }

    public int check_patient(String pnum, String plocnum){
        if(result.equals("success")) return 1;
        else return 0;
    }

    public int insert_patient(Patient patient){
        if(result.equals("success")) return 1;
        else return 0;
    }

    public int delete_patient(Patient patient){
        if(result.equals("success")) return 1;
        else return 0;
    }

    public int login(String managerID, String managerPW){
        try {
            sendmsg = "login";
            task = new DB(sendmsg);
            result = task.execute(managerID, managerPW, sendmsg).get();
            Log.i("Servertest", "서버에서 받은 값" + result);

            if (result.equals("success")) return 1;
            else if (result.equals("failed")) return 0;
            else if (result.equals("noId")) return 2;
            else return -1;
        }catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            return -2;}
    }
}
