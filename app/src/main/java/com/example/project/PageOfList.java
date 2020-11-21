package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PageOfList extends Fragment {

    /*Spinner 관련 컴포넌트*/
    private Spinner bigLocSpinner, smallLocSpinner;
    private ArrayAdapter bigAdapter, smallAdapter;

    /*RecyclerView 관련 컴포넌트*/
    private RecyclerView patientRecyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<RowOfPatient> patientArrayList, totalArrayList;
    private AdapterOfList adapter;

    /*데이터 저장 변수*/
    int pBigLocal, pSmallLocal;




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_list, container, false);

        pBigLocal = 0;
        pSmallLocal = 0;

        /*Spinner 연결*/
        bigLocSpinner = v.findViewById(R.id.list_big_Spinner);
        smallLocSpinner = v.findViewById(R.id.list_small_Spinner);

        bigAdapter = ArrayAdapter.createFromResource(getContext(), R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);

        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_0, android.R.layout.simple_spinner_dropdown_item);
        smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallLocSpinner.setAdapter(smallAdapter);

        BigSpinnerAction();
        SmallSpinnerAction();
        //--------------------------------------------------------------------------------------
        /*RecyclerView 연결*/
        totalArrayList = new ArrayList<RowOfPatient>();
        MakeTempArray();
        patientArrayList = new ArrayList<RowOfPatient>();

        patientRecyclerView = v.findViewById(R.id.list_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        patientRecyclerView.setLayoutManager(layoutManager);
        patientRecyclerView.setHasFixedSize(true);

        adapter = new AdapterOfList(patientArrayList);
        patientRecyclerView.setAdapter(adapter);
        adapter.setOnListClickListenter(new AdapterOfList.OnListClickListener() {
            @Override
            public void onListItemCick(View v, int pos) {
                RowOfPatient selectedRow = patientArrayList.get(pos);
                Intent intent = new Intent(getActivity(), ManagerModify.class);
                intent.putExtra("row", (Serializable) selectedRow);
                startActivity(intent);
            }
        });

        return v;
    }

    private void MakeTempArray() {
        RowOfPatient rowOfPatient00 = new RowOfPatient("1", "201113", 0, 0);
        RowOfPatient rowOfPatient01 = new RowOfPatient("2", "201112", 0, 0);
        RowOfPatient rowOfPatient02 = new RowOfPatient("3", "201111", 0, 0);
        RowOfPatient rowOfPatient03  = new RowOfPatient("4", "201114", 0, 0);
        RowOfPatient rowOfPatient1 = new RowOfPatient("2", "201114", 1, 0);
        RowOfPatient rowOfPatient2 = new RowOfPatient("3", "201115", 12, 8);
        RowOfPatient rowOfPatient3 = new RowOfPatient("4", "201116", 2, 0);
        totalArrayList.add(rowOfPatient00);
        totalArrayList.add(rowOfPatient01);
        totalArrayList.add(rowOfPatient02);
        totalArrayList.add(rowOfPatient03);
        totalArrayList.add(rowOfPatient1);
        totalArrayList.add(rowOfPatient2);
        totalArrayList.add(rowOfPatient3);
    }

    private void BigSpinnerAction(){
        bigLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String index = Integer.toString(i);
                int resId = getResources().getIdentifier("array_"+index, "array", getContext().getPackageName());
                smallAdapter = ArrayAdapter.createFromResource(getContext(), resId, android.R.layout.simple_spinner_dropdown_item);

                pBigLocal = i;
                Log.d("큰 도시 선택 : ", Integer.toString(i));
                smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smallLocSpinner.setAdapter(smallAdapter);
                if(i<=7){
                    if(patientArrayList.size() !=0 ){
                        patientArrayList.clear();
                        showList(pBigLocal, 0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pBigLocal = 0;
            }
        });
    }
    private void SmallSpinnerAction(){
        smallLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pSmallLocal = i;
                Log.d("작은 도시 선택 : ", Integer.toString(i));
                if(patientArrayList.size() != 0){
                    patientArrayList.clear();
                }
                showList(pBigLocal, pSmallLocal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pSmallLocal = 0;
            }
        });
    }

    private void showList(int bigLoc, int smallLoc){
        // 스피너에 대한 값을 받고
        // 해당 지역 번호를 가지고 있는 리스트를 모두 보여줌
        // DB 구축 후, 쿼리 문으로 실행
        for(int i = 0;i<totalArrayList.size();i++){
            RowOfPatient row =  totalArrayList.get(i);
            if (row.getBigLocalNum() == bigLoc && row.getSmallLocalNum() == smallLoc){
                patientArrayList.add(row);
            }
            else{
                Log.d("List Recyclerview : ", "현재 데이터가 없습니다.");
            }
        }
    }
}
