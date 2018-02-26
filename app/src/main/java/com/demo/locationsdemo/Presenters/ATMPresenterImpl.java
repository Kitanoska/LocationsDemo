package com.demo.locationsdemo.Presenters;

import android.location.Location;
import android.os.AsyncTask;

import com.demo.locationsdemo.Database.DatabaseAccess;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Database.AppDatabase;
import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Model.User;
import com.demo.locationsdemo.Views.ATMView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Natalija on 11/14/2017.
 */

public class ATMPresenterImpl implements  ATMPresenter{

    private ATMView atmView;
    private DatabaseAccess databaseAccess;

    public ATMPresenterImpl(ATMView view){
        this.atmView = view;
        databaseAccess = DatabaseAccess.getInstance(ApplicationClass.getContext());
    }

    @Override
    public void getAllATMNear(Location currentLocation) {
        //TODO swith to async task class
        /*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<ATMEntity> atms = AppDatabase.getAppDatabase(ApplicationClass.getContext()).atmDao().getAll();
            }
        }); */

        try {
            databaseAccess.open();
        } catch (IOException e) {
            e.printStackTrace();
            atmView.displayError("Could not access database");
        }

        List<ATM> atmsList = databaseAccess.getAllAtmNear(currentLocation);
        databaseAccess.close();
        atmView.displayListOfATMs(atmsList);
    }

}
