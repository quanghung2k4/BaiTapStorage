package com.learn.baitapstorage.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.learn.baitapstorage.models.Account;

@Database(entities = {Account.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract DAO dao();
    private static AppDB INSTANCE;

    public static AppDB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDB.class,
                    "absss"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
