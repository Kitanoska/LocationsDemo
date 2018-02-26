package com.demo.locationsdemo.Listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.demo.locationsdemo.Helpers.ApplicationClass;

/**
 * Created by Natalija on 2/25/2018.
 */

public class MyLocationListener implements LocationListener {

    private String TAG = "Location Listener";

    @Override
    public void onLocationChanged(Location location) {

        Log.v(TAG,"LocationChanged");

        //update live data
        //usersLocationViewModel.getCurrentUsersLocation().setValue(location);

        //update current location variable
        ApplicationClass.currentLocation.setCurrentLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
