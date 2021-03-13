package com.bewise.ringfence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "Test";

    private NotificationHelper notificationHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        notificationHelper = new NotificationHelper(context);

        Log.i(TAG, "___________________ Geofence is triggered from App ____________________ ");
        // an Intent broadcast.
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.i(TAG, "onReceive: Error receiving geofence event...");
            return;
        }


        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.i(TAG, "onReceive: " + geofence.getRequestId());
        }

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.i(TAG, "___________________ GEOFENCE_TRANSITION_ENTER ____________________ ");
                notificationHelper.sendNotification("Be wise", "Remember to look after your mates");
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Log.i(TAG, "___________________ GEOFENCE_TRANSITION_DWELL ____________________ ");
                notificationHelper.sendNotification("Be wise", "Remember to look after your mates");
                break;
        }

    }
}
