package com.example.project;

import java.util.ArrayList;


/*
 * 1-9-3 이동 교통 수단 정보 확장 노드 1..n
 */
public class SubPath {
    SubPath(int trafficType, double distance, int sectionTime, int stationCount, ArrayList<SubwayLane> subwayLanes,
            ArrayList<BusLane> busLanes, Place startStation, Place endStation, String way,
            int wayCode, String door, int startID, int endID, Place startExitNo, Place endExitNo,
            ArrayList<Stations> stations){
        this.trafficType =trafficType;
        this.distance=distance;
        this.sectionTime=sectionTime;
        this.stationCount=stationCount;
        this.subwayLanes=subwayLanes;
        this.busLanes=busLanes;
        this.startStation =startStation;
        this.endStation=endStation;
        this.way=way;
        this.wayCode=wayCode;
        this.door=door;
        this.startID=startID;
        this.endID=endID;
        this.startExitNo=startExitNo;
        this.endExitNo=endExitNo;
        this.stations=stations;

    }
    private int trafficType; //이동 수단 종류 (도보, 버스, 지하철) 1-지하철, 2-버스, 3-도보
    private double distance; //이동 거리
    private int sectionTime; //이동 소요시간
    private int stationCount; //이동 정거장 수(지하철, 버스 경우만 필수)
    private ArrayList<SubwayLane> subwayLanes; //1-9-3-5 교통 수단 정보 확장 노드 지하철 용
    private ArrayList<BusLane> busLanes; //1-9-3-5 교통 수단 정보 확장 노드 버스 용

    private Place startStation; // 승차정류장 역명 , 역 x(경도)좌표, 역 Y(위도 좌표)
    private Place endStation; // 하차 정류장 역명 , 역 x(경도)좌표, 역 Y(위도 좌표)
    private String way; // 방면 정보
    private int wayCode; // 방면 정보 코드 1: 상행 2: 하행
    private String door; // 지하철 빠른 환승 위치
    private int startID; //출발 정류장/역코드
    private int endID; //도착 정류장/역코드

    private Place startExitNo;
    // startExitNo 지하철 들어가는 출구 번호 (지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)
    // startExitX 지하철 들어가는 출구 X좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)
    // startExitY 지하철 들어가는 출구 Y좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)
    private Place endExitNo;
    // endExitNo 지하철 들어가는 출구 번호 (지하철인 경우에만 사용되지만 해당 태그가 없을 수도 있다.)
    // endExitX 지하철 들어가는 출구 X좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)
    // endExitY 지하철 들어가는 출구 Y좌표(지하철인 경우에 만 사용되지만 해당 태그가 없을 수도 있다.)
    private ArrayList<Stations> stations; //정류장 정보 그룹노드

    public ArrayList<Stations> getStations() {
        return stations;
    }
    public int getEndID() {
        return endID;
    }
    public int getStartID() {
        return startID;
    }
    public Place getEndStation() {
        return endStation;
    }
    public Place getStartStation() {
        return startStation;
    }
    public String getWay() {
        return way;
    }
    public String getDoor() {
        return door;
    }
    public Place getStartExitNo() {
        return startExitNo;
    }
    public Place getEndExitNo() {
        return endExitNo;
    }
    public int getWayCode() {
        return wayCode;
    }
    public ArrayList<BusLane> getBusLanes() {
        return busLanes;
    }
    public ArrayList<SubwayLane> getSubwayLanes() {
        return subwayLanes;
    }
    public double getDistance() {
        return distance;
    }
    public int getSectionTime() {
        return sectionTime;
    }
    public int getStationCount() {
        return stationCount;
    }
    public int getTrafficType() {
        return trafficType;
    }
}


