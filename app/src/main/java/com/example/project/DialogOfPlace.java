package com.example.project;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DialogOfPlace extends Dialog {

    private Context mContext;
    ImageButton dialogDismissButton, searchButton;
    Button placeAddButton;
    EditText visitDateEditText, searchBar;
    TextView visitPlaceTextView, visitDetailTextView;

    /*서치바 관련 컴포넌트*/
    String searchPlace;
    Place findPlace;   // 검색 결과의 리턴값

    public DialogOfPlace(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_patient_visit);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogDismissButton = findViewById(R.id.dialog_dismiss_Button);
        placeAddButton = findViewById(R.id.dialog_add_Button);
        visitDateEditText = findViewById(R.id.dialog_visit_date_EditText);
        visitPlaceTextView = findViewById(R.id.dialog_visit_select_title);
        visitDetailTextView = findViewById(R.id.dialog_visit_select_detail);

        searchButton = findViewById(R.id.dialog_search_button);
        searchBar = findViewById(R.id.dialog_search_bar_EditText);

        dialogDismissButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        placeAddButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 임시로 다이얼로그를 닫는다
                //updateVisitButtonAction();
                dismiss();
            }
        });
        searchAction();
    }

    void searchAction(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPlace = searchBar.getText().toString();
                if(searchPlace != null){
                    // searchPlace(String) 변수로 검색 기능 구현

                    // 아래는 검색 결과 String을 보여주는 코드
                    visitPlaceTextView.setText(searchPlace);
                }
                else{
                    Toast.makeText(getContext(), "입력값이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void updateVisitButtonAction(){
        //서치바의 리턴값이 Place 객체

        //Place place = new Place();
        //VisitPlace visitPlace = new VisitPlace(place, visitDateEditText.toString());
    }


}
