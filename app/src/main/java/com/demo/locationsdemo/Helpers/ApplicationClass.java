package com.demo.locationsdemo.Helpers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.demo.locationsdemo.LocationChangeVariable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Natalija on 11/11/2017.
 */

public class ApplicationClass extends Application{

    private static Context context;
    private static String generatedHashCode;
    public static LocationChangeVariable currentLocation;
    //private static double myLongitude;
    //private static double myLatitude;
    final String PREFS_NAME = "FirstStart";

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        currentLocation = new LocationChangeVariable();
    }

    /**
     * is true app is launched for the first time
     * @return
     */
    public static boolean isAppFirstStarted(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        /*boolean previouslyStarted = prefs.getBoolean("first_user_data" , false);
        if(!previouslyStarted){
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("first_user_data", Boolean.TRUE);
            edit.commit();
        }*/
        return prefs.getBoolean("first_user_data" , true);
    }

    /**
     * when user data is entered for the first time, it s not displayed anymore
     */
    public static void firstUserDataEntered(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("first_user_data", false).commit();
    }

    public static void setGeneratedCode(String hashCode) {
        generatedHashCode = hashCode;
    }

    public static String getGeneratedHashCode(){
        return generatedHashCode;
    }

    /*public static double getMyLongitude() {
        return myLongitude;
    }

    public static void setMyLongitude(double myLongitude) {
        ApplicationClass.myLongitude = myLongitude;
    }

    public static double getMyLatitude() {
        return myLatitude;
    }

    public static void setMyLatitude(double myLatitude) {
        DecimalFormat df = new DecimalFormat("#.####");
        myLatitude = Double.valueOf(df.format(myLatitude));
        ApplicationClass.myLatitude = myLatitude;
    }*/

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
