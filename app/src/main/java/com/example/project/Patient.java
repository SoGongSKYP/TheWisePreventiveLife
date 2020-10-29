package com.example.project;
import java.util.*;

/**
 * 
 */
public class Patient {

    /**
     * Default constructor
     */
    public Patient(Place home, String localNum, String patientNum, Date confirmDate, ArrayList<VisitPlace> visitPlaceList) {
        this.home = home;
        this.localNum= localNum;
        this.confirmDate =confirmDate;
        this.patientNum = patientNum;
        this.visitPlaceList = visitPlaceList;
    }

    private Place home;
    private String localNum;
    private String patientNum;
    private Date confirmDate;
    private ArrayList<VisitPlace> visitPlaceList;

    public Place getHome() {
        return home;
    }
    public Date getConfirmDate() {
        return confirmDate;
    }
    public String getPatientNum() {
        return patientNum;
    }
    public String getLocalNum() {
        return localNum;
    }
    public ArrayList<VisitPlace> getVisitPlaceList() {
        return visitPlaceList;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }
    public void setHome(Place home) {
        this.home = home;
    }
    public void setLocalNum(String localNum) {
        this.localNum = localNum;
    }
    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }
    public void setVisitPlaceList(ArrayList<VisitPlace> visitPlaceList) {
        this.visitPlaceList = visitPlaceList;
    }
}
