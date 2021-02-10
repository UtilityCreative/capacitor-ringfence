package com.bewise.ringfence;

import android.app.Activity;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.UUID;

@NativePlugin(
        requestCodes={RingfencePlugin.FINE_LOCATION_ACCESS_REQUEST_CODE, RingfencePlugin.BACKGROUND_LOCATION_ACCESS_REQUEST_CODE},
        permissions={
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        }
)
public class RingfencePlugin extends Plugin {

    // These are codes used to check that permissions have been granted as picked up in 'handleRequestPermissionsResult'
    static final int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    static final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;


    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private  List<GeoItem> geofences;

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void passJson(PluginCall call){
        String value = call.getString("jsonPassed");
        Log.i("Test", value);
        // Make sure anything breaking the parser is escaped
        String geofenceStr = value.replaceAll("\n", "\\n");

        try {
            JSONObject geofenceObj = new JSONObject(geofenceStr);
            JSONArray geofenceArray = geofenceObj.getJSONObject("geofenceData").getJSONArray("record");

            geofences = new ArrayList<>();
            // Build the geofences Array with class mappings of the JSON object passed from ionic
            for(int i=0;i<geofenceArray.length();i++){
                Double[] latlong = {Double.valueOf(geofenceArray.getJSONObject(i).getString("lat")), Double.valueOf(geofenceArray.getJSONObject(i).getString("long"))};

                GeoItem geoObj = new GeoItem(geofenceArray.getJSONObject(i).getString("name"), latlong, Integer.parseInt(geofenceArray.getJSONObject(i).getString("radius")));
                geofences.add(geoObj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        enableUserlocation();
    }


    public void enableUserlocation() {
        Log.i("Test", "enableUserlocation === ");
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Fine location permission has already been granted
            enableBackgroundTracking();
        }else if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ){
            pluginRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_ACCESS_REQUEST_CODE);
        } else {
            Log.i("Test", "___________________ Get Permissions ____________________ !!!!!");
            pluginRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_ACCESS_REQUEST_CODE);
        }

    }

    private void enableBackgroundTracking() {
        pluginRequestPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
    }


    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int result : grantResults) {
            Log.i("Test", "grantResults === "+String.valueOf(result));
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.i("Test", "User denied permission");
                return;
            }
        }

        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            Log.i("Test", "FINE_LOCATION_ACCESS_REQUEST_CODE");
            // We got the permission proceed to enable background tasks
            enableBackgroundTracking();
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            Log.i("Test", "BACKGROUND_LOCATION_ACCESS_REQUEST_CODE");
            // We got the permission proceed to setup geolocations
            setupGeolocations();
        }

    }



    public void setupGeolocations() {
        Log.i("Test", "User has accepted  ALL permissions");
        geofenceHelper = new GeofenceHelper((Activity) this.getContext());

        geofencingClient = LocationServices.getGeofencingClient((Activity) this.getContext());

        ArrayList<Geofence> geofenceArray = new ArrayList<>();
        // Build our geofence (we can run this on a loop to create multiple Geofences and add them to an ArrayList
        for(GeoItem geoitem : geofences){
            Geofence geofence = geofenceHelper.getGeofence(geoitem.getId(), geoitem.getlatlong(), geoitem.getRadius(), Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL );
            geofenceArray.add(geofence);
        }

        if (ContextCompat.checkSelfPermission((Activity) this.getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            geofencingClient.addGeofences(geofenceHelper.getGeofencingRequest(geofenceArray), geofenceHelper.getPendingIntent())
                    .addOnSuccessListener((Activity) this.getContext(), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("Test", "onSuccess: Geofences Added...");
                        }
                    })
                    .addOnFailureListener((Activity) this.getContext(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String errorMessage = geofenceHelper.getErrorString(e);
                            Log.i("Test", "onFailure: " + errorMessage);
                        }
                    });
        }
    }



}


/**
 * Class to map the JSON object passed from ionic
 * Currently this maps two values - the 'name' of the venue and the 'latlong' location
 */
class GeoItem {
    private String id = UUID.randomUUID().toString();
    private String name;
    private Double[] latlong;
    private int radius;

    public GeoItem(String name, Double[] latlong, int radius) {
        this.name = name;
        this.latlong = latlong;
        this.radius = radius;
    }
    public String getName(){
        return this.name;
    }
    public Double[] getlatlong(){
        return this.latlong;
    }
    public String getId(){
        return this.id;
    }
    public int getRadius(){
        return this.radius;
    }
}
