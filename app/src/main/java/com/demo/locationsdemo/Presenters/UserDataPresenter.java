package com.demo.locationsdemo.Presenters;

import com.demo.locationsdemo.Model.User;

/**
 * Created by Natalija on 11/11/2017.
 */

public interface UserDataPresenter {

    public void saveUsersData(String firstName, String lastName, String cardNo, Integer pin);
    public User getUser();
}
