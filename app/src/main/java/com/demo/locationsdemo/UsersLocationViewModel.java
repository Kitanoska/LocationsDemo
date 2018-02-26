package com.demo.locationsdemo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

/**
 * Created by Natalija on 2/24/2018.
 */

public class UsersLocationViewModel extends ViewModel {

    private MutableLiveData<Location> currentUsersLocation;

    public MutableLiveData<Location> getCurrentUsersLocation() {
        if (currentUsersLocation == null) {
            currentUsersLocation = new MutableLiveData<Location>();
        }
        return currentUsersLocation;
    }

}
