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
        import android.widget.ImageButton;
        import android.widget.TextView;

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

    /*구글 search bar 관련 컴포넌트*/
    private AutocompleteSupportFragment searchBar;
    private CardView searchCardView;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private Intent intent;
    private Intent alarmIntent;

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

        InfoImageButton = findViewById(R.id.user_info_ImageButton);
        InfoImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), PageOfToManager.class);
                startActivity(intent);


            }
        });


        /*구글 맵 서치바 연결*/
        searchCardView = findViewById(R.id.search_CardView);
        /*
        searchBar = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.search_fragment);

        searchBar.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        searchBar.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        */

    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    /* 일반사용자 페이지 네비게이션 바 연결*/
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.user_Home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfMain).commit();
                    TitleTextView.setText("주변 확진자 현황");
                    searchCardView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.user_Danger:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfMyDanger).commit();
                    TitleTextView.setText("나의 동선 위험도");
                    searchCardView.setVisibility(View.GONE);
                    return true;
                case R.id.user_Statistics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfStatistics).commit();
                    TitleTextView.setText("전국 통계");
                    searchCardView.setVisibility(View.GONE);
                    return true;
                case R.id.user_SelfDiagnosis:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelfDiagnosis).commit();
                    TitleTextView.setText("자가 진단");
                    searchCardView.setVisibility(View.GONE);
                    return true;
                case R.id.user_Clinics:
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_FrameLayout, pageOfSelectedClinic).commit();
                    TitleTextView.setText("주변 선별 진료소");
                    searchCardView.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onDestroy() {
        new Thread(){
            @Override
            public void run() {
                System.out.println("프로그램 종료");
                alarmIntent = new Intent(getApplicationContext(),AlramService.class);
                //alarmIntent.putExtra("patientList",DBEntity.getPatientList());
                startService(alarmIntent);
            }
        }.start();

        super.onDestroy();
    }

    private void ToolbarInfoButtonAction(){

    }
}
