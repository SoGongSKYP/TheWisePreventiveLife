package com.example.project;
import java.util.*;

/**
 * 
 */
public class NationStatistics extends Statistics {

    /**
     * Default constructor
     */
    public NationStatistics(Date staticsDate,Integer patientNum, Integer deadNum,Integer healerNum,
                            Integer testNum, ArrayList<LocalStatistics> localStatistics,Integer careNum,
                            Integer testNeg, Integer testCnt, Integer testCntComplete, double accDefRate) {
        this.staticsDate=staticsDate;
        this.patientNum=patientNum;
        this.deadNum=deadNum;
        this.healerNum=healerNum;
        this.testNum=testNum;
        this.localStatistics=localStatistics;
        this.careNum=careNum;
        this.testNeg=testNeg;
        this.testCnt=testCnt;
        this.testCntComplete=testCntComplete;
        this.accDefRate=accDefRate;
    }
    private Integer testNum; /*검사진행 수*/
    private ArrayList<LocalStatistics> localStatistics;/*지역별 통계*/
    private Integer careNum; /*치료중 환자수*/
    private Integer testNeg;/*결과 음성 수*/
    private Integer testCnt; /*누적 검사 수*/
    private Integer testCntComplete; /*누적 검사 완료수*/
    private double accDefRate; /*누적 확진률*/

    public Date getStaticsDate(){
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

    public ArrayList<LocalStatistics> getLocalStatistics() {
        return localStatistics;
    }
    public double getAccDefRate() {
        return accDefRate;
    }
    public Integer getCareNum() {
        return careNum;
    }
    public Integer getTestCnt() {
        return testCnt;
    }
    public Integer getTestCntComplete() {
        return testCntComplete;
    }
    public Integer getTestNeg() {
        return testNeg;
    }
    public Integer getTestNum() {
        return testNum;
    }

    public void make_chart() {
        // TODO implement here
    }

    /**
     * 
     */
}
