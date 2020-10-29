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
public class PageOfMain extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfMain() {
    }

    /**
     * 
     */
    private Void my_place;

    /**
     * 
     */
    private ArrayList<Place> near_places;







    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

    /**
     * 
     */
    public void print_map() {
        // TODO implement here
    }

}