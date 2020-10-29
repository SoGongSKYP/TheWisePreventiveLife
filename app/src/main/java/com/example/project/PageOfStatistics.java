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
public class PageOfStatistics extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_statistics, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfStatistics() {
    }

    /**
     * 
     */
    private Void user_place;

    /**
     * 
     */
    private Void nation_statistic;

    /**
     * 
     */
    private Void local_statics;

    /**
     * 
     */
    private Void map_nationStatics;






    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

    /**
     * 
     */
    public void print_nationStatistics() {
        // TODO implement here
    }

    /**
     * 
     */
    public void print_localStatistics() {
        // TODO implement here
    }

}