package com.example.project;
import java.util.*;

/**
 * 확진자 방문 장소 저장할 class
 */
public class VisitPlace {

    private Place visitPlace;
    private Date visitDate;

    //Constructor
    public VisitPlace(Place place, Date date) {
        this.visitDate=date;
        this.visitPlace=place;
    }

    // get -------------------------
    public Place get_visitPlace(){
        return this.visitPlace;
    }
    public Date getVisit_date(){
        return this.visitDate;
    }
    //--------------------------------

    // set----------------------------
    public void setVisit_place(Place place){
        this.visitPlace=place;
    }
    public void setVisit_date(Date date){
        this.visitDate =date;
    }
    //--------------------------------
}
