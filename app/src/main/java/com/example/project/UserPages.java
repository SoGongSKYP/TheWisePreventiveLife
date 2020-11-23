package com.example.project;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.cardview.widget.CardView;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
        import com.google.android.material.bottomnavigation.BottomNavigationView;

        import org.xml.sax.SAXException;

        import java.io.IOException;
        import java.text.ParseException;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.concurrent.locks.Lock;
        import java.util.concurrent.locks.ReentrantLock;

        import javax.xml.parsers.ParserConfigurationException;


public class UserPages extends AppCompatActivity {

    /*Bottom Navigation Bar 관련 컴포넌트*/
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private PageOfMain pageOfMain;
    private PageOfMyDanger pageOfMyDanger ;
    private PageOfStatistics pageOfStatistics;
    private PageOfSelfDiagnosis pageOfSelfDiagnosis ;
    private PageOfSelectedClinic pageOfSelectedClinic;

    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;
    private ImageButton InfoImageButton;

    /*search bar 관련 컴포넌트*/
    private EditText searchBar;
    private ImageButton searchButton;
    private RelativeLayout searchLayout;
    String searchPlace;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;

    public UserPages() throws ParserConfigurationException, SAXException, ParseException, IOException {
        pageOfMain = new PageOfMain();
        new Thread(){
            public void run() {
                pageOfMyDanger = new PageOfMyDanger();
            }
        }.start();
        new Thread(){
            public void run() {
                pageOfSelectedClinic = new PageOfSelectedClinic();
            }
        }.start();
        new Thread(){
            public void run() {
                try {
                    pageOfStatistics = new PageOfStatistics();
                } catch (ParserConfigurationException | SAXException | ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            public void run() {
                pageOfSelfDiagnosis = new PageOfSelfDiagnosis();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pages);

        /*Bottom Navigation 연결*/
        BottomNavigationView navigationView = findViewById(R.id.user_BottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_FrameLayout, pageOfMain).commit();

        /*Tool Bar 연결*/
        toolbar = findViewById(R.id.user_Toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        TitleTextView = findViewById(R.id.user_Title_TextView);
        TitleTextView.setText("주변 확진자 현황");

        /*서치바 컴포넌트 연결*/
        searchBar = findViewById(R.id.user_search_bar);
        searchButton = findViewById(R.id.user_search_button);
        searchLayout = findViewById(R.id.search_RelativeLayout);
        SearchAction();

        InfoImageButton = findViewById(R.id.user_info_ImageButton);
        InfoImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PageOfToManager.class);
                startActivity(intent);

            }
        });
    }

    /*검색 바 구현*/
    void SearchAction(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPlace = searchBar.getText().toString();
                if(searchPlace!=null){
                    // 검색 기능 구현


                }else{
                    Toast.makeText(getParent(), "검색할 장소를 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /* 일반사용자 페이지 네비게이션 바 연결*/
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.user_Home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfMain).commit();
                    TitleTextView.setText("주변 확진자 현황");
                    searchLayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.user_Danger:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfMyDanger).commit();
                    TitleTextView.setText("나의 동선 위험도");
                    searchLayout.setVisibility(View.GONE);
                    return true;
                case R.id.user_Statistics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfStatistics).commit();
                    TitleTextView.setText("전국 통계");
                    searchLayout.setVisibility(View.GONE);
                    return true;
                case R.id.user_SelfDiagnosis:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelfDiagnosis).commit();
                    TitleTextView.setText("자가 진단");
                    searchLayout.setVisibility(View.GONE);
                    return true;
                case R.id.user_Clinics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelectedClinic).commit();
                    TitleTextView.setText("주변 선별 진료소");
                    searchLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void ToolbarInfoButtonAction(){

    }
}
