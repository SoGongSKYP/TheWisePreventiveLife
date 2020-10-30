package com.example.project;

        import java.util.*;
        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
/**
 *
 */
public abstract class Map extends AppCompatActivity implements OnMapReadyCallback {

    /**
     * Default constructor
     */
    public Map() {
    }
    
    private ArrayList<Place> nearPlaces;
    private Place centerPlace;
    private UserLoc userPlace;


    public abstract void print_marker();
    public abstract void input_marker();
    public abstract ArrayList<Place> search_nearPlaces(ArrayList<Patient> patientsList);
    public void print_map(Integer x1, Integer y1, Integer x2, Integer y2) {
        // TODO implement here
    }

}
