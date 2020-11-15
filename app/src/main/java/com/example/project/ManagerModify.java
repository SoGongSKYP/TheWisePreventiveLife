package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ManagerModify extends AppCompatActivity {

    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;

    /*Dialog 관련 컴포넌트*/
    Button addButton;
    Dialog placeAddDialog;

    /*RecyclerView 관련 컴포넌트*/
    private RecyclerView patientRecyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Patient> patientArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_modify);

        /*Tool Bar 연결*/
        toolbar = findViewById(R.id.manager_modify_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TitleTextView = findViewById(R.id.manager_modify_Title_TextView);
        TitleTextView.setText("확진자 정보 수정");

        //--------------------------------------------------------------------------------------
        /*RecyclerView 연결*/
        patientRecyclerView = findViewById(R.id.list_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        patientRecyclerView.setLayoutManager(layoutManager);
        patientRecyclerView.setHasFixedSize(true);

        //adapter = new AdapterOfDiagnosis(QuestionSentencesArray);
        //patientRecyclerView.setAdapter(adapter);

    }

}