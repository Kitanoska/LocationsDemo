package com.demo.locationsdemo.Presenters;

import android.location.Location;

import com.demo.locationsdemo.Model.User;

/**
 * Created by Natalija on 11/14/2017.
 */

public interface ATMPresenter {

    public void getAllATMNear(Location currentLocation);
    public User getUser();
}
