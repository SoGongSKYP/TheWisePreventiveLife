package com.example.project;
import java.sql.Connection;
import java.util.*;

/**
 * 
 */
public class DB {

    /**
     * Default constructor
     */
    public DB() {
    }

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String pw;

    /**
     * 
     */
    private Connection conn;

    /**
     * 
     */
    private ArrayList<Patient> patients;

    /**
     * @return
     */
    public ArrayList<Patient> bring_patient_infoNroute() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void connect_DB() {
        // TODO implement here
    }

    public boolean update_DB(ArrayList<Patient> patientslist) {
        // TODO implement here
        return true;
    }
}