package com.demo.locationsdemo.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.demo.locationsdemo.Entity.ATMEntity;


import java.util.List;

/**
 * Created by Natalija on 11/9/2017.
 */

@Dao
public interface ATMDao {

    @Query("SELECT * FROM atm")
    List<ATMEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsersData(List<ATMEntity> atms);
}
