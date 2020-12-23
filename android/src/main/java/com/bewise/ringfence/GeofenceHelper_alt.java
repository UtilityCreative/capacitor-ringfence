/*
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


public class GeofenceHelper_alt extends ContextWrapper {

    private static final String TAG = "GeofenceHelper";
    public PendingIntent pendingIntent;

    public Activity baseContext;

    public GeofenceHelper_alt(Activity context) {
        super(context);
        this.baseContext = context;
    }




    // Creates our geofence by passing in the latlong and radius parameters
    public Geofence getGeofence(String ID, Double latlong[], float radius, int transitionTypes) {
        Log.i(TAG, "___________________ Setup latlong ____________________ !!!!! == "+latlong[0]+" + "+latlong[1]+" radius == "+radius);
        return new Geofence.Builder()
                .setCircularRegion(latlong[0], latlong[1], radius)
                .setRequestId(ID)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(1)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

    // Sets up the trigger request - ToDo: should change this to  INITIAL_TRIGGER_ENTER
    public GeofencingRequest getGeofencingRequest(ArrayList<Geofence> geofenceList) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER| GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        return builder.build();


    }

    // Sets up an intent which is listened to by the BroadcastReceiver

    public PendingIntent getPendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(baseContext, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        pendingIntent = PendingIntent.getBroadcast(baseContext, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    public PendingIntent getPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceRegistrationService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
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
*/