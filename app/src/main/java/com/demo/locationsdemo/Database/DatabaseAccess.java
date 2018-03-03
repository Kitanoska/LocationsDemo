package com.demo.locationsdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.demo.locationsdemo.Dao.ATMDao;
import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Helpers.ApplicationClass;
import com.demo.locationsdemo.Model.ATM;
import com.demo.locationsdemo.Model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalija on 11/14/2017.
 */

public class DatabaseAccess {

    private DatabaseHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() throws IOException{
        openHelper.createDataBase();
        database = openHelper.openDataBase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        /*if (database != null) {
            database.close();
        }*/
    }

    /**
     * Read all ATMs from the database.
     *
     * @return a List of atms
     */
    public List<ATM> getAllAtmNear(Location myCurrentLoc) {
        List<ATM> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM atm", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            float[] dist = new float[1];
            double lat = cursor.getDouble(1);
            double lon = cursor.getDouble(2);
            Location.distanceBetween(lat, lon, myCurrentLoc.getLatitude(), myCurrentLoc.getLongitude(), dist);

            //search in radius 50m
            if(dist[0]<50) {

                ATM atmEntity = new ATM();

                atmEntity.setId(cursor.getInt(0));
                atmEntity.setLongitude(cursor.getString(2));
                atmEntity.setLatitude(cursor.getString(1));
                atmEntity.setName(cursor.getString(3));
                atmEntity.setDistance(ApplicationClass.round(dist[0],2));

                list.add(atmEntity);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void insertUser(User user){
        ContentValues values = new ContentValues();
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("card_number", user.getCardNumber());
        values.put("pin", user.getPin());
        database.execSQL("delete from user");
        long id =database.insert("user", null, values);

    }

    public User getUser(){

        User user = new User();
        Cursor cursor = database.rawQuery("select * from user", null);

        if (cursor.moveToFirst()){
            do{
                user.setId(cursor.getInt(0));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setCardNumber(cursor.getString(3));
                user.setPin(cursor.getInt(4));

            }while(cursor.moveToNext());
        }

        cursor.close();
        return user;
    }

    public void insertATM(String longitude, String latitude, String name) {

        ContentValues values = new ContentValues();
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("name", name);
        try {
            if(!database.isOpen())
            {
                database = openHelper.openDataBase();

            }
            long id = database.insert("atm", null, values);

        }catch(Exception e){
            Log.v("DATABASE", e.getMessage());
        }

        /*if (id != -1)
            Log.v("DATABASE", "INSERTED");
        else Log.e("DATABASE", "NOT INSERTED");*/
    }
}
