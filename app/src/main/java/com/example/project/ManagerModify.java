package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ManagerModify extends AppCompatActivity {

    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;

    /*Dialog 관련 컴포넌트*/
    ImageButton addPlaceButton;
    private DialogOfPlace dialog;

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
        //--------------------------------------------------------------------------------------
        /*다이얼로그 연결*/
        addPlaceButton = findViewById(R.id.modify_visit_add_Button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new DialogOfPlace(ManagerModify.this);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

}