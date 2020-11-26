package com.example.project;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PageOfAdd<STATE_DUPLE_FALSE> extends Fragment {

    /*입력 받는 컴포넌트*/
    //Spinner 관련 컴포넌트
    private Spinner bigLocSpinner, smallLocSpinner;
    private ArrayAdapter bigAdapter, smallAdapter;

    private EditText patientNumEditText, patientDateEditText;
    private Button dupleButton;

    /*RecyclerView 관련 컴포넌트*/
    private RecyclerView visitRecyclerView;
    private LinearLayoutManager layoutManager;
    //private ArrayList<Patient> patientArrayList;

    /*다이얼로그 관련 컴포넌트*/
    private ImageButton addPlaceButton;
    private DialogOfPlace dialog;

    /*UI 컴포넌트*/
    private TextView dupleCheckTextView;

    /*데이터 저장 변수*/
    String pBigLocal, pSmallLocal, pNum, pDate;
    ArrayList<Place> pPlaces;
    ArrayList<VisitPlace> visitPlaces;
    Boolean dupleCheck, saveCheck;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_manager_add, container, false);

        patientNumEditText = v.findViewById(R.id.patient_num_EditText);
        patientDateEditText = v.findViewById(R.id.patient_date_EditText);
        patientNumEditText.setText(null);
        patientDateEditText.setText(null);

        dupleCheck = false;
        saveCheck = false;
        dupleButton = v.findViewById(R.id.patient_duple_Button);
        dupleCheckTextView = v.findViewById(R.id.duple_check_TextView);
        DupleChecking();

        /*Spinner 연결*/
        bigLocSpinner = v.findViewById(R.id.add_big_Spinner);
        smallLocSpinner = v.findViewById(R.id.add_small_Spinner);

        SetSpinnerAdapter();
        BigSpinnerAction();
        SmallSpinnerAction();
        //--------------------------------------------------------------------------------------

        /*RecyclerView 연결*/
        visitRecyclerView = v.findViewById(R.id.patient_visit_RecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        visitRecyclerView.setLayoutManager(layoutManager);
        visitRecyclerView.setHasFixedSize(true);

        visitPlaces = new ArrayList<>();
        //--------------------------------------------------------------------------------------

        /*다이얼로그 연결*/
        addPlaceButton = v.findViewById(R.id.patient_visit_add_Button);
        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dupleCheck == true){
                    dialog = new DialogOfPlace(getContext());
                    dialog.setVisitDialogListener(new DialogOfPlace.VisitDialogListener(){
                        @Override
                        public void onAddCliked(Place place, String date) {
                            VisitPlace vp = new VisitPlace(place, date);
                            visitPlaces.add(vp);
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
                else{
                    Toast.makeText(getContext(), "중복 확인을 먼저 하고 동선을 추가하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //--------------------------------------------------------------------------------------


        return v;
    }

    private void DupleChecking(){
        dupleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dupleCheck == false && saveCheck == false){
                    if(patientNumEditText.getText() != null && patientDateEditText.getText() !=null){
                        dupleCheckTextView.setText("중복 확인! 아래에서 확진자 동선을 추가하세요");
                        dupleCheckTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                        dupleCheck = true;
                        dupleButton.setText("저    장");
                        dupleButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_full_green));
                    }else{
                        Toast.makeText(getContext(), "확진자 기본 정보를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(dupleCheck == true && saveCheck == false){
                    GetDataFromUI();
                    Toast.makeText(getContext(), "확진자"+pNum+" 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    dupleButton.setText("새 로 고 침");
                    dupleButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_full_blue));
                    dupleCheckTextView.setText("정보가 저장되었습니다. 새로고침하여 새 정보를 입력하세요.");
                    dupleCheckTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
                    saveCheck = true;
                }
                else if(dupleCheck && saveCheck){
                    Log.d("새로고침", "OK");
                    RefreshFragment();
                }
            }
        });
    }

    void RefreshFragment(){
        patientNumEditText.setText(null);
        patientDateEditText.setText(null);
        SetSpinnerAdapter();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void GetDataFromUI(){
        pNum = patientNumEditText.getText().toString();
        pDate = patientDateEditText.getText().toString();
        Log.d("잘 들어왔는가?", pNum+" , "+pDate);
        //String date = "20" + patientDateEditText.getText().toString();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
        //Date pdate = dateFormat.parse(date);

    }



    private void SetSpinnerAdapter(){
        bigAdapter = ArrayAdapter.createFromResource(getContext(), R.array.big_location_array, android.R.layout.simple_spinner_dropdown_item);
        bigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bigLocSpinner.setAdapter(bigAdapter);

        smallAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_0, android.R.layout.simple_spinner_dropdown_item);
        smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smallLocSpinner.setAdapter(smallAdapter);
    }

    private void BigSpinnerAction(){
        bigLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String index = Integer.toString(i);
                int resId = getResources().getIdentifier("array_"+index, "array", getContext().getPackageName());
                smallAdapter = ArrayAdapter.createFromResource(getContext(), resId, android.R.layout.simple_spinner_dropdown_item);

                pBigLocal = Integer.toString(i);
                Log.d("ADD 큰 도시 : ", pBigLocal);
                smallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                smallLocSpinner.setAdapter(smallAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pBigLocal = "0";
            }
        });
    }
    private void SmallSpinnerAction(){
        smallLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pSmallLocal = Integer.toString(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pSmallLocal = "0";
            }
        });
    }

}
