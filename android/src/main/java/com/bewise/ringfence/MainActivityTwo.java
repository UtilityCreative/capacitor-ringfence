/*
package com.bewise.ringfence;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivityTwo extends Activity {



    public RingfencePlugin ringfencePlugin = new RingfencePlugin();


    //RingfencePlugin ringfencePlugin = (RingfencePlugin) bridge.getPlugin("RingfencePlugin").getInstance();

    public void MainActivityTwo(){

    }

    public void onStart() {
        super.onStart();
        //this.ringfencePlugin = new RingfencePlugin(this);
        //ringfencePlugin = (RingfencePlugin) bridge.getPlugin("RingfencePlugin").getInstance();
        enableUserLocation();
    }

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    private Double latlong[] = {-37.8229, 144.9614};




    private void enableUserLocation() {
        Log.i("Test", "___________________ Get Permissions ____________________ !!!!!");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Test", "___________________ Permissions granted ____________________ !!!!!");
            enableBackgroundLocation();
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }


    private void enableBackgroundLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("Test", "___________________ Background Location check permitted ____________________ !!!!!");
            //ringfencePlugin.setupGeofences(latlong , 500, this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                //We show a dialog and ask for permission
                Log.i("Test", "___________________ Show dialog for background permission ____________________ !!!!!");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                Log.i("Test", "___________________ Show dialog anyway for background permission ____________________ !!!!!");
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }


    //protected static final int REQUEST_TRACKING = 12345; // Unique request code

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Log.i("Test", "___________________ Permissions granted ____________________ !!!!!");
                enableBackgroundLocation();
                //ringfencePlugin.setupGeofences(latlong , 500, this);
            } else {
                //We do not have the permission..
                Log.i("Test", "___________________ Permissions not granted ____________________ !!!!!");
            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Log.i("Test", "___________________ Background Location access granted ____________________ !!!!!");
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
                //ringfencePlugin = (RingfencePlugin) bridge.getPlugin("RingfencePlugin").getInstance();
                ringfencePlugin.setupGeofences(latlong , 500, this);
            } else {
                //We do not have the permission..
                Log.i("Test", "___________________ Background Location access not allowed ____________________ !!!!!");
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
*/