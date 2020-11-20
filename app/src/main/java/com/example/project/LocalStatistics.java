package com.example.project;
import java.util.*;

/**
 * 
 */
public class LocalStatistics extends Statistics {

    /**
     * Default constructor
     */
    public LocalStatistics(String staticsDate,Integer patientNum,Integer deadNum,Integer healerNum,String localName,Place localPosition, Integer increaseDecrease) {
    this.staticsDate=staticsDate;
    this.patientNum=patientNum;
    this.deadNum =deadNum;
    this.healerNum =healerNum;
    this.localName =localName;
    this.localPosition =localPosition;
    this.increaseDecrease=increaseDecrease;
    }
    private String localName; /*지역 이름*/
    private Place localPosition; /*지역 시청 or 군청 좌표*/
    private Integer increaseDecrease; /*전일 대비 증감 수*/

    public Place getLocalPosition() {
        return this.localPosition;
    }
    public String getLocalName() {
        return this.localName;
    }
    public String getStaticsDate(){
        return this.staticsDate;
    }
    public Integer getPatientNum() {
        return this.patientNum;
    }
    public Integer getDeadNum(){
        return this.deadNum;
    }
    public Integer getHealerNum() {
        return this.healerNum;
    }
    public Integer getIncreaseDecrease() {
        return increaseDecrease;
    }

    public void make_chart() {
        // TODO implement here
    }


}
