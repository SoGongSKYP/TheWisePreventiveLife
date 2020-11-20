package com.example.project;

import java.util.ArrayList;

public class DBEntity{
    private static ArrayList<Patient> patientList;
    private DB db;
    public DBEntity(ArrayList<Patient> patientList) {
        this.patientList = patientList;//여기서 DB로 환자 불러오는 함수 호출
    }
}
