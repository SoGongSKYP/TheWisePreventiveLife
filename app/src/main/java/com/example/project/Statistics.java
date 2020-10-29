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

    public Date staticsDate;
    public Integer patientNum;
    public Integer deadNum;
    public Integer healerNum;

    public abstract void make_chart();

}
