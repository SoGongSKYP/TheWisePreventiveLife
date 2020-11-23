package com.example.project;

public class Lane {

    Lane(String name, int subwayCodeORBusNo, int subwayCityCodeORBusId){
        this.name =name;
        this.subwayCodeORBusNo = subwayCodeORBusNo;
        this.subwayCityCodeORBusId =subwayCityCodeORBusId;

    }

    private String name; //지하철 노선명 (지하철인 경우에만 필수)
    private int subwayCodeORBusNo; //지하철 노선 번호 (지하철인 경우에만 필수)
    private int subwayCityCodeORBusId; //지하철 도시코드 (지하철인 경우에만 필수)

    public int getSubwayCityCodeORBusId() {
        return subwayCityCodeORBusId;
    }
    public int getSubwayCodeORBusNo() {
        return subwayCodeORBusNo;
    }

    public void setSubwayCityCodeORBusId(int subwayCityCodeORBusId) {
        this.subwayCityCodeORBusId = subwayCityCodeORBusId;
    }
    public void setSubwayCodeORBusNo(int subwayCodeORBusNo) {
        this.subwayCodeORBusNo = subwayCodeORBusNo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
