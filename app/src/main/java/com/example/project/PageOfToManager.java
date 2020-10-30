package com.example.project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PageOfToManager extends AppCompatActivity {

    Button toManagerButton;
    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_manager);

        /*Tool Bar 연결*/
        toolbar = findViewById(R.id.toManager_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TitleTextView = findViewById(R.id.toManager_Title_TextView);
        TitleTextView.setText("사용자 어플리케이션 정보");

        /*임시 화면 전환 버튼*/
        toManagerButton = findViewById(R.id.switch_to_manager_Button);
        toManagerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerPages.class);
                startActivity(intent);
            }
        });
    }
}