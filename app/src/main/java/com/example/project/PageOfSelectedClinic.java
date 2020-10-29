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
public class PageOfSelectedClinic extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_clinics, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfSelectedClinic() {
    }

    /**
     * 
     */
    private ArrayList<SelectedClinic> near_clinics;

    /**
     * 
     */
    private ArrayList<SelectedClinic> clinics;

    /**
     * 
     */
    private Void user_place;






    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

    /**
     * @return
     */
    public ArrayList<Place> find_clinic() {
        // TODO implement here
        return null;
    }

}