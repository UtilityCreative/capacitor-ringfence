package com.bewise.ringfence;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;


import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.concurrent.Executor;


//(requestCodes={RingfencePlugin.REQUEST_TRACKING})
@NativePlugin
public class RingfencePlugin extends Plugin {


    //protected static final int REQUEST_TRACKING = 12345; // Unique request code


    /*
    public void enableUserlocation() {
        Log.i("Test", "___________________ Get Permissions ____________________ !!!!!");

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission has already been granted
            setupGeolocations();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ){
            //ActivityCompat.requestPermissions((Activity) this.getContext(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_TRACKING);
            pluginRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_TRACKING);
        } else {
            //ActivityCompat.requestPermissions((Activity) this.getContext(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_TRACKING);
            pluginRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_TRACKING);
        }

    }




    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.i("Test", "User denied permission");
                return;
            }
        }

        if (requestCode == REQUEST_TRACKING) {
            // We got the permission!
            setupGeolocations();
        }
    }
*/

    /*
    public void setupGeolocations() {
        Log.i("Test", "User has accepted permission !!!!!");
    }
*/

    /*
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private String GEOFENCE_ID = "BLAHTWENTYEIGHT";

    private static final String TAG = "MapsActivity";

    public Context baseContext;


    public void setupGeofences(Double latlong[], float radius, Context context) {

        this.baseContext = context;
        geofenceHelper = new GeofenceHelper(context);
        Log.i("Test", "___________________ Setup Geofences ____________________ !!!!!");
        geofencingClient = LocationServices.getGeofencingClient((Activity)baseContext);


        // Build our geofence (we can run this on a loop to create multiple Geofences and add them to an ArrayList
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latlong, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);

        ArrayList<Geofence> geofenceArray = new ArrayList<>();
        geofenceArray.add(geofence);


        if (ActivityCompat.checkSelfPermission((Activity)baseContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //.capacitorringfence    (this was removed from the AndroidMainifest for this plugin - no idea why the path was set to this....
        // android:enabled="true" android:exported="true"  (Also this from the receiver xml)

        geofencingClient.addGeofences(geofenceHelper.getGeofencingRequest(geofenceArray), geofenceHelper.getPendingIntent())
            .addOnSuccessListener((Activity) baseContext, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(TAG, "onSuccess: Geofence Added...");
                }
            })
            .addOnFailureListener((Activity) baseContext, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String errorMessage = geofenceHelper.getErrorString(e);
                    Log.i(TAG, "onFailure: " + errorMessage);
                }
            });
    }

*/









    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        Log.i("Test", "___________________ Get Permissions ____________________ !!!!!");
        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }


}
