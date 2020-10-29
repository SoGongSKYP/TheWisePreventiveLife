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

    private Date staticsDate;
    private Integer patientNum;
    private Integer deadNum;
    private Integer healerNum;

    public abstract void make_chart();

}
