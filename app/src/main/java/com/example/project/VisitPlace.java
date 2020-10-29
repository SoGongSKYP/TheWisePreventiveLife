package com.example.project;
import java.util.*;

/**
 * 확진자 방문 장소 저장할 class
 */
public class VisitPlace {

    private Place Visit_place;
    private Date Visit_date;

    //Constructor
    public VisitPlace(Place p, Date d) {
        this.Visit_date=d;
        this.Visit_place=p;
    }

    // get -------------------------
    public Place get_visitplace(){
        return this.Visit_place;
    }
    public Date getVisit_date(){
        return this.Visit_date;
    }
    //--------------------------------

    // set----------------------------
    public void setVisit_place(Place p){
        this.Visit_place=p;
    }
    public void setVisit_date(Date d){
        this.Visit_date =d;
    }
    //--------------------------------
}