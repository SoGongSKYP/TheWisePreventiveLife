package com.example.project;
/*
1-9-3-23 passStopList 경로 상세구간 정보 확장 노드(국문에 한하여 제공)
1-9-3-23-1 stations 정류장 정보 그룹노드
 */
public class Stations {

    Stations(int index, int stationID, Place station){
        this.index =index;
        this.stationID=stationID;
        this.station =station;
    }
    private int index; //정류장 순번
    private int stationID; //정류장 ID
    private Place station;
    /*
    1-9-3-23-1-3	stationName	정류장 명칭
    1-9-3-23-1-4	x		정류장 X좌표
    1-9-3-23-1-5	y		정류장 Y좌표
     */

    public int getIndex() {
        return this.index;
    }
    public int getStationID() {
        return this.stationID;
    }
    public Place getStation() {
        return this.station;
    }
}
