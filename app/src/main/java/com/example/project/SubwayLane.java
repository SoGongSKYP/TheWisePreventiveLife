package com.example.project;

import java.util.ArrayList;
/*
 * 1-9-3-5 교통 수단 정보 확장 노드 지하철용 1..n
 */
public class SubwayLane {
    SubwayLane(String name, int subwayCode, int subwayCityCode,int index){
        this.name =name;
        this.subwayCode = subwayCode;
        this.subwayCityCode =subwayCityCode;
        this.index = index;
    }
    private int index;
    private String name; //지하철 노선명 (지하철인 경우에만 필수)
    private int subwayCode; //지하철 노선 번호 (지하철인 경우에만 필수)
    private int subwayCityCode; //지하철 도시코드 (지하철인 경우에만 필수)

    public int getSubwayCityCode() {
        return subwayCityCode;
    }
    public int getSubwayCode() {
        return subwayCode;
    }
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSubwayCityCode(int subwayCityCode) {
        this.subwayCityCode = subwayCityCode;
    }
    public void setSubwayCode(int subwayCode) {
        this.subwayCode = subwayCode;
    }
}
