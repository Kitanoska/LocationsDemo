package com.demo.locationsdemo.Views;

import android.arch.lifecycle.LiveData;
import android.location.Location;

import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Model.ATM;

import java.util.List;

/**
 * Created by Natalija on 11/14/2017.
 */

public interface ATMView {

    void displayListOfATMs(List<ATM> atmList);
    void displayError(String message);
}
