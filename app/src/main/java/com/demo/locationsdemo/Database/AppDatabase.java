package com.demo.locationsdemo.Database;

import android.content.Context;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.demo.locationsdemo.Dao.ATMDao;
import com.demo.locationsdemo.Dao.UserDao;
import com.demo.locationsdemo.Entity.ATMEntity;
import com.demo.locationsdemo.Entity.UserEntity;

/**
 * Created by Natalija on 11/9/2017.
 */

@Database(entities = {UserEntity.class, ATMEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

        private static AppDatabase INSTANCE;

        public abstract UserDao userDao();
        public abstract ATMDao atmDao();

        public static AppDatabase getAppDatabase(Context context) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                                // allow queries on the main thread.
                                // Don't do this on a real app! See PersistenceBasicSample for an example.
                                //.allowMainThreadQueries()
                                .build();
            }
            return INSTANCE;
        }

        public static void destroyInstance() {
            INSTANCE = null;
        }
}

