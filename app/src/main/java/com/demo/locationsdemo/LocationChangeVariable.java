package com.demo.locationsdemo;

import android.location.Location;

import java.util.Observable;

/**
 * Created by Natalija on 2/24/2018.
 */

public class LocationChangeVariable{

    private Location currentLocation;
    private ChangeListener listener;


    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }

}
