package com.example.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBEntity{
    private static ArrayList<Patient> patientList;
   // private DB db;


    public void setPatientList(ArrayList<Patient> patientList){
        this.patientList = patientList;
    }
    public static ArrayList<Patient> getPatientList(){
        return patientList;
    }

    public DBEntity() throws ParseException {
        //1. 더미 데이트 객체 생성
        String date1 = "2020-10-21";
        String date2 = "2020-12-12";
        //2. 더미 장소 객체 생성
        Place dumP1=new Place("파주",1.1,1.2);
        Place dumP2=new Place("서울",2.1,2.2);

        //3. 1,2로 확진자 방문장소 더미객체 생성
        VisitPlace dumVP1=new VisitPlace(dumP1,date1);
        VisitPlace dumVP2=new VisitPlace(dumP2,date1);
        VisitPlace dumVP3=new VisitPlace(dumP1,date2);
        //4. 3으로 확진자 방문장소 리스트 더미객체 생
        ArrayList<VisitPlace> dumVSP1=new ArrayList<VisitPlace>();
        ArrayList<VisitPlace> dumVSP2=new ArrayList<VisitPlace>();
        dumVSP1.add(dumVP1);
        dumVSP1.add(dumVP2);
        dumVSP2.add(dumVP2);
        dumVSP2.add(dumVP3);
        //5. 확진자 두명
        Patient dumPatient=new Patient(0,12,"1",date2,dumVSP1);
        Patient dumPatient2=new Patient(0,1,"2",date1,dumVSP2);
        patientList.add(dumPatient);
        patientList.add(dumPatient2);


    }
}
