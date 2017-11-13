package com.demo.locationsdemo.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demo.locationsdemo.Entity.UserEntity;
import com.demo.locationsdemo.Entity.UserEntity;

import java.util.List;

/**
 * Created by Natalija on 11/9/2017.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<UserEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsersData(UserEntity user);

    @Insert
    void insertAll(UserEntity... users);
}
