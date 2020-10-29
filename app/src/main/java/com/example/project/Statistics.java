package com.example.project;
import java.util.*;

/**
 * 
 */
abstract class Statistics {

    /**
     * Default constructor
     */
    public Statistics() {
    }

    /**
     * 
     */
    public Date statics_date;

    /**
     * 
     */
    public int patientNum;

    /**
     * 
     */
    public int deadNum;

    /**
     * 
     */
    public int healerNum;

    /**
     * 
     */
    public abstract void make_chart();

}