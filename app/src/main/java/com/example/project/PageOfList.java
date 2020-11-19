package com.example.project;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private ArrayList<RowOfPatient> patientArrayList;
    private AdapterOfList adapter;




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_list, container, false);

        /*Spinner 연결*/
        bigLocSpinner = v.findViewById(R.id.list_big_Spinner);
        smallLocSpinner = v.findViewById(R.id.list_small_Spinner);

        bigAdapter = ArrayAdapter.createFromResource(getContext(), R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);

        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.seoul_array, android.R.layout.simple_spinner_dropdown_item);
        smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallLocSpinner.setAdapter(smallAdapter);

        SpinnerAction();
        //--------------------------------------------------------------------------------------
        /*RecyclerView 연결*/
        patientArrayList = new ArrayList<RowOfPatient>();
        MakeTempArray();

        patientRecyclerView = v.findViewById(R.id.list_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        patientRecyclerView.setLayoutManager(layoutManager);
        patientRecyclerView.setHasFixedSize(true);

        adapter = new AdapterOfList(patientArrayList);
        patientRecyclerView.setAdapter(adapter);


        return v;
    }

    private void MakeTempArray() {
        RowOfPatient rowOfPatient = new RowOfPatient("1", "2020년 11월 13일");
        RowOfPatient rowOfPatient1 = new RowOfPatient("2", "2020년 11월 14일");
        RowOfPatient rowOfPatient2 = new RowOfPatient("3", "2020년 11월 15일");
        RowOfPatient rowOfPatient3 = new RowOfPatient("4", "2020년 11월 16일");
        patientArrayList.add(rowOfPatient);
        patientArrayList.add(rowOfPatient1);
        patientArrayList.add(rowOfPatient2);
        patientArrayList.add(rowOfPatient3);
    }

    private void SpinnerAction(){
        bigLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.seoul_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 1:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.busan_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 2:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.daegu_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 3:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.incheon_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 4:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gwangju_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 5:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.daejeon_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 6:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ulsan_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 7:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sejong_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 8:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeonggi_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 9:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gangwondo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 10:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chungcheongbukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 11:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chungcheongnamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 12:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeollabukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 13:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeollanamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 14:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeongsasngbukdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 15:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gyeongsangnamdo_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 16:
                        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jeju_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
                smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smallLocSpinner.setAdapter(smallAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
