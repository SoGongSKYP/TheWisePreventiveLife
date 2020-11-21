package com.example.project;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.*;

/**
 * 
 */
public class Alarm  extends BroadcastReceiver {


    public Alarm() {
    }

    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, RestartService.class);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, UserPages.class);
            context.startService(in);
        }
    }
    private Void user_place;

    private ArrayList<Place> near_places;

    public void able_check() {
        // TODO implement here
    }

    /**
     * 
     */
    public void alarm() {
        // TODO implement here
    }

    /**
     * 
     */
    public void search_nearplace() {
        // TODO implement here
    }

}
