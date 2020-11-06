package com.example.project;

import java.util.ArrayList;
/*
 * 1-9-3-5 교통 수단 정보 확장 노드 버스용 1..n
 */
public class BusLane {
    BusLane(String busNo, int type, int busID, int index){
        this.busID=busID;
        this.busNo=busNo;
        this.type=type;
        this.index=index;
    }
    private int index;
    private String busNo; //버스 번호 (버스인 경우에만 필수)
    private int type; //버스 코드 (버스인 경우에만 필수)
    private int busID; //버스 코드 (버스인 경우에만 필수)

    public int getBusID() {
        return busID;
    }
    public int getType() {
        return type;
    }
    public String getBusNo() {
        return busNo;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public void setBusID(int busID) {
        this.busID = busID;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public void setType(int type) {
        this.type = type;
    }

}
