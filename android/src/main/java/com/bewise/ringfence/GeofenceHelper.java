package com.bewise.ringfence;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;


public class GeofenceHelper extends ContextWrapper {

    private static final String TAG = "Test";
    public PendingIntent pendingIntent;


    public GeofenceHelper(Activity base) {
        super(base);
    }

    // Creates our geofence by passing in the latlong and radius parameters
    public Geofence getGeofence(String geoId, Double latlong[], float radius, int transitionTypes) {
        radius = radius + 35;
        return new Geofence.Builder()
                .setCircularRegion(latlong[0], latlong[1], radius)
                .setRequestId(geoId)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(1)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }
    // .setNotificationResponsiveness(5000)
    // Sets up the trigger request
    public GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofenceList) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    // Sets up an intent which is listened to by the BroadcastReceiver
    public PendingIntent getPendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        pendingIntent = PendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }


    public String getErrorString(Exception e) {
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            switch (apiException.getStatusCode()) {
                case GeofenceStatusCodes
                        .GEOFENCE_NOT_AVAILABLE:
                    return "GEOFENCE_NOT_AVAILABLE";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_GEOFENCES:
                    return "GEOFENCE_TOO_MANY_GEOFENCES";
                case GeofenceStatusCodes
                        .GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    return "GEOFENCE_TOO_MANY_PENDING_INTENTS";
            }
        }
        return e.getLocalizedMessage();
    }
}
