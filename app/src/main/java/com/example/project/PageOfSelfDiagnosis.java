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
public class PageOfSelfDiagnosis extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_user_selfdiagnosis, container, false);
    }

    /**
     * Default constructor
     */
    public PageOfSelfDiagnosis() {
    }

    /**
     * 
     */
    private ArrayList<SelfDiagnosis> question_sentence;

    /**
     * 
     */
    private ArrayList<Boolean> question_answer;

    /**
     * 
     */
    private Integer diagnosis_result;



    /**
     * 
     */
    public void self_diagnose() {
        // TODO implement here
    }

    /**
     * 
     */
    public void calculate_result() {
        // TODO implement here
    }

    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

}