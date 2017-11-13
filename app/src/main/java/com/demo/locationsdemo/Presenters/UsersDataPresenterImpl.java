package com.demo.locationsdemo.Presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.demo.locationsdemo.ApplicationClass;
import com.demo.locationsdemo.Database.AppDatabase;
import com.demo.locationsdemo.Entity.UserEntity;
import com.demo.locationsdemo.Views.UsersDataView;

/**
 * Created by Natalija on 11/9/2017.
 */

public class UsersDataPresenterImpl implements UserDataPresenter{

    private UsersDataView usersDataView;

    public UsersDataPresenterImpl(UsersDataView usrDataView){
        usersDataView = usrDataView;
    }

    public void saveUsersData(String firstName, String lastName, String cardNo, Integer pin){

        final UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCardNumber(cardNo);
        user.setPin(pin);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(ApplicationClass.getContext()).userDao().insertUsersData(user);
            }
        });
       usersDataView.openNextScreen();
    }

}
