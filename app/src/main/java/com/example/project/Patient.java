package com.example.project;
import java.util.*;

/**
 * 
 */
public class Patient {

    /**
     * Default constructor
     */
    public Patient(String smallLocalNum, String bigLocalNum, String patientNum, Date confirmDate, ArrayList<VisitPlace> visitPlaceList) {
        this.smallLocalNum = smallLocalNum;
        this.bigLocalNum= bigLocalNum;
        this.confirmDate =confirmDate;
        this.patientNum = patientNum;
        this.visitPlaceList = visitPlaceList;
    }

    private String smallLocalNum;
    private String bigLocalNum;
    private String patientNum;
    private Date confirmDate;
    private ArrayList<VisitPlace> visitPlaceList;

    public String getSmallLocalNum() { return smallLocalNum; }
    public Date getConfirmDate() {
        return confirmDate;
    }
    public String getPatientNum() {
        return patientNum;
    }
    public String getBigLocalNum() {
        return bigLocalNum;
    }
    public ArrayList<VisitPlace> getVisitPlaceList() {
        return visitPlaceList;
    }

    public void setSmallLocalNum(String smallLocalNum) { this.smallLocalNum = smallLocalNum; }
    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }
    public void setBigLocalNum(String localNum) {
        this.bigLocalNum = bigLocalNum;
    }
    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }
    public void setVisitPlaceList(ArrayList<VisitPlace> visitPlaceList) {
        this.visitPlaceList = visitPlaceList;
    }
}
