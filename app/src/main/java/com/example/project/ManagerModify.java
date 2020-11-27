package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private ArrayList<VisitPlace> visitPlaceArrayList;
    private AdapterOfPlace adapter;

    /*데이터 관련 컴포넌트*/
    private TextView patientNumEditText, patientDateEditText;
    private TextView bigLocTextView, smallLocTextView;
    int pBigLocal, pSmallLocal;
    private TextView titleTextView;
    private ArrayList<VisitPlace> deletePlaceArrayList;
    Patient data;   // PageOfList에서 선택된 확진자 객체
    int rowNum;      // PageOfList에서 선택된 확진자 번호

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
        /* 확진자 데이터 전체 삭제 */

        //--------------------------------------------------------------------------------------
        titleTextView = findViewById(R.id.modify_next_to_button_TextView);
        titleTextView.setText("확진자 정보");
        patientNumEditText = findViewById(R.id.modify_num_EditText);
        patientDateEditText = findViewById(R.id.modify_time_EditText);
        bigLocTextView = findViewById(R.id.modify_big_TextView);
        smallLocTextView = findViewById(R.id.modify_small_TextView);

        Intent intent = getIntent();
        data = (Patient) intent.getSerializableExtra("row");
        rowNum = intent.getIntExtra("rowNum", 100);

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
        patientRecyclerView = findViewById(R.id.modify_visit_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        patientRecyclerView.setLayoutManager(layoutManager);
        patientRecyclerView.setHasFixedSize(true);

        visitPlaceArrayList = data.getVisitPlaceList();
        adapter = new AdapterOfPlace(visitPlaceArrayList, 0);
        patientRecyclerView.setAdapter(adapter);

        deletePlaceArrayList = new ArrayList<VisitPlace>(); // DB에서 지울 동선 리스트

        //--------------------------------------------------------------------------------------
        /*다이얼로그 연결*/
        addPlaceButton = findViewById(R.id.modify_visit_add_Button);
        addPlaceButton.setVisibility(View.INVISIBLE);
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
        editButton.setImageResource(R.drawable.save);
        addPlaceButton.setVisibility(View.VISIBLE);
        titleTextView.setText("수정할 확진자");
        adapter = new AdapterOfPlace(visitPlaceArrayList, 1);
        patientRecyclerView.setAdapter(adapter);
        adapter.setModifyClickListenter(new AdapterOfPlace.OnModifyClickListener(){
            @Override
            public void onModifyItemClick(View v, int pos) {
                // 삭제할 데이터를 deletePlaceArrayList에 저장
                deletePlaceArrayList.add(visitPlaceArrayList.get(pos));
                visitPlaceArrayList.remove(pos);
                Log.d("삭제될 데이터 위치", Integer.toString(pos));
                Log.d("삭제 후에 실제 데이터 크기 : ", Integer.toString(visitPlaceArrayList.size()));
            }
        });
    }

    private void setDefMode(){
        editButton.setImageResource(R.drawable.ic_baseline_edit_24);
        addPlaceButton.setVisibility(View.INVISIBLE);
        titleTextView.setText("확진자 정보");
        adapter = new AdapterOfPlace(visitPlaceArrayList, 0);
        patientRecyclerView.setAdapter(adapter);
    }

    private void saveEditData(){
        // 이곳에서 변경된 데이터를 업데이트 함
        // DBEntity 클래스의 pmoving_delete 함수, insert 함수
        Toast.makeText(this, "여기서 데이터를 업데이트 하면 됩니다.", Toast.LENGTH_SHORT).show();
    }


}