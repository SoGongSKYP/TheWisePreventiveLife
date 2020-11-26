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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
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
    private ArrayList<Patient> patientArrayList, totalArrayList;
    private AdapterOfList adapter;

    /*데이터 저장 변수*/
    int pBigLocal, pSmallLocal;
    String deleteOK = "";
    Patient selectedRow;
    int testPos;


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
        totalArrayList = (((ManagerPages)getActivity()).data);
        patientArrayList = new ArrayList<Patient>();

        patientRecyclerView = v.findViewById(R.id.list_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        patientRecyclerView.setLayoutManager(layoutManager);
        patientRecyclerView.setHasFixedSize(true);

        adapter = new AdapterOfList(patientArrayList);
        patientRecyclerView.setAdapter(adapter);
        adapter.setOnListClickListenter(new AdapterOfList.OnListClickListener() {
            @Override
            public void onListItemClick(View v, int pos) {
                selectedRow = patientArrayList.get(pos);
                testPos = pos;
                Intent intent = new Intent(getActivity(), ManagerModify.class);
                intent.putExtra("row", (Serializable) selectedRow);
                intent.putExtra("ID", (((ManagerPages)getActivity()).ID));
                intent.putExtra("PW", (((ManagerPages)getActivity()).PW));
                startActivity(intent);
            }
        });

        //--------------------------------------------------------------------------------------
        /* ManagerModify에서 전체삭제 버튼을 눌렀을 때, 해당 ROW 삭제*/
       Bundle bundle = getArguments();
        if(bundle != null){
            deleteOK = bundle.getString("delete");
            if(deleteOK == "OK"){
                //DBEntity delete_Patient - delete seletedRow
                Log.d("삭제할 위치 반환 : ", Integer.toString(testPos));

            }
        }


        return v;
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
            Patient row =  totalArrayList.get(i);
            if (row.getBigLocalNum() == bigLoc && row.getSmallLocalNum() == smallLoc){
                patientArrayList.add(row);
            }
            else{
                Log.d("List Recyclerview : ", "현재 데이터가 없습니다.");
            }
        }
    }
}
