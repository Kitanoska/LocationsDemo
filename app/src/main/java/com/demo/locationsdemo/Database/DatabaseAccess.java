package com.demo.locationsdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.demo.locationsdemo.Dao.ATMDao;
import com.demo.locationsdemo.Entity.ATMEntity;
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
        //this.database = openHelper.getWritableDatabase();
        openHelper.createDataBase();
        this.database = openHelper.openDataBase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all ATMs from the database.
     *
     * @return a List of atms
     */
    public List<ATM> getAllAtm() {
        List<ATM> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM atm", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ATM atmEntity = new ATM();

            atmEntity.setId(cursor.getInt(0));
            atmEntity.setLongitude(cursor.getString(1));
            atmEntity.setLatitude(cursor.getString(2));
            atmEntity.setName(cursor.getString(3));

            list.add(atmEntity);
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
        database.insert("user", null, values);
    }
}
