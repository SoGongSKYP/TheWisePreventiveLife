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
    
    private ArrayList<Place> nearPlaces;
    private Place centerPlace;
    private UserLoc userPlace;


    public abstract void print_marker();
    public abstract void input_marker();
    public abstract ArrayList<Place> search_nearPlaces(ArrayList<Patient> patientsList);
    public void print_map(Integer x1, Integer y1, Integer x2, Integer y2) {
        // TODO implement here
    }

}
