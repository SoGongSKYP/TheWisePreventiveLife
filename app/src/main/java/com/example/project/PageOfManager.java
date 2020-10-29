package com.example.project;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.*;


public class PageOfManager extends Fragment {

    Button switchButton;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_info, container, false);

        switchButton = v.findViewById(R.id.switch_to_user_Button);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PageOfMain.class);
                startActivity(intent);
            }
        });

        return v;
    }

    /**
     * Default constructor
     */
    public PageOfManager() {
    }

    /**
     * 
     */
    private Void patient;

    /**
     * 
     */
    private ArrayList<Patient> patient_list;

    /**
     * 
     */
    private Void patient_localNum;

    /**
     * 
     */
    private Void patient_visitlist;

    /**
     * 
     */
    private Void visit_place;

    /**
     * 
     */
    private Integer mode;





    /**
     * 
     */
    public void print_UI() {
        // TODO implement here
    }

    /**
     * 
     */
    public void add_patient() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Patient find_patient() {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void delete_patient() {
        // TODO implement here
    }

    /**
     * 
     */
    public void delete_place() {
        // TODO implement here
    }

    /**
     * 
     */
    public void add_place() {
        // TODO implement here
    }

    /**
     * 
     */
    public void edit_patient() {
        // TODO implement here
    }

}