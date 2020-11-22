package com.example.project;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlramService extends Service {
    public AlramService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
