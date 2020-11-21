package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
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

    /*데이터 관련 컴포넌트*/
    private EditText patientNumEditText, patientDateEditText;
    private Spinner bigLocSpinner, smallLocSpinner;
    private ArrayAdapter bigAdapter, smallAdapter;
    int pBigLocal, pSmallLocal;

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

        patientNumEditText = findViewById(R.id.modify_num_EditText);
        patientDateEditText = findViewById(R.id.modify_time_EditText);
        bigLocSpinner = findViewById(R.id.modify_big_Spinner);
        smallLocSpinner = findViewById(R.id.modify_small_Spinner);

        Intent intent = getIntent();
        RowOfPatient data = (RowOfPatient) intent.getSerializableExtra("row");
        patientDateEditText.setText(data.getConfirmDate());
        patientNumEditText.setText(data.getPatientNum());

        bigAdapter = ArrayAdapter.createFromResource(this, R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);




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

    private void BigSpinnerAction(){
        bigLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String index = Integer.toString(i);
                int resId = getResources().getIdentifier("array_"+index, "array", getPackageName());
                smallAdapter = ArrayAdapter.createFromResource(getParent(), resId, android.R.layout.simple_spinner_dropdown_item);

                pBigLocal = i;
                Log.d("큰 도시 선택 : ", Integer.toString(i));
                smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smallLocSpinner.setAdapter(smallAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pBigLocal = 0;
            }
        });
    }
    private void SmallSpinnerAction() {
        smallLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pSmallLocal = i;
                Log.d("작은 도시 선택 : ", Integer.toString(i));
                if (patientArrayList.size() != 0) {
                    patientArrayList.clear();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pSmallLocal = 0;
            }
        });
    }
}