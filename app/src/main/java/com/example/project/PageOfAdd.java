package com.example.project;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PageOfAdd extends Fragment {

    /*Spinner 관련 컴포넌트*/
    private Spinner bigLocSpinner, smallLocSpinner;
    private ArrayAdapter bigAdapter, smallAdapter;

    /*RecyclerView 관련 컴포넌트*/
    private RecyclerView visitRecyclerView;
    private LinearLayoutManager layoutManager;
    //private ArrayList<Patient> patientArrayList;

    /*다이얼로그 관련 컴포넌트*/
    private Button addPlaceButton;
    private DialogOfPlace dialog;

    /*UI 컴포넌트*/
    private TextView dupleCheckTextView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_add, container, false);

        /*Spinner 연결*/
        bigLocSpinner = v.findViewById(R.id.add_big_Spinner);
        smallLocSpinner = v.findViewById(R.id.add_small_Spinner);

        bigAdapter = ArrayAdapter.createFromResource(getContext(), R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);

        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.seoul_array, android.R.layout.simple_spinner_dropdown_item);
        smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallLocSpinner.setAdapter(smallAdapter);

        SpinnerAction();
        //--------------------------------------------------------------------------------------
        /*RecyclerView 연결*/
        visitRecyclerView = v.findViewById(R.id.patient_visit_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        visitRecyclerView.setLayoutManager(layoutManager);
        visitRecyclerView.setHasFixedSize(true);

        //adapter = new AdapterOfDiagnosis(QuestionSentencesArray);
        //patientRecyclerView.setAdapter(adapter);
        //--------------------------------------------------------------------------------------
        /*다이얼로그 연결*/
        /*
        addPlaceButton = v.findViewById(R.id.dialog_add_Button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new DialogOfPlace(getContext());
                dialog.show();
            }
        });
        */
        //--------------------------------------------------------------------------------------
        return v;
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
