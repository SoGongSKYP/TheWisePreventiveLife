package com.example.project;
import java.util.*;

/**
 * 
 */
public class Patient {


    public Patient(int smallLocalNum, int bigLocalNum, String patientNum, Date confirmDate, ArrayList<VisitPlace> visitPlaceList) {
        this.smallLocalNum = smallLocalNum;
        this.bigLocalNum = bigLocalNum;
        this.patientNum = patientNum;
        this.confirmDate = confirmDate;
        this.visitPlaceList = visitPlaceList;
    }

    public int getSmallLocalNum() {
        return smallLocalNum;
    }

    public void setSmallLocalNum(int smallLocalNum) {
        this.smallLocalNum = smallLocalNum;
    }

    public int getBigLocalNum() {
        return bigLocalNum;
    }

    public void setBigLocalNum(int bigLocalNum) {
        this.bigLocalNum = bigLocalNum;
    }

    public String getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public ArrayList<VisitPlace> getVisitPlaceList() {
        return visitPlaceList;
    }

    public void setVisitPlaceList(ArrayList<VisitPlace> visitPlaceList) {
        this.visitPlaceList = visitPlaceList;
    }

    private int smallLocalNum;
    private int bigLocalNum;
    private String patientNum;
    private Date confirmDate;
    private ArrayList<VisitPlace> visitPlaceList;


}
