package com.example.project;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.*;

/**
 * 
 */
public class PageOfMyDanger extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_danger, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfMyDanger() {
    }

    /**
     * 
     */
    private ArrayList<Place> visitplace_list;

    /**
     * 
     */
    private ArrayList<Place> route_list;

    /**
     * 
     */
    private int danger;




    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

    /**
     * 
     */
    public void print_route() {
        // TODO implement here
    }

    /**
     * @return
     */
    public int print_danger() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public ArrayList<Place> search_route() {
        // TODO implement here
        return null;
    }

}