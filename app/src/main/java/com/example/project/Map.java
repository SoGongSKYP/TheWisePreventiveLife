package com.example.project;

import java.util.*;

/**
 * 
 */
public abstract class Map {

    /**
     * Default constructor
     */
    public Map() {
    }

    /**
     * 
     */
    public ArrayList<Place> near_places;
    public Place center_place;
    public UserLoc user_place;



    /**
     * 
     */
    public abstract void print_marker();

    /**
     * 
     */
    public abstract void input_marker();

    /**
     * @param arraylist<Patient> 
     * @return
     */
    public abstract ArrayList<Place> search_nearPlaces(ArrayList<Patient> patientsList);

    /**
     * @param Integer x1
     * @param int y1 
     * @param int x2 
     * @param int y2
     */
    public void print_map(Integer x1, Integer y1, Integer x2, Integer y2) {
        // TODO implement here
    }

}
