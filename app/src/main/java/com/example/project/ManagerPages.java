package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagerPages extends AppCompatActivity {

    /*Bottom Navigation Bar 관련 컴포넌트*/
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private PageOfAdd pageOfAdd = new PageOfAdd();
    private PageOfModify pageOfModify = new PageOfModify();
    private PageOfManager pageOfManager = new PageOfManager();
    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_pages);

        /*Bottom Navigation 연결*/
        BottomNavigationView navigationView = findViewById(R.id.manager_BottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.manager_FrameLayout, pageOfAdd).commit();

        /*Tool Bar 연결*/
        toolbar = findViewById(R.id.manager_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TitleTextView = findViewById(R.id.manager_Title_TextView);
        TitleTextView.setText("확진자 정보 추가");
    }

    /* 일반사용자 페이지 네비게이션 바 연결*/
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.manager_Add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.manager_FrameLayout, pageOfAdd).commit();
                    TitleTextView.setText("확진자 정보 추가");
                    return true;
                case R.id.manager_Modify:
                    getSupportFragmentManager().beginTransaction().replace(R.id.manager_FrameLayout, pageOfModify).commit();
                    TitleTextView.setText("확진자 정보 수정");
                    return true;
                case R.id.manager_Info:
                    getSupportFragmentManager().beginTransaction().replace(R.id.manager_FrameLayout, pageOfManager).commit();
                    TitleTextView.setText("관리자 정보");
                    return true;
            }
            return false;
        }
    };
}