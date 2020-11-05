package com.example.project;

/*
* 1-9-2요약 정보 확장 노드
*/
public class ExtendNode {

    ExtendNode(){

    }
    private double trafficDistance; //도보를 제외한 총 이동 거리
    private int totalWalk; //총 도보 이동 거리
    private int totalTime; //총 소요시간
    private int payment; // 총 요금
    private int busTransitCount; //버스 환승 카운트
    private int subwayTransitCount; //지하철 환승 카운트
    private String mapObj; //보간점 API를 호출하기 위한 파라미터 값
    private String firstStartStation; //최초 출발역/정류장
    private String lastEndStation; //최종 도착역/정류장
    private int totalStationCount; //총 정류장 합
    private double totalDistance; //총 거리
}
