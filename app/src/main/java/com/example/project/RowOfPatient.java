package com.example.project;

import java.util.Date;

public class RowOfPatient {


    public RowOfPatient(String patientNum, String confirmDate, int bigLocalNum, int smallLocalNum) {
        this.patientNum = patientNum;
        this.confirmDate = confirmDate;
        this.bigLocalNum = bigLocalNum;
        this.smallLocalNum = smallLocalNum;
    }

    public String getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(String patientNum) {
        this.patientNum = patientNum;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public int getBigLocalNum() {
        return bigLocalNum;
    }

    public void setBigLocalNum(int bigLocalNum) {
        this.bigLocalNum = bigLocalNum;
    }

    public int getSmallLocalNum() {
        return smallLocalNum;
    }

    public void setSmallLocalNum(int smallLocalNum) {
        this.smallLocalNum = smallLocalNum;
    }

    String patientNum;
    String confirmDate;
    int bigLocalNum;
    int smallLocalNum;
}
