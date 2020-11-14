package com.example.project;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.text.style.TtsSpan;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.material.bottomnavigation.BottomNavigationView;

        import org.xml.sax.SAXException;

        import java.io.IOException;
        import java.text.ParseException;

        import javax.xml.parsers.ParserConfigurationException;

        import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class UserPages extends AppCompatActivity {

    /*Bottom Navigation Bar 관련 컴포넌트*/
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private PageOfMain pageOfMain = new PageOfMain();
    private PageOfMyDanger pageOfMyDanger = new PageOfMyDanger();
    private PageOfStatistics pageOfStatistics = new PageOfStatistics();
    private PageOfSelfDiagnosis pageOfSelfDiagnosis = new PageOfSelfDiagnosis();
    private PageOfSelectedClinic pageOfSelectedClinic = new PageOfSelectedClinic();
    /*Tool Bar 관련 컴포넌트*/
    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView TitleTextView;
    private ImageButton InfoImageButton;


    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;
    private Place sendLoc;

    public UserPages() throws ParserConfigurationException, SAXException, ParseException, IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pages);

        //LocPermission(this, getApplicationContext());


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

        InfoImageButton = findViewById(R.id.user_info_ImageButton);
        InfoImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PageOfToManager.class);
                startActivity(intent);
            }
        });

    }

    //권한 확인후 권한 요청

    public void getLocationByGPS(){



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
                    return true;
                case R.id.user_Danger:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfMyDanger).commit();
                    TitleTextView.setText("나의 동선 위험도");
                    return true;
                case R.id.user_Statistics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfStatistics).commit();
                    TitleTextView.setText("전국 통계");
                    return true;
                case R.id.user_SelfDiagnosis:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelfDiagnosis).commit();
                    TitleTextView.setText("자가 진단");
                    return true;
                case R.id.user_Clinics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelectedClinic).commit();
                    TitleTextView.setText("주변 선별 진료소");
                    return true;
            }
            return false;
        }
    };

    private void ToolbarInfoButtonAction(){

    }

}
