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

import androidx.annotation.NonNull;

import java.util.Objects;

public class DialogOfPlace extends Dialog {

    private Context mContext;
    ImageButton dialogDismissButton;
    Button placeAddButton;
    EditText visitDateEditText;
    TextView visitPlaceTextView, visitDetailTextView;

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
                // void updatePatientVisitButton();
                dismiss();
            }
        });
        
    }



}
