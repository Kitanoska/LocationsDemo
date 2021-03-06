package com.demo.locationsdemo.Presenters;

import android.os.AsyncTask;

import com.demo.locationsdemo.Database.DatabaseAccess;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Model.User;
import com.demo.locationsdemo.Views.UsersDataView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Natalija on 11/9/2017.
 */

public class UsersDataPresenterImpl implements UserDataPresenter{

    private UsersDataView usersDataView;
    DatabaseAccess databaseAccess;


    public UsersDataPresenterImpl(UsersDataView usrDataView){
        usersDataView = usrDataView;
        databaseAccess = DatabaseAccess.getInstance(ApplicationClass.getContext());
    }

    @Override
    public void saveUsersData(String firstName, String lastName, String cardNo, Integer pin){

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCardNumber(cardNo);
        user.setPin(pin);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //AppDatabase.getAppDatabase(ApplicationClass.getContext()).userDao().insertUsersData(user); //ROOM

                try {
                    databaseAccess.open();
                } catch (IOException e) {
                    e.printStackTrace();
                    usersDataView.displayError("Could not access database");
                }
                databaseAccess.insertUser(user);
                databaseAccess.close();
            }
        });
       usersDataView.openNextScreen();
    }

    @Override
    public User getUser() {
        try {
            databaseAccess.open();
        } catch (IOException e) {
            e.printStackTrace();
            usersDataView.displayError("Could not access database");
        }
        User user = databaseAccess.getUser();
        databaseAccess.close();

        return user;
    }


}
