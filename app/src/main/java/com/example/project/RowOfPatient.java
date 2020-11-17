package com.example.project;

import java.util.Date;

public class RowOfPatient {
    public RowOfPatient(String patientNum, String confirmDate) {
        this.patientNum = patientNum;
        this.confirmDate = confirmDate;
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

    String patientNum;
    String confirmDate;
}
