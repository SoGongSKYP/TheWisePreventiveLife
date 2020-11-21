package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ManagerModify extends AppCompatActivity {

    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;
    private ImageButton editButton;

    /*Dialog 관련 컴포넌트*/
    ImageButton addPlaceButton;
    private DialogOfPlace dialog;

    /*RecyclerView 관련 컴포넌트*/
    private RecyclerView patientRecyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Patient> patientArrayList;

    /*데이터 관련 컴포넌트*/
    private EditText patientNumEditText, patientDateEditText;
    private TextView bigLocTextView, smallLocTextView;
    private ArrayAdapter bigAdapter, smallAdapter;
    int pBigLocal, pSmallLocal;

    enum MODE {DEF, EDIT};
    MODE now = MODE.DEF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_modify);

        now = MODE.DEF;

        /*Tool Bar 연결*/
        toolbar = findViewById(R.id.manager_modify_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TitleTextView = findViewById(R.id.manager_modify_Title_TextView);
        TitleTextView.setText("확진자 정보");

        editButton = findViewById(R.id.manager_modify_save_ImageButton);
        setMode();
        //--------------------------------------------------------------------------------------

        patientNumEditText = findViewById(R.id.modify_num_EditText);
        patientDateEditText = findViewById(R.id.modify_time_EditText);
        bigLocTextView = findViewById(R.id.modify_big_TextView);
        smallLocTextView = findViewById(R.id.modify_small_TextView);

        Intent intent = getIntent();
        RowOfPatient data = (RowOfPatient) intent.getSerializableExtra("row");
        patientDateEditText.setText(data.getConfirmDate());
        patientNumEditText.setText(data.getPatientNum());
        pBigLocal = data.getBigLocalNum();
        pSmallLocal = data.getSmallLocalNum();

        String[] bigArray = getResources().getStringArray(R.array.big_location_array);
        bigLocTextView.setText(bigArray[pBigLocal]);
        String index = Integer.toString(pBigLocal);
        int resId = getResources().getIdentifier("array_"+index, "array", getPackageName());
        String[] smallArray = getResources().getStringArray(resId);
        smallLocTextView.setText(smallArray[pSmallLocal]);


        //--------------------------------------------------------------------------------------
        /*RecyclerView 연결*/
        patientRecyclerView = findViewById(R.id.list_RecyclerView);
        //layoutManager = new LinearLayoutManager(this);
        //patientRecyclerView.setLayoutManager(layoutManager);
        //patientRecyclerView.setHasFixedSize(true);

        //adapter = new AdapterOfDiagnosis(QuestionSentencesArray);
        //patientRecyclerView.setAdapter(adapter);
        //--------------------------------------------------------------------------------------
        /*다이얼로그 연결*/
        addPlaceButton = findViewById(R.id.modify_visit_add_Button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = MODE.EDIT;
                dialog = new DialogOfPlace(ManagerModify.this);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

    }

    private void setMode(){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(now == MODE.DEF){
                    now = MODE.EDIT;
                    setEditMode();
                }else{
                    now = MODE.DEF;
                    saveEditData();
                    setDefMode();
                }
            }
        });
    }

    private void setEditMode(){
        Toast.makeText(this, "편집 모드로 전환합니다.", Toast.LENGTH_SHORT).show();
        patientDateEditText.setEnabled(true);
        patientNumEditText.setEnabled(true);
        patientDateEditText.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGrey));
        patientNumEditText.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGrey));
        editButton.setImageResource(R.drawable.save);
    }

    private void setDefMode(){
        patientDateEditText.setEnabled(false);
        patientNumEditText.setEnabled(false);
        patientDateEditText.setTextColor(ContextCompat.getColor(this, R.color.colorMiddleGrey));
        patientNumEditText.setTextColor(ContextCompat.getColor(this, R.color.colorMiddleGrey));
        editButton.setImageResource(R.drawable.ic_baseline_edit_24);
    }

    private void saveEditData(){
        // 이곳에서 변경된 데이터를 업데이트 함
        Toast.makeText(this, "여기서 데이터를 업데이트 하면 됩니다.", Toast.LENGTH_SHORT).show();
    }


}