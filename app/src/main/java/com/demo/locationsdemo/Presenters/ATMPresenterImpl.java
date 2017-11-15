package com.demo.locationsdemo.Presenters;

import android.os.AsyncTask;

import com.demo.locationsdemo.Database.DatabaseAccess;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Database.AppDatabase;
import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Views.ATMView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Natalija on 11/14/2017.
 */

public class ATMPresenterImpl implements  ATMPresenter{

    private ATMView atmView;

    public ATMPresenterImpl(ATMView view){
        this.atmView = view;
    }

    @Override
    public void getAllATM() {

        //TODO swith to async task class
        /*AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<ATMEntity> atms = AppDatabase.getAppDatabase(ApplicationClass.getContext()).atmDao().getAll();
            }
        }); */


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ApplicationClass.getContext());
        try {
            databaseAccess.open();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
            //atmView.displayError
        }
        List<ATM> atmsList = databaseAccess.getAllAtm();
        databaseAccess.close();

        atmView.displayListOfATMs(atmsList);
    }
}
